/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author diegogustavo
 */
class SourceBroker extends Thread {

    private final Broker myBroker;
    private final Socket brokerSocket;

    public SourceBroker(Broker aBroker, Socket aSocket) {
        this.myBroker = aBroker;
        this.brokerSocket = aSocket;
        start();
    }

    public void run() {
        InputStream brokerInput = null;
        OutputStream sourceOutput = null;
        try {
            brokerInput = brokerSocket.getInputStream();
            sourceOutput = brokerSocket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(brokerInput));
            String newEmergency;
            while ((newEmergency = br.readLine()) != null) {
                System.out.println("===> Nueva emergencia! Fuente=>" + brokerSocket.getInetAddress() + ":" + brokerSocket.getPort() + ": " + newEmergency);
                myBroker.sendEmergency(newEmergency);
            }
        } catch (IOException ex) {
            System.out.println("*** Ha ocurrido un error ***");
        } finally {
            try {
                brokerInput.close();
                sourceOutput.close();
                brokerSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
