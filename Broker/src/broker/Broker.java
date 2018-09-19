/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author diegogustavo
 */
public class Broker extends Thread {

    ArrayList<ClientBroker> clientConns = new ArrayList<>();
    int openPort = ThreadLocalRandom.current().nextInt(7980, 7985 + 1);

    public Broker() {
        start();
    }

    public void run() {
        ServerSocket brokerServer = null;
        try {
            brokerServer = new ServerSocket(openPort);
            System.out.println("IP Broker: " + brokerServer.getLocalSocketAddress().toString());
            while (true) {
                Socket newConnection = brokerServer.accept();
                if (newConnection.getPort() >= 53000 && newConnection.getPort() < 54000) {
                    System.out.println("=> Nuevo publicador: " + newConnection.getInetAddress() + ":" + newConnection.getPort());
                    new SourceBroker(this, newConnection);
                } else if (newConnection.getPort() >= 55000 && newConnection.getPort() < 56000) {
                    System.out.println("=> Nuevo suscriptor: " + newConnection.getInetAddress() + ":" + newConnection.getPort());
                    clientConns.add(new ClientBroker(this, newConnection));
                }
            }
        } catch (IOException ex) {
            System.out.println("*** No se pudo establecer la conexion. ***");
        } finally {
            try {
                if (brokerServer != null) {
                    brokerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmergency(String emergency) {
        String formatedEmergency = "";
        ArrayList<String> tokenizedEmergency = new ArrayList<>();
        StringTokenizer emergencyTokenizer = new StringTokenizer(emergency);
        while (emergencyTokenizer.hasMoreTokens()) {
            tokenizedEmergency.add(emergencyTokenizer.nextToken());
        }
        for (int i = 1; i < tokenizedEmergency.size(); i++) {
            formatedEmergency += tokenizedEmergency.get(i) + " ";
        }
        for (ClientBroker client : clientConns) {
            if (client.tags.contains(tokenizedEmergency.get(1))) {
                new BrokerSender(client.subscriberSocket, formatedEmergency);
                System.out.println(">>> Emergencia a enviar: " + formatedEmergency);
            } else if (client.clientCity.equals(tokenizedEmergency.get(2))) {
                new BrokerSender(client.subscriberSocket, formatedEmergency);
                System.out.println(">>> Emergencia a enviar: " + formatedEmergency);
            }
        }

    }
}
