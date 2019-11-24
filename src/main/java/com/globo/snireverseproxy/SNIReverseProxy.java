package com.globo.snireverseproxy;

import org.eclipse.jetty.servlet.ServletHandler;

import com.globo.snireverseproxy.handler.RedirectHandler;

import org.eclipse.jetty.server.Server;

/**
 * Reverse Proxy that checks if user has SNI certificate and redirects to a server.
 *
 */
public class SNIReverseProxy  {
    public static void main( String[] args ) throws Exception {
        int port;
        try {
            port = Integer.parseInt(System.getenv("SNI_REVERSE_PROXY_PORT"));
        } catch (Exception e) {
            port = 8080;
        }
        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(RedirectHandler.class, "/");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
