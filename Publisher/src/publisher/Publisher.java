/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publisher;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author diegogustavo
 */
public class Publisher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String brokerIP = "127.0.0.1";
        ArrayList<String> emergencies = new ArrayList<>();
        int brokerPort;
        int localPort;
        try {
            InetAddress localAdd = InetAddress.getByName("127.0.0.1");
            ArrayList<Socket> allBrokers = new ArrayList<>();
            ArrayList<PrintWriter> allOutputs = new ArrayList<>();
            ArrayList<Integer> attempts = new ArrayList<>();
            attempts.add(0);
            int cantAttempts = 0;
            try {
                do {
                    Socket brokerSocket = null;
                    PrintWriter publisherOutput;
                    brokerPort = ThreadLocalRandom.current().nextInt(7980, 7985 + 1);
                    localPort = ThreadLocalRandom.current().nextInt(53000, 54000 + 1);
                    try {
                        //for (int attempt : attempts) {
                        if (!attempts.contains(brokerPort)) {
                            attempts.add(brokerPort);
                            cantAttempts++;
                            brokerSocket = new Socket(brokerIP, brokerPort, localAdd, localPort);
                        }
                        //}
                    } catch (IOException e) {
                        brokerSocket = null;
                    }
                    if (brokerSocket != null) {
                        System.out.println("Conectado a: " + brokerSocket);
                        publisherOutput = new PrintWriter(brokerSocket.getOutputStream(), true);
                        allBrokers.add(brokerSocket);
                        allOutputs.add(publisherOutput);
                    }
                } while (cantAttempts < 6);
            } catch (UnknownHostException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            } catch (IOException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            }
            String archiveName;
            System.out.print("Ingrese el archivo que contiene los eventos: ");
            BufferedReader nameBR = new BufferedReader(new InputStreamReader(System.in));
            archiveName = nameBR.readLine();
            nameBR.close();
            File emergenciesFile = new File(System.getProperty("user.dir") + "/" + archiveName);
            BufferedReader fileBR = new BufferedReader(new FileReader(emergenciesFile));
            String newEmergency;
            while ((newEmergency = fileBR.readLine()) != null) {
                emergencies.add(newEmergency);
            }
            int cont = 0;
            int pick = 0;
            int emergencyStamp = 0;
            while (cont < emergencies.size()) {
                pick = ThreadLocalRandom.current().nextInt(1, 150 + 1);
                emergencyStamp = Integer.parseInt(String.valueOf(emergencies.get(cont).charAt(0))) * 10 + Integer.parseInt(String.valueOf(emergencies.get(cont).charAt(1)));
                if (pick == emergencyStamp) {
                    for (PrintWriter publisherOutput : allOutputs) {
                        publisherOutput.println(emergencies.get(cont));
                    }
                    cont++;
                    Thread.sleep(10000);
                }
            }
            for (Socket brokerSocket : allBrokers) {
                brokerSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
