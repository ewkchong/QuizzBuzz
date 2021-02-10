package model;

import java.util.ArrayList;
import java.util.Scanner;

import static ui.QuizApp.header;

public class Deck {
    private String title;
    private ArrayList<Card> cardList;
    private Scanner scanner = new Scanner(System.in);

    public Deck(String title) {
        this.title = title;
        cardList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addCard() {
        ArrayList<String> tags = new ArrayList<>();

        header("New Card:");

        System.out.println("\tFront of Card:");
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

        Card c = new Card(front, back, tags);
        cardList.add(c);
        System.out.println("Card added!");

    }

    public void viewCards() {
        header("Card List");
        if (cardList.size() == 0) {
            System.out.println("No cards to view!");
        } else {
            int i = 1;
            for (Card c : cardList) {
                System.out.println(i + ") ");
                c.viewCard();
                i++;
            }
        }
    }

    public void deleteCard() {
        System.out.println("Enter in the number of the card you would like to delete, or 0 to quit:");
        int e = scanner.nextInt();

        if (e <= cardList.size() && !(e <= 0)) {
            cardList.remove(e - 1);
        }
    }

    public void renameDeck() {
        header("Rename Deck:");
        System.out.println("Enter new name:");
        this.title = scanner.nextLine();
        System.out.println("Deck name changed!");
    }
}
