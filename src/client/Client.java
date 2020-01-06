package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] argv) {
        Scanner inFromUser = new Scanner(System.in);
        String[] userCredentials = inFromUser.nextLine().split("\\s+");

        try {
            Socket clientSocket = new Socket("localhost", 6969);
        } catch (IOException e) {
            e.printStackTrace();
        }
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