package com.mintype.studio2site;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class HTTPServer {
    private final int port;
    private boolean isRunning;
    private HttpServer httpServer;
    private static String indexAbsolutePath;


    public HTTPServer(int port, String absolutePath) {
        this.port = port;
        indexAbsolutePath = absolutePath;

        isRunning = false;

        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new MyHandler());
            httpServer.setExecutor(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServer() {
        httpServer.start();

        isRunning = true;

        System.out.println("Server is listening on port " + port);
    }

    public void stopServer() {
        httpServer.stop(0);

        isRunning = false;

        System.out.println("Server has stopped running.");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                String response = "";
                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", "text/html");
                System.out.println(indexAbsolutePath);
                FileInputStream fileStream = new FileInputStream(indexAbsolutePath);
                if (fileStream != null) {
                    System.out.println("fillefwef");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response += line;
                    }
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    String errorResponse = "Error: HTML file not found.";
                    exchange.sendResponseHeaders(404, errorResponse.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(errorResponse.getBytes());
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
