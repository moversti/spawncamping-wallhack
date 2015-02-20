/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trakkeri;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arvy
 */
public class TrackerServer {

    private HttpServer s;

    public TrackerServer(int portti) {
        try {
            s = HttpServer.create(new InetSocketAddress(portti), 100);
            s.setExecutor(null);
        } catch (IOException ex) {
            Logger.getLogger(Trakkeri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ranaa() {
        if (s != null) {
            s.createContext("/announce", new TrackerHandler());
            s.start();
        }
    }

}
