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

    private Broker myBroker;
    private Socket brokerSocket;

    public SourceBroker(Broker aBroker, Socket aSocket) {
        this.myBroker = aBroker;
        this.brokerSocket = aSocket;
        start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = brokerSocket.getInputStream();
            out = brokerSocket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String informacion;
            while ((informacion = br.readLine()) != null) {
                System.out.println("======== Nueva emergencia! Fuente=>" + brokerSocket.getInetAddress()+":"+brokerSocket.getPort()+ ": " + informacion+" ============");
                //myBroker.enviarInfo(informacion);
                //out.write(informacion.getBytes());
            }
        } catch (IOException ex) {
            System.out.println("*** Ha ocurrido un error ***");
        } finally {
            try {
                in.close();
                out.close();
                brokerSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
