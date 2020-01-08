package server;

import server.document.book.Library;
import server.document.iDocument;
import server.user.UserDB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    public static void main(String[] args) throws Exception {
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library");
        System.out.println(library.toString());
        UserDB userDB = new UserDB("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\user\\database");
        System.out.println(userDB.toString());

        /**
         * Booking thread
         */
        new Thread(() -> {
            final int port = 2500;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            /* initializing */
                            DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                            String userID = inFromClient.readUTF();
                            String bookID = inFromClient.readUTF();
                            /* processing */
                            Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null))
                                    .booking(userDB.findUserFromID(userID));
                            /* output */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Booking successful.");
                        } catch (IOException | iDocument.BookingException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }).start();

        /**
         * Borrowing thread
         */
        new Thread(() -> {
            final int port = 2600;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            /* initializing */
                            DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                            String userID = inFromClient.readUTF();
                            String bookID = inFromClient.readUTF();
                            /* processing */
                            Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null))
                                    .borrowing(userDB.findUserFromID(userID));
                            /* output */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Borrowing successful.");
                        } catch (IOException | iDocument.BookingException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }).start();

        /**
         * Returning thread
         */
        new Thread(() -> {
            final int port = 2700;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            /* initializing */
                            String bookID = new DataInputStream(socket.getInputStream()).readUTF();
                            /* processing */
                            Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null))
                                    .returning();
                            /* output */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Returning successful.");
                        } catch (IOException | iDocument.ReturnException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }).start();

        new Thread(() -> {
            final int port = 6969; // ah ah lmao, get it ?
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            String[] userCredentials = new DataInputStream(socket.getInputStream()).readUTF().split("\\s+");
                            new ObjectOutputStream(socket.getOutputStream()).writeObject(userDB.returnIfExist(userCredentials[0], userCredentials[1]));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
