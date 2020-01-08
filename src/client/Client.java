package client;

import server.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static User currentUser;
    private static String bookCatalogue;

    public static void main(String[] argv) {
        connexion();
        if (currentUser != null) {
            System.out.println("Authentication successful.");
            try {
                startApplication();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User don't exist.");
        }
    }

    private static void connexion() {
        Scanner inFromUser = new Scanner(System.in);

        try {
            Socket connexionSocket = new Socket("localhost", 6969);
            // sending user's credentials to the server for authentication
            new DataOutputStream(connexionSocket.getOutputStream()).writeUTF(inFromUser.nextLine());
            // watching for confirmation
            currentUser = (User) new ObjectInputStream(connexionSocket.getInputStream()).readObject();
            bookCatalogue = new DataInputStream(connexionSocket.getInputStream()).readUTF();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private static void startApplication() throws IOException {
        Scanner inFromUser = new Scanner(System.in);
        Socket bookingSocket = new Socket("localhost", 2500);
        Socket borrowingSocket = new Socket("localhost", 2600);
        Socket returningSocket = new Socket("localhost", 2700);

        while (true) {
            String[] command = inFromUser.nextLine().split("\\s+");
            switch (command[0]) {
                case "catalogue":
                    System.out.println(bookCatalogue);
                    break;
                case "booking":
                    new DataOutputStream(bookingSocket.getOutputStream()).writeUTF(currentUser.getID());
                    new DataOutputStream(bookingSocket.getOutputStream()).writeUTF(command[1]);
                    System.out.println(new DataInputStream(bookingSocket.getInputStream()).readUTF());
                    break;
                case "borrowing":
                    new DataOutputStream(borrowingSocket.getOutputStream()).writeUTF(currentUser.getID());
                    new DataOutputStream(borrowingSocket.getOutputStream()).writeUTF(command[1]);
                    System.out.println(new DataInputStream(borrowingSocket.getInputStream()).readUTF());
                    break;
                case "returning":
                    new DataOutputStream(returningSocket.getOutputStream()).writeUTF(command[1]);
                    System.out.println(new DataInputStream(returningSocket.getInputStream()).readUTF());
                    break;
                case "help":
                    printHelp();
                    break;
                default:
                    System.out.println("Sorry, I didn't get that. Type 'help' to see all available commands.");
            }
        }
    }

    private static void printHelp() {
        String leftAlignFormat = "│ %-9s │ %-36s │ %-18s │%n";
        String separator = "├───────────┼──────────────────────────────────────┼────────────────────┤%n";

        System.out.format("╭───────────┬──────────────────────────────────────┬────────────────────╮%n");
        System.out.format(leftAlignFormat, "Commands", "Description", "Usage");
        System.out.format(separator);
        System.out.format(leftAlignFormat, "catalogue", "Display the current catalogue", "catalogue");
        System.out.format(separator);
        System.out.format(leftAlignFormat, "booking", "Reserves you the given book", "booking 'bookID'");
        System.out.format(separator);
        System.out.format(leftAlignFormat, "borrowing", "Borrows the given book", "borrowing 'bookID'");
        System.out.format(separator);
        System.out.format(leftAlignFormat, "returning", "Return the given book to the library", "returning 'bookID'");
        System.out.format(separator);
        System.out.format(leftAlignFormat, "help", "Display this table", "help");
        System.out.format("╰───────────┴──────────────────────────────────────┴────────────────────╯%n");
    }
}