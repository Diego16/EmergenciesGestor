/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriber;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author diegogustavo
 */
public class Subscriber {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here
        String brokerIP = "127.0.0.1";
        int brokerPort = 0;
        int localPort = ThreadLocalRandom.current().nextInt(55000, 56000 + 1);
        try {
            Socket brokerSocket = null;
            PrintWriter clientOutput = null;
            BufferedReader brokerInput = null;
            InetAddress localAdd = InetAddress.getByName("127.0.0.1");
            try {
                do {
                    brokerPort = ThreadLocalRandom.current().nextInt(7980, 7985 + 1);
                    try {
                        brokerSocket = new Socket(brokerIP, brokerPort, localAdd, localPort);
                    } catch (IOException e) {
                        brokerSocket = null;
                    }
                } while (brokerSocket == null);
                clientOutput = new PrintWriter(brokerSocket.getOutputStream(), true);
                brokerInput = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
                System.out.println("Conectado a: " + brokerSocket);
            } catch (UnknownHostException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            } catch (IOException e) {
                System.out.println("*** No fue posible mantener la conexion ***");
            }
            String preferencias;
            String ciudad;
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("1. Terremoto\n2. Sismo\n3. Erupcion\n4. Tsunami\n5. Huracan\n6. Infestacion");
            System.out.print("=> Ingrese las emergencias de su interes: ");
            preferencias = infoInput.readLine();
            System.out.print("=> Ingrese la ciudad donde habita: ");
            ciudad = infoInput.readLine();
            clientOutput.println(preferencias + " " + ciudad);
            while (true) {
                if (brokerInput.readLine() == null) {
                    ArrayList<Integer> attempts = new ArrayList<>();
                    attempts.add(0);
                    int cantAttempts = 0;
                    do {
                        brokerSocket = null;
                        brokerPort = ThreadLocalRandom.current().nextInt(7980, 7985 + 1);
                        localPort = ThreadLocalRandom.current().nextInt(55000, 56000 + 1);
                        try {
                            if (!attempts.contains(brokerPort)) {
                                attempts.add(brokerPort);
                                cantAttempts++;
                                brokerSocket = new Socket(brokerIP, brokerPort, localAdd, localPort);
                            }
                        } catch (IOException e) {
                            brokerSocket = null;
                        }
                        if (brokerSocket != null) {
                            brokerInput = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
                            clientOutput = new PrintWriter(brokerSocket.getOutputStream(), true);
                            clientOutput.println(preferencias + " " + ciudad);
                            break;
                        }
                        if(cantAttempts==6)
                        {
                            System.out.println("*** No hay ningun broker disponible!!! ***");
                            System.exit(-1);
                        }
                    } while (cantAttempts < 6 && brokerSocket==null);
                } else {
                    System.out.println("=>Nuevo evento!\n==>" + brokerInput.readLine());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
