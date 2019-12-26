package client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] argv) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            Socket clientSocket = new Socket("localhost", 2500);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            sentence = inFromUser.readLine();
            outToServer.writeUTF(sentence);
            modifiedSentence = inFromServer.readUTF();
            System.out.println(modifiedSentence);
            outToServer.flush();
        }
    }
}
