package ui;

import model.Card;
import model.Deck;
import org.json.JSONArray;
import persistence.Writer;
import ui.ss.AllStudySession;
import ui.ss.NormalStudySession;
import ui.ss.StudySession;
import ui.ss.TagStudySession;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// A flash card application
public class QuizApp {
    private ArrayList<Deck> decks;     // List of decks created by user
    private Scanner scanner;           // Scanner for user input

    // EFFECTS: initializes user interface
    public QuizApp(ArrayList<Deck> decks) {
        scanner = new Scanner(System.in);
        this.decks = decks;
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
        saveMenu();
        System.out.println("Thank you for using QuizzBuzz! See you next time!");
    }

    // EFFECTS: displays start menu options
    private void openMenu() {
        System.out.println("\nPlease select from one of the options below using the numbers:");
        System.out.println("\t1) Select from/view all Decks");
        System.out.println("\t2) Create a new Deck");
        System.out.println("\t3) Quit");
    }

    // REQUIRES: i == 1 OR i == 2
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
        header("Deck List");
        if (decks.size() == 0) {
            System.out.println("No decks to view!");
        } else {
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
            scanner.reset();
        }
        if (!(selection > 7)) {
            processDeckMenuCommand(d, selection);
        }
    }

    // REQUIRES: 1 <= s <= 7
    // MODIFIES: this
    // EFFECTS: processes user input from deck menu
    private void processDeckMenuCommand(Deck d, int s) {
        if (s == 1) {
            studySessionMenu(d);
            waitForEnter();
            deckMenu(d);
        } else if (s == 2) {
            cardsMenu(d);
            deckMenu(d);
        } else if (s == 3) {
            inputCardInfo(d);
            deckMenu(d);
        } else if (s == 4) {
            viewCards(d);
            deleteSelectedCard(d);
            waitForEnter();
        } else if (s == 5) {
            renameDeck(d);
            waitForEnter();
            deckMenu(d);
        } else if (s == 6) {
            decks.remove(d);
        }
    }

    // EFFECTS: displays operations on list of cards and processes user input
    private void cardsMenu(Deck d) {
        while (true) {
            viewCards(d);
            header("Options");

            System.out.println("1) Add a Card");
            System.out.println("2) Edit a Card");
            System.out.println("3) Remove a Card");
            System.out.println("4) Return to Menu");

            System.out.println("\nPlease choose a number or return to menu:");
            int command = scanner.nextInt();
            if (command == 1) {
                inputCardInfo(d);
            } else if (command == 2) {
                userSelectEditCard(d);
            } else if (command == 3) {
                deleteSelectedCard(d);
            } else if (command == 4) {
                break;
            } else {
                System.out.println("Invalid Command!");
            }
        }
    }

    // EFFECTS: allows user to select a card to edit
    private void userSelectEditCard(Deck d) {
        System.out.println("Enter a card number to edit that card:");
        int e = scanner.nextInt();
        if (e <= d.getCardList().size() && e > 0) {
            Card c = d.getCardList().get(e - 1);
            chooseEdit(c);
        } else {
            System.out.println("Invalid input");
        }

    }


    // EFFECTS: processes user choice for editing a card
    private void chooseEdit(Card c) {
        System.out.println("Would you like to edit front, back, or tags?:");
        System.out.println("\t 1) Front");
        System.out.println("\t 2) Back");
        System.out.println("\t 3) Tags");
        int e = scanner.nextInt();
        if (e == 1) {
            editCardUI("front", c);
        } else if (e == 2) {
            editCardUI("back", c);
        } else if (e == 3) {
            editTags(c);
        } else {
            System.out.println("Invalid Input!");
            waitForEnter();
        }
    }

    // EFFECTS: processes user input to change front or back of card
    private void editCardUI(String choice, Card card) {
        System.out.println("Enter new value:");
        if (choice.equals("front")) {
            scanner.nextLine();
            String entry = scanner.nextLine();
            card.setFront(entry);
            System.out.println("Card changed!");
        } else {
            scanner.nextLine();
            String entry = scanner.nextLine();
            card.setBack(entry);
            System.out.println("Card changed!");
        }
        waitForEnter();
    }


    // MODIFIES: card
    // EFFECTS: processes user input for editing tags of a single card
    private void editTags(Card card) {
        System.out.println("Tags:");
        ArrayList<String> tags = card.getTags();
        System.out.println(tags);

        System.out.println("Enter the name of a new tag to add, or existing tag to remove:");
        scanner.nextLine();
        String entry = scanner.nextLine();
        int i = tags.indexOf(entry);

        if (i != -1) {
            card.changeTag(i, entry);
            System.out.println("\nTag " + entry + " removed!");
        } else {
            card.changeTag(i, entry);
            System.out.println("\nTag " + entry + " added!");
        }
        waitForEnter();
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

    // MODIFIES: Deck d
    // EFFECTS: changes the name of deck to user-input
    private void renameDeck(Deck d) {
        header("Rename Deck:");

        System.out.println("Enter new name:");
        scanner.nextLine();
        String title = scanner.nextLine();
        d.renameDeck(title);
        System.out.println("Deck name changed!");
    }

    // MODIFIES: Deck d
    // EFFECTS: removes user-selected card from deck
    private void deleteSelectedCard(Deck d) {
        System.out.println("\nEnter in the number of the card you would like to delete, or 0 to quit:");
        int e = scanner.nextInt();
        if (e <= d.getCardList().size() && e >= 0) {
            if (e != 0) {
                d.deleteCard(e);
                System.out.println("\nCard deleted!");
            }
        } else {
            System.out.println("Invalid input! No card has been deleted");
        }
    }

    // MODIFIES: d
    // EFFECTS: user interface for adding a new card
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
        Scanner tempScanner = new Scanner(System.in);
        System.out.println("\nPress ENTER to return to menu...");
        String ent = tempScanner.nextLine();
        while (true) {
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

    public JSONArray deckListToJson() {
        JSONArray array = new JSONArray();
        for (Deck d: decks) {
            array.put(d.toJson());
        }
        return array;
    }

    public void saveMenu() {
        System.out.println("Would you like to save all changes to decks/cards?");
        System.out.println("\t1) Yes");
        System.out.println("\t2) No");
        scanner.nextLine();
        String entry = scanner.nextLine();
        if (entry.equals("1")) {
            try {
                Writer writer = new Writer("./data/decks.json");
                writer.save(deckListToJson());
                System.out.println("Saved!");
            } catch (FileNotFoundException e) {
                System.out.println("File cannot be found, NOOO!!!!");
            }
        }
    }

    public void studySessionMenu(Deck d) {
        header("How would you like to study?");
        System.out.println("\t1) Study");
        System.out.println("\t2) Study by Tag (ignore schedule)");
        System.out.println("\t3) Study all cards (ignore schedule)");
        scanner.nextLine();
        while (true) {
            String entry = scanner.nextLine();
            if (entry.equals("1")) {
                StudySession ss = new NormalStudySession(d.getCardList());
                ss.begin();
                break;
            } else if (entry.equals("2")) {
                beginTagStudy(d);
                break;
            } else if (entry.equals("3")) {
                StudySession ss = new AllStudySession(d.getCardList());
                ss.begin();
                break;
            } else {
                System.out.println("Invalid Input!");
            }
        }

    }

    public void beginTagStudy(Deck d) {
        ArrayList<String> tags = new ArrayList<>();
        boolean keepOpen = true;
        while (keepOpen) {
            System.out.println("\nEnter name of tag (case sensitive) to study, or press ENTER to quit:");
            String e = scanner.nextLine();

            if (e.equals("")) {
                keepOpen = false;
            } else {
                tags.add(e);
            }
        }
        StudySession ss = new TagStudySession(d.getCardList(), tags);
        ss.begin();
    }

}
