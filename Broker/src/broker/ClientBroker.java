/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author diegogustavo
 */
public class ClientBroker extends Thread {

    private final Broker myBroker;
    private final ArrayList<String> tagsCodes = new ArrayList<>();
    public final Socket subscriberSocket;
    public ArrayList<String> tags = new ArrayList<>();
    public String clientCity;

    public ClientBroker(Broker aBroker, Socket aSocket) {
        this.myBroker = aBroker;
        this.subscriberSocket = aSocket;
        start();
    }

    public void run() {
        InputStream clientInput;
        OutputStream brokerOutput = null;
        try {
            clientInput = subscriberSocket.getInputStream();
            brokerOutput = subscriberSocket.getOutputStream();
            BufferedReader clientBufferedReader = new BufferedReader(new InputStreamReader(clientInput));
            String clientInfo;
            clientInfo = clientBufferedReader.readLine();
            StringTokenizer infoTokenizer = new StringTokenizer(clientInfo);
            while (infoTokenizer.hasMoreTokens()) {
                tagsCodes.add(infoTokenizer.nextToken());
            }
            clientCity = tagsCodes.get(tagsCodes.size() - 1);
            tagsCodes.remove(tagsCodes.size() - 1);
            for (String code : tagsCodes) {
                switch (code) {
                    case "1":
                        tags.add("Terremoto");
                        break;
                    case "2":
                        tags.add("Sismo");
                        break;
                    case "3":
                        tags.add("Erupcion");
                        break;
                    case "4":
                        tags.add("Tsunami");
                        break;
                    case "5":
                        tags.add("Huracan");
                        break;
                    case "6":
                        tags.add("Infestacion");
                        break;
                    default:
                        break;
                }
            }
            System.out.println("===>Informacion recibida de " + subscriberSocket.getInetAddress().getHostAddress() + "\n====> Tags: " + tags.toString() + "\n====> Ciudad: " + clientCity);
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
}
