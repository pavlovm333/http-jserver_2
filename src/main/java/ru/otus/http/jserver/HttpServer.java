package ru.otus.http.jserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер запущен на порту: {}", port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    LOGGER.info("Подключился новый клиент");
                    byte[] buffer = new byte[8192];
                    int n = socket.getInputStream().read(buffer);
                    if (n < 0) {
                        continue;
                    }
                    HttpRequest request = new HttpRequest(new String(buffer, 0, n));
                    request.info(true);
                    dispatcher.execute(request, socket.getOutputStream());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
