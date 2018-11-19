package org.betavzw.jpa;

import org.betavzw.jpa.entities.Persoon;
import org.betavzw.jpa.persistence.NotFoundException;
import org.betavzw.jpa.persistence.PersoonRepository;
import org.betavzw.jpa.persistence.derby.DerbyPersoonRepository;
import org.betavzw.jpa.persistence.memory.MemoryPersoonRepository;

import java.util.Collection;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        PersoonRepository repository = new DerbyPersoonRepository();
        for (int i = 0; i< 4;i++){
            System.out.print("Geef naam: ");
            String naam = scanner.nextLine();
            Persoon p = repository.addPersoon(naam);
            System.out.printf("Persoon %s toegevoegd met id %d%n", p.getNaam(), p.getId());
        }
        Collection<Persoon> personen = repository.getPersonen();
        for(Persoon p: personen) {
            System.out.printf("%d: %s%n", p.getId(), p.getNaam());
        }
        System.out.print("Geef het nummer van de persoon die u wil aanpassen: ");
        long id = Integer.parseInt(scanner.nextLine());
        System.out.print("Geef de nieuwe naam: ");
        String naam = scanner.nextLine();
        Persoon nieuwePersoon = new Persoon(id, naam);
        try {
            repository.updatePersoon(nieuwePersoon);
        }catch(NotFoundException nf){
            System.out.println(nf.getMessage());
        }
        personen = repository.getPersonen();
        for(Persoon p: personen) {
            System.out.printf("%d: %s%n", p.getId(), p.getNaam());
        }
        System.out.println("Laatste persoon verwijderen:");
        repository.deletePersoon(nieuwePersoon);
        personen = repository.getPersonen();
        for(Persoon p: personen) {
            System.out.printf("%d: %s%n", p.getId(), p.getNaam());
        }
    }
}
