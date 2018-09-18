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
public class ClientBroker extends Thread{

    private Broker myBroker;
    private Socket brokerSocket;

    public ClientBroker(Broker aBroker, Socket aSocket) {
        this.myBroker = aBroker;
        this.brokerSocket = aSocket;
        start();
    }

}
