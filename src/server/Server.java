/**
 * This Class is the main application for the server side.
 * It involve using 5 threads :
 * - 3 like stipulated in the contract
 * - 2 added for user's convenience
 * Each thread wait for a connection to occur and then
 * create a sub-thread to fulfill the user demand.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 3.8
 */

package server;

import server.document.book.Book;
import server.document.book.Library;
import server.document.iDocument;
import server.document.iDocument.BookingException;
import server.user.UserDB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public static void main(String[] args) throws Exception {

        /* creating the library */
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library");
        System.out.println(library.toString());

        /* creating the user database */
        UserDB userDB = new UserDB("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\user\\database");
        System.out.println(userDB.toString());

        /* creating the Timer */
        Timer timer = new Timer();

        /**
         * Booking thread
         * Check for a connection, then give the work to a sub-thread.
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2500;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());

                    /**
                     * Sub-thread
                     * Find the book from the given ID and calls his booking method
                     * with the correct user.
                     *
                     * need : User's and Book's ID
                     * send : result or error message
                     */
                    new Thread(() -> {
                        System.out.println("booking sub-thread started, id : " + Thread.currentThread().getId());
                        try {
                            /* initializing */
                            DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                            String userID = inFromClient.readUTF();
                            String bookID = inFromClient.readUTF();
                            /* processing */
                            Book b = Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null));
                            b.booking(userDB.findUserFromID(userID));
                            /* setting timer for the borrowing */
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (b.isAvailable()) { // if the book isn't borrowed by the user who reserved
                                        b.reset(); // then make the book available again
                                        System.out.println("book n°" + bookID + " has been reset.");
                                    }
                                    cancel();
                                }
                            }, 120000);
                            /* outputting */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Booking successful.");
                            System.out.println("book n°" + bookID + " booked by user " + userID);
                        } catch (IOException | BookingException e) {
                            e.printStackTrace();
                            /* sending error message to client */
                            try {
                                new DataOutputStream(socket.getOutputStream()).writeUTF(e.getMessage());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }, "booking_thread").start();

        /**
         * Borrowing thread
         * Check for a connection, then give the work to a sub-thread.
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2600;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());

                    /**
                     * Sub-thread
                     * Find the book from the given ID and calls his borrowing method
                     * with the correct user.
                     *
                     * need : User's and Book's ID
                     * send : result or error message
                     */
                    new Thread(() -> {
                        System.out.println("borrowing sub-thread started, id : " + Thread.currentThread().getId());
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
                            /* outputting */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Borrowing successful.");
                            System.out.println("book n°" + bookID + " borrowed by user " + userID);
                        } catch (IOException | BookingException e) {
                            e.printStackTrace();
                            /* sending error message to client */
                            try {
                                new DataOutputStream(socket.getOutputStream()).writeUTF(e.getMessage());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }, "borrowing_thread").start();

        /**
         * Returning thread
         * Check for a connection, then give the work to a sub-thread.
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2700;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());

                    /**
                     * Sub-thread
                     * Find the book from the given ID and calls his returning method
                     * with the correct user.
                     *
                     * need : User's and Book's ID
                     * send : result or error message
                     */
                    new Thread(() -> {
                        System.out.println("returning sub-thread started, id : " + Thread.currentThread().getId());
                        try {
                            /* initializing */
                            DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                            String userID = inFromClient.readUTF();
                            String bookID = inFromClient.readUTF();
                            /* processing */
                            Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null))
                                    .returning(userDB.findUserFromID(userID));
                            /* outputting */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Returning successful.");
                            System.out.println("book n°" + bookID + " returned");
                        } catch (IOException | iDocument.ReturnException e) {
                            e.printStackTrace();
                            /* sending error message to client */
                            try {
                                new DataOutputStream(socket.getOutputStream()).writeUTF(e.getMessage());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        }, "returning_thread").start();

        /**
         * Authentication thread
         * Check for a connection, then give the work to a sub-thread.
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 69; // ah ah lmao, get it ?
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());

                    /**
                     * Sub-thread
                     * Check if the user exist in userDB,
                     * if yes : send it to the client
                     * if not : send a null
                     * (the client interpret the null like "user not found")
                     *
                     * need : User's ID and password
                     * send : result or error message
                     */
                    new Thread(() -> {
                        System.out.println("authentication sub-thread started, id : " + Thread.currentThread().getId());
                        try {
                            /* getting user's credentials */
                            String[] userCredentials = new DataInputStream(socket.getInputStream()).readUTF().split("\\s+");
                            /* sending out the corresponding user, null is none */
                            new ObjectOutputStream(socket.getOutputStream()).writeObject(userDB.returnIfExist(userCredentials[0], userCredentials[1]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "authentication_thread").start();

        /**
         * Catalogue thread
         * Check for a connection, then give the work to a sub-thread.
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 420; // that's comedy right here
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());

                    /**
                     * Sub-thread
                     * Calls the .toString() method of the library and
                     * add a timestamp for user convenience.
                     *
                     * send : String of the catalogue
                     */
                    new Thread(() -> {
                        System.out.println("catalogue sub-thread started, id : " + Thread.currentThread().getId());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        try {
                            new DataOutputStream(socket.getOutputStream()).writeUTF(library.toString() +
                                    "last updated : " + formatter.format(date) + "\n");
                            socket.shutdownOutput();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "catalogue_thread").start();
    }
}
