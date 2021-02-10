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
        runQuizApp();
    }

    // EFFECTS: opens start menu
    public void runQuizApp() {
        boolean keepOpen = true;
        int input;

        header("Welcome to QuizzBuzz!");
        while (keepOpen) {
            openMenu();
            input = scanner.nextInt();

            if (input == 3) {
                keepOpen = false;
            } else {
                processStartMenuCommand(input);
            }

        }

        System.out.println("Thank you for using QuizzBuzz! See you next Time!");

    }

    // EFFECTS: diplays start menu options
    private void openMenu() {
        System.out.println("\nPlease select from one of the options below using the numbers:");
        System.out.println("\t1) Select from/view all Decks");
        System.out.println("\t2) Create a new Deck");
        System.out.println("\t3) Quit");
    }

    // EFFECTS: processes user key input for start menu
    private void processStartMenuCommand(int i) {
        if (i == 1) {
            viewDecks();
        } else if (i == 2) {
            System.out.println("Please enter name of new deck:");
            createDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new deck with user-input generated title
    private void createDeck() {
        scanner.nextLine();
        String s = scanner.nextLine();
        Deck d = new Deck(s);

        decks.add(d);
        System.out.println("Deck " + "\"" + s + "\"" + " created!");
        deckMenu(d);
    }

    // EFFECTS: prints a list of all decks, allows selection
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
                deckMenu(decks.get(command - 1));
            }

        }
    }

    // EFFECTS: displays operations for a given deck
    public void deckMenu(Deck d) {
        int selection = 0;

        while (!(selection > 0)) {
            System.out.println("\nDeck " + d.getTitle() + ":");
            System.out.println("\t1) Study!");
            System.out.println("\t2) View all cards");
            System.out.println("\t3) Add a card");
            System.out.println("\t4) Remove a card");
            System.out.println("\t5) Rename deck");
            System.out.println("\t6) Delete deck");
            System.out.println("\t7) Return to menu");
            selection = scanner.nextInt();
        }
        processDeckMenuCommand(d, selection);
    }

    // REQUIRES: 1 <= s <= 7
    // MODIFIES: this
    // EFFECTS: processes user input from deck menu
    private void processDeckMenuCommand(Deck d, int s) {
        if (s == 2) {
            d.viewCards();
            waitForEnter();
            deckMenu(d);
        } else if (s == 3) {
            d.addCard();
            waitForEnter();
            deckMenu(d);
        } else if (s == 4) {
            d.viewCards();
            d.deleteCard();
        } else if (s == 5) {
            d.renameDeck();
            waitForEnter();
        } else if (s == 6) {
            decks.remove(d);
        }
    }

    // EFFECTS: waits for user to press ENTER before continuing
    public void waitForEnter() {
        while (true) {
            System.out.println("\nPress ENTER to return to menu...");
            scanner.nextLine();
            String ent = scanner.nextLine();

            if (ent.equals("")) {
                break;
            }
        }
    }

    // EFFECTS: adds a horizontal line under given string and prints
    public static void header(String h) {
        System.out.println("\n" + h);
        System.out.println("==============================================================");
    }
}
