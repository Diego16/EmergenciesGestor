/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriber;

import java.io.*;
import java.net.*;
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
        int brokerPort = 7987;
        int localPort = ThreadLocalRandom.current().nextInt(55000, 56000 + 1);
        try {
            Socket brokerSocket = null;
            PrintWriter clientOutput = null;
            BufferedReader brokerInput = null;
            InetAddress localAdd = InetAddress.getByName("127.0.0.1");
            try {
                brokerSocket = new Socket(brokerIP, brokerPort, localAdd, localPort);
                clientOutput = new PrintWriter(brokerSocket.getOutputStream(),true);
                brokerInput = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
                System.out.println("Conectado a: " + brokerSocket);
            } catch (UnknownHostException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            } catch (IOException e) {
                System.out.println("*** No fue posible realizar la conexion ***");
            }
            String preferencias;
            String ciudad;
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("1. /n2. /n3. /n4. /n5. ");
            System.out.print("=> Ingrese las emergencias de su interes: ");
            preferencias=infoInput.readLine();
            System.out.print("=> Ingrese la ciudad donde habita: ");
            ciudad=infoInput.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
