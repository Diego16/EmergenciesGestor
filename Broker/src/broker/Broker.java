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

    ArrayList<Socket> clientConns = new ArrayList<Socket>();
    int clientPort = 7897;
    int sourcePort = 7987;

    public Broker() {
        start();
    }

    public void run() {
        ServerSocket clientServer = null;
        ServerSocket sourceServer = null;
        try {
            clientServer = new ServerSocket(clientPort);
            sourceServer = new ServerSocket(sourcePort);
            while (true) {
                Socket newSource = sourceServer.accept();
                Socket newClient = clientServer.accept();
                if (!newSource.isClosed()) {
                    new SourceBroker(this, newSource);
                }
                if (!newClient.isClosed()) {
                    clientConns.add(newClient);
                }

            }
        } catch (IOException ex) {
            System.out.println("No se pudo establecer la conexion.");
        } finally {
            try {
                if (clientServer != null && sourceServer != null) {
                    clientServer.close();
                    sourceServer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void addClient(Socket clientSocket) {
        clientConns.add(clientSocket);
    }
}
