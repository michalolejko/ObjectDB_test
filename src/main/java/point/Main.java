package point;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

public class Main {
    static Scanner scanner;
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {

        emf = Persistence.createEntityManagerFactory("$objectdb/db/travels.odb");
        em = emf.createEntityManager();

        scanner = new Scanner(System.in);
        while (true) {
            showAllRecords();
            pressEnter();
            showMenu();
            switch (scanner.nextInt()) {
                case 0:
                    System.out.println("Zakonczono");
                    em.close();
                    emf.close();
                    return;
                case 1:
                    saveRecord();
                    break;
                case 2:
                    updateRecord();
                    break;
                case 3:
                    deleteRecord();
                    break;
                case 4:
                    getRecordById();
                    break;
                case 5:
                    gerRecordByStatement();
                    break;
                case 6:
                    processing();
                    break;
            }
        }
    }

    private static void processing() {
        System.out.println("Wszystkie podroze dostana koszt o 100 wiekszy:");
        TypedQuery<Travel> query = em.createQuery("SELECT p FROM Travel p", Travel.class);
        List<Travel> results = query.getResultList();
        em.getTransaction().begin();
        for (Travel p : results)
            p.setCost(p.getCost() + 100);
        em.getTransaction().commit();
    }

    private static void gerRecordByStatement() {
        scanner.nextLine();
        System.out.println("Podaj wlasne zapytanie Select:");
        String ownQuerry = scanner.nextLine();
        TypedQuery<Travel> query =
                em.createQuery(ownQuerry, Travel.class);
        List<Travel> results = query.getResultList();
        for (Travel t : results)
            System.out.println(t);
    }

    private static void getRecordById() {
        showAllRecords();
        System.out.println("Podaj ID:");
        int id = scanner.nextInt();
        Travel travel = em.find(Travel.class, id);
        System.out.println(travel + "\n");
    }

    private static void deleteRecord() {
        showAllRecords();
        System.out.println("Podaj ID:");
        int id = scanner.nextInt();
        Travel travel = em.find(Travel.class, id);
        em.getTransaction().begin();
        em.remove(travel);
        em.getTransaction().commit();
    }

    private static void updateRecord() {
        showAllRecords();
        System.out.println("Update polega na zmianie ceny wybranego rekordu\nPodaj ID:");
        int id = scanner.nextInt();
        System.out.println("Podaj nowa cene: ");
        int cost = scanner.nextInt();
        Travel travel = em.find(Travel.class, id);
        em.getTransaction().begin();
        travel.setCost(cost);
        em.getTransaction().commit();
    }

    private static void saveRecord() {
        List<String> attractions = new ArrayList<>();
        scanner.nextLine();
        while (true) {
            System.out.println("Podaj atrakcje (lub zostaw puste aby zakonczyc):");
            String tmp = scanner.nextLine();
            if (tmp.equals(""))
                break;
            attractions.add(tmp);
        }
        System.out.println("Ile miejsc?");
        int numOfSeats = scanner.nextInt();
        System.out.println("Jaki jest koszt?");
        int cost = scanner.nextInt();
        em.getTransaction().begin();
        Travel newTrav = new Travel(attractions, numOfSeats, cost);
        em.persist(newTrav);
        em.getTransaction().commit();
    }

    private static void showAllRecords() {
        System.out.println("Wszystkie rekordy:");
        TypedQuery<Travel> query = em.createQuery("SELECT p FROM Travel p", Travel.class);
        List<Travel> results = query.getResultList();
        for (Travel p : results)
            System.out.println(p);
    }

    private static void pressEnter() {
        System.out.println("Wcisnij enter, aby kontynuowac...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.print("\n2) Biuro turystyczne (ObjectDB)\n\nWybierz operacje:\n" +
                "1.Zapisywanie\n2.Aktualizowanie\n3.Kasowanie\n4.Pobieranie po ID\n5.Wlasne zapytanie)\n" +
                "6.Przetwarzanie(koszt+=100)\n0.Zakoncz\n\nWpisz cyfre i zatwierdz enterem: ");
    }

}