package org.betavzw.jpa.persistence;

import org.betavzw.jpa.entities.Persoon;

import java.util.Collection;

public interface PersoonRepository {
    Persoon addPersoon(String naam);
    void deletePersoon(Persoon persoon) ;
    Persoon getPersoon(long id);
    void updatePersoon(Persoon persoon);
    Collection<Persoon> getPersonen();

}