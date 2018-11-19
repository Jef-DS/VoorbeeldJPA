package org.betavzw.jpa.persistence.derby;

import org.betavzw.jpa.entities.Persoon;
import org.betavzw.jpa.persistence.NotFoundException;
import org.betavzw.jpa.persistence.PersoonRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class DerbyPersoonRepository implements PersoonRepository {
    private EntityManager em;

    public DerbyPersoonRepository() {
        em = getEntityManager();
    }

    @Override
    public Persoon addPersoon(String naam) {
        Persoon p = new Persoon();
        p.setNaam(naam);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public boolean deletePersoon(Persoon persoon) {
        Persoon p = getPersoon(persoon.getId());
        if (p == null) return false;
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return true;
    }

    @Override
    public Persoon getPersoon(long id) {
        return em.find(Persoon.class, id);
    }

    @Override
    public void updatePersoon(Persoon persoon) throws NotFoundException {
        Persoon p = getPersoon(persoon.getId());
        if (p == null) throw new NotFoundException("Persoon bestaat niet");
        em.getTransaction().begin();
        p.setNaam(persoon.getNaam());
        em.getTransaction().commit();
    }

    @Override
    public Collection<Persoon> getPersonen() {
        return em.createQuery("SELECT p FROM Persoon p").getResultList();
    }
    private EntityManager getEntityManager(){

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        properties.put("javax.persistence.jdbc.url", "jdbc:derby:memory:db;create=true");
        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        return Persistence.createEntityManagerFactory("test",properties).createEntityManager();
    }
}
