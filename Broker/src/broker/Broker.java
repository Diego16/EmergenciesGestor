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

/**
 *
 * @author diegogustavo
 */
public class Broker extends Thread {

    ArrayList<ClientBroker> clientConns = new ArrayList<ClientBroker>();
    int openPort = 7987;

    public Broker() {
        start();
    }

    public void run() {
        ServerSocket sourceServer = null;
        try {
            sourceServer = new ServerSocket(openPort);
            while (true) {
                Socket newConnection = sourceServer.accept();
                if (newConnection.getPort()>=53000 && newConnection.getPort()<54000) {
                    System.out.println("Nuevo publicador: " + newConnection);
                    new SourceBroker(this, newConnection);
                } else if(newConnection.getPort()>=55000 && newConnection.getPort()<56000) {
                    System.out.println("Nuevo suscriptor: " + newConnection);
                    clientConns.add(new ClientBroker(this, newConnection));
                }
            }
        } catch (IOException ex) {
            System.out.println("No se pudo establecer la conexion.");
        } finally {
            try {
                if (sourceServer != null) {
                    sourceServer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
