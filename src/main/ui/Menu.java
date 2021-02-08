package ui;

import model.Deck;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<Deck> decks;
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
        decks = new ArrayList<>();
        start();
    }

    public void start() {
        int input = 0;

        System.out.println("Welcome to QuizzBuzz!");
        while (true) {
            System.out.println("Please select from one of the options below:");
            System.out.println("1) View all Decks");
            System.out.println("2) Quit");
            input = scanner.nextInt();

            if (input == 1) {
                viewDecks();
            }

            if (input == 2) {
                break;
            }

        }

    }

    private void viewDecks() {
        if (decks.size() == 0) {
            System.out.println("No decks to view!");
        }

        for (Deck d: decks) {
            System.out.println(d.getTitle());
        }
    }
}
