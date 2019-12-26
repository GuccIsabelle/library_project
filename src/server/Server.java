package server;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
    public static void main(String[] args) throws Exception {
        Library library = new Library("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\library");
        System.out.println(library.toString());

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(2500);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readUTF();
            capitalizedSentence = clientSentence.toUpperCase();
            outToClient.writeUTF(capitalizedSentence);
            outToClient.flush();
        }
    }

    @Override
    public void run() {

    }
}