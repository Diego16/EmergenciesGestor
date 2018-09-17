/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

/**
 *
 * @author diegogustavo
 */
public class Client extends Thread{

    private Broker clientBroker;
    private int brokerPort;

    public Client(Broker aBroker) {
        this.clientBroker = aBroker;
        this.brokerPort = aBroker.clientPort;
        start();
    }

}
