/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publisher;

import java.io.*;
import java.net.*;

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
        int brokerPort = 7987;
        try {
            Socket brokerSocket = null;
            PrintWriter out = null;
            try {
                brokerSocket = new Socket(brokerIP, brokerPort);
                out = new PrintWriter(brokerSocket.getOutputStream(), true);
            } catch (UnknownHostException e) {
                System.err.println("*** No fue posible realizar la conexion ***");
            } catch (IOException e) {
                System.err.println("No se pudo hacer contacto ");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
