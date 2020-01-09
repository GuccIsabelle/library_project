package server;

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

public class Server {
    public static void main(String[] args) throws Exception {

        /* creating the library */
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library");
        System.out.println(library.toString());

        /* creating the user database */
        UserDB userDB = new UserDB("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\user\\database");
        System.out.println(userDB.toString());

        /**
         * Booking thread
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2500;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());
                    new Thread(() -> {
                        System.out.println("sub-thread " + Thread.currentThread().getId() + " started");
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
                            /* outputting */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Booking successful.");
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
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2600;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());
                    new Thread(() -> {
                        System.out.println("sub-thread " + Thread.currentThread().getId() + " started");
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
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 2700;
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());
                    new Thread(() -> {
                        System.out.println("sub-thread " + Thread.currentThread().getId() + " started");
                        try {
                            /* initializing */
                            String bookID = new DataInputStream(socket.getInputStream()).readUTF();
                            /* processing */
                            Objects.requireNonNull(library.getCatalog().stream()
                                    .filter(book -> book.getID().equals(bookID))
                                    .findAny().orElse(null))
                                    .returning();
                            /* outputting */
                            new DataOutputStream(socket.getOutputStream()).writeUTF("Returning successful.");
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
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 69; // ah ah lmao, get it ?
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());
                    new Thread(() -> {
                        System.out.println("sub-thread " + Thread.currentThread().getId() + " started");
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
         */
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            final int port = 420; // that's comedy right here
            /* creating the serverSocket */
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("connexion established with " + socket.getInetAddress().getHostName());
                    new Thread(() -> {
                        System.out.println("sub-thread " + Thread.currentThread().getId() + " started");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
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
