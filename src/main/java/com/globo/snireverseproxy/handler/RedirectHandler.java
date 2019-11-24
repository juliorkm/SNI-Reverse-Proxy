package com.globo.snireverseproxy.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectHandler extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpURLConnection.setFollowRedirects(true);
        
        String requestAddr = System.getenv("SNI_REVERSE_PROXY_BACKEND_ADDRESS");
        if (requestAddr==null) requestAddr = "localhost";
        int requestPort;
        try {
            requestPort = Integer.parseInt(System.getenv("SNI_REVERSE_PROXY_BACKEND_PORT"));
        } catch (Exception e) {
            requestPort = 8081;
        }
        String requestMethod = req.getMethod();
        Map<String, String[]> requestParameters = req.getParameterMap();

        URL url = new URL("http://"
                + requestAddr + ":" + requestPort + req.getRequestURI()
                + (req.getQueryString() != null ? "?" + req.getQueryString() : ""));
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(requestMethod);
        conn.connect();

        int status = conn.getResponseCode();
        resp.setStatus(status);
        
        String encoding = conn.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        resp.setCharacterEncoding(encoding);
        
        BufferedReader br;
        if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                    encoding));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
          sb.append(output);
        }
        PrintWriter out = resp.getWriter();
        out.print(sb.toString());
        out.close();

        conn.disconnect();
    }
}
