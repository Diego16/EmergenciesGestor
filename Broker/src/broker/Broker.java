/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author diegogustavo
 */
public class Broker extends Thread {

    ArrayList<ClientBroker> clientConns = new ArrayList<ClientBroker>();
    int openPort = ThreadLocalRandom.current().nextInt(7980, 7985 + 1);

    public Broker() {
        start();
    }

    public void run() {
        ServerSocket brokerServer = null;
        try {
            brokerServer = new ServerSocket(openPort);
            System.out.println("IP Broker: "+brokerServer.getInetAddress()+":"+brokerServer.getLocalPort());
            while (true) {
                Socket newConnection = brokerServer.accept();
                if (newConnection.getPort()>=53000 && newConnection.getPort()<54000) {
                    System.out.println("Nuevo publicador: " + newConnection.getInetAddress()+":"+newConnection.getPort());
                    new SourceBroker(this, newConnection);
                } else if(newConnection.getPort()>=55000 && newConnection.getPort()<56000) {
                    System.out.println("Nuevo suscriptor: " + newConnection.getInetAddress()+":"+newConnection.getPort());
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
