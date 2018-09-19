/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author diegogustavo
 */
public class BrokerSender extends Thread {

    private final Socket subscriberSocket;
    private String emergencyToSend;

    public BrokerSender(Socket clientSocket, String emergency) {
        this.emergencyToSend = emergency;
        this.subscriberSocket = clientSocket;
        start();
    }

    public void run() {
        PrintWriter senderOutput;
        try {
            senderOutput = new PrintWriter(subscriberSocket.getOutputStream(), true);
            System.out.println(">>>>> Enviando Noticia a " + subscriberSocket.getInetAddress().getHostAddress() + ": " + emergencyToSend);
            senderOutput.println(emergencyToSend);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
