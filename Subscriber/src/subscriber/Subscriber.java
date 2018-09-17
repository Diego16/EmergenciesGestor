/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscriber;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author diegogustavo
 */
public class Subscriber extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket s;
    String data;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) { // args message and hostname
        Subscriber c = new Subscriber();
    }

    /**
     *
     */
    public Subscriber() {
        try {
            int serverPort = 7897;
            s = new Socket("127.0.0.1", serverPort);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
            this.start();
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }
    }

    public void run() {
        try {
            out.writeUTF("PING");
            data = in.readUTF();
            System.out.println("Received: " + data);
            System.out.println(s.toString());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}
