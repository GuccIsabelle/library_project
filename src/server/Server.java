package server;

import server.document.book.Library;
import server.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws Exception {
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library");
        System.out.println(library.toString());

        /**
         * Booking thread
         */
        Thread bookingThread = new Thread(() -> {
            final int port = 2500;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
                            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

                            String bookID = inFromClient.readUTF();
//                            library.getCatalog().stream()
//                                    .filter(book -> book.getID().equals(bookID))
//                                    .collect(Collectors.toList())
//                                    .forEach(book -> book.booking(user));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        });

        /**
         * Borrowing thread
         */
        Thread borrowingThread = new Thread(() -> {
            final int port = 2600;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        });

        /**
         * Returning thread
         */
        Thread returningThread = new Thread(() -> {
            final int port = 2700;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        });

//        bookingThread.start();
//        borrowingThread.start();
//        returningThread.start();
    }
}
