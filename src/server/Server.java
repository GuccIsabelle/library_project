package server;

import server.document.book.Library;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library");
        System.out.println(library.toString());

        Thread bookingThread = new Thread(() -> {
            final int port = 2500;
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        });

        Thread borrowingThread = new Thread(() -> {
            final int port = 2600;
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                }
            } catch (IOException e) {
                System.out.println("Server not found or some shit...\n" +
                        "IOException error be like: " + e.getMessage());
            }
        });

        Thread returningThread = new Thread(() -> {
            final int port = 2700;
            try (ServerSocket serverSocket = new ServerSocket(port)) {
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
