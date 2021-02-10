package ui;

import model.Card;
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

            System.out.println("\nEnter a number to select a deck or enter 0 to return to menu:");
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
            viewCards(d);
            waitForEnter();
            deckMenu(d);
        } else if (s == 3) {
            inputCardInfo(d);
            deckMenu(d);
        } else if (s == 4) {
            viewCards(d);
            deleteSelectedCard(d);
        } else if (s == 5) {
            renameDeck(d);
            waitForEnter();
        } else if (s == 6) {
            decks.remove(d);
        }
    }

    // EFFECTS: prints list of cards in current deck
    public void viewCards(Deck d) {
        header("Card List");
        ArrayList<Card> cards = d.getCardList();

        if (cards.size() == 0) {
            System.out.println("No cards to view!");
        } else {
            int i = 1;
            for (Card c : cards) {
                System.out.println(i + ") ");
                System.out.println("Front: " + c.getFront());
                System.out.println("Back: " + c.getBack());
                System.out.println("Tags: " + c.getTags().toString());
                i++;
            }
        }
    }

    private void renameDeck(Deck d) {
        header("Rename Deck:");

        System.out.println("Enter new name:");
        scanner.nextLine();
        String title = scanner.nextLine();
        d.renameDeck(title);
        System.out.println("Deck name changed!");
    }

    private void deleteSelectedCard(Deck d) {
        System.out.println("Enter in the number of the card you would like to delete, or 0 to quit:");
        int e = scanner.nextInt();
        d.deleteCard(e);
    }

    public void inputCardInfo(Deck d) {
        ArrayList<String> tags = new ArrayList<>();

        header("New Card:");

        System.out.println("\tFront of Card:");
        scanner.nextLine();
        String front = scanner.nextLine();

        System.out.println("\tBack of Card:");
        String back = scanner.nextLine();

        System.out.println("Tags (optional):");
        boolean keepOpen = true;
        while (keepOpen) {
            System.out.println("\nEnter tag, or press ENTER to quit:");
            String e = scanner.nextLine();

            if (e.equals("")) {
                keepOpen = false;
            } else {
                tags.add(e);
            }
        }

        d.addCard(front, back, tags);
        System.out.println("Card added!");
    }

    // EFFECTS: waits for user to press ENTER before continuing
    public void waitForEnter() {
        while (true) {
            System.out.println("\nPress ENTER to return to menu...");
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
