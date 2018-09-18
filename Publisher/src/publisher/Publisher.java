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
        ArrayList<String> emergencies = new ArrayList<String>();
        int brokerPort = 7987;
        int localPort = ThreadLocalRandom.current().nextInt(53000, 54000 + 1);
        try {
            InetAddress localAdd = InetAddress.getByName("127.0.0.1");
            Socket brokerSocket = null;
            PrintWriter publisherOutput = null;
            try {
                brokerSocket = new Socket(brokerIP, brokerPort, localAdd, localPort);
                System.out.println("Conectado a: " + brokerSocket);
                publisherOutput = new PrintWriter(brokerSocket.getOutputStream(), true);
            } catch (UnknownHostException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            } catch (IOException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            }
            /* String archiveName;
            System.out.print("Ingrese el archivo que  contiene los eventos: ");
            BufferedReader nameBR = new BufferedReader(new InputStreamReader(System.in));
            archiveName = nameBR.readLine();
            nameBR.close();
            File emergenciesFile = new File(System.getProperty("user.dir") + "\\" + archiveName);
            BufferedReader fileBR = new BufferedReader(new FileReader(emergenciesFile));
            String newEmergency;
            while ((newEmergency = fileBR.readLine()) != null) {
                emergencies.add(newEmergency);
            }
            for (int i = 0; i < 100; i++) {
                int cont = 10;
                if (Integer.parseInt(String.valueOf(emergencies.get(i).charAt(0))) == cont) {
                    publisherOutput.println(emergencies.get(i));
                }
                cont++;
            }*/
            publisherOutput.println("Emergencia 1");
            publisherOutput.println("Emergencia 2");
            publisherOutput.println("Emergencia 3");
            publisherOutput.println("Emergencia 4");
            publisherOutput.println("Emergencia 5");
            publisherOutput.println("Emergencia 6");
            publisherOutput.close();
            brokerSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
