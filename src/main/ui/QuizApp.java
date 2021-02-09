package ui;

import model.Deck;

import java.util.ArrayList;
import java.util.Scanner;

public class QuizApp {
    private ArrayList<Deck> decks;
    private Scanner scanner;

    public QuizApp() {
        scanner = new Scanner(System.in);
        decks = new ArrayList<>();
        start();
    }

    public void start() {
        boolean keepOpen = true;
        int input;

        System.out.println("Welcome to QuizzBuzz!");
        while (keepOpen) {
            openMenu();
            input = scanner.nextInt();

            if (input == 3) {
                keepOpen = false;
            } else {
                processSelection(input);
            }

        }

        System.out.println("Thank you for using QuizzBuzz! See you next Time!");

    }

    private void openMenu() {
        System.out.println("\nPlease select from one of the options below:");
        System.out.println("\t1 -> Select from/view all Decks");
        System.out.println("\t2 -> Create a new Deck");
        System.out.println("\t3 -> Quit");
    }

    private void processSelection(int i) {
        if (i == 1) {
            viewDecks();
        } else if (i == 2) {
            System.out.println("Please enter name of new deck:");
            createDeck();
        }
    }

    private void createDeck() {
        String s = scanner.next();
        Deck d = new Deck(s);

        decks.add(d);
        System.out.println("Deck " + "\"" + s + "\"" + " created!");
    }

    private void viewDecks() {
        if (decks.size() == 0) {
            System.out.println("No decks to view!");
        } else {
            System.out.println("Deck List:");
            int i = 1;
            for (Deck d : decks) {
                System.out.println("\t" + i + ") " + d.getTitle());
                i++;
            }

            System.out.println("\nEnter a deck number to select a deck or enter any other key to return to menu:");
            int command = scanner.nextInt();
            if (command <= decks.size() && command > 0) {
                System.out.println(decks.get(command - 1).toString());
            }

        }
    }
}
