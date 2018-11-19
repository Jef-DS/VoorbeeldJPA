package org.betavzw.jpa.persistence.memory;

import org.betavzw.jpa.entities.Persoon;
import org.betavzw.jpa.persistence.NotFoundException;
import org.betavzw.jpa.persistence.PersoonRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryPersoonRepository implements PersoonRepository {
    private Map<Long, Persoon> personen = new HashMap<>();
    private long laatsteId=0;
    @Override
    public Persoon addPersoon(String naam) {
        laatsteId++;
        Persoon p = new Persoon(laatsteId, naam);
        personen.put(laatsteId, p);
        return p;
    }

    @Override
    public boolean deletePersoon(Persoon persoon) {
        if(getPersoon(persoon.getId())== null) return false;
        personen.remove(persoon.getId());
        return true;
    }

    @Override
    public Persoon getPersoon(long id) {
        return personen.get(id);
    }

    @Override
    public void updatePersoon(Persoon persoon) throws NotFoundException {
        if (getPersoon(persoon.getId())==null) throw new NotFoundException("Persoon niet gevonden");
        personen.put(persoon.getId(), persoon);
    }

    @Override
    public Collection<Persoon> getPersonen() {
        return  personen.values();
    }
}