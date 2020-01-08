package client;

import server.user.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] argv) {
        if (connexion() != null) {
            System.out.println("Authentication successful.");
            startApplication();
        } else {
            System.out.println("Something went wrong.");
        }
    }

    private static User connexion() {
        Scanner inFromUser = new Scanner(System.in);

        try {
            Socket connexionSocket = new Socket("localhost", 6969);
            // sending user's credentials to the server for authentication
            new DataOutputStream(connexionSocket.getOutputStream()).writeUTF(inFromUser.nextLine());
            // watching for confirmation
            return (User) new ObjectInputStream(connexionSocket.getInputStream()).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void startApplication() {

    }
}

//    Socket clientSocket = new Socket("localhost", 2500);
//    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//    DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
//
//            sentence = inFromUser.readLine();
//                    outToServer.writeUTF(sentence);
//                    modifiedSentence = inFromServer.readUTF();
//                    System.out.println(modifiedSentence);