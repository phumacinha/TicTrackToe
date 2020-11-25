/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import MensagemSocket.MensagemParaServidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 * @author pedro
 */
public class ClientConnection {
    
    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final int SERVER_PORT = 4545;
    private final ConnectionThread connThread;
    private final Consumer<Object> onReceiveCallback;
    private boolean isConnected = false;
    
    public ClientConnection(Consumer<Object> onReceiveCallback) {
        this.connThread = new ConnectionThread();
        this.onReceiveCallback = onReceiveCallback;
    }
    
    private String getIP() {
           return SERVER_ADDRESS; 
    }

    private int getPort() {
        return SERVER_PORT;
    }
    
    public void startConnection() throws Exception {
        connThread.start();
    }
    
    public boolean connected() {
        return isConnected;
    }
    
    public void send(MensagemParaServidor data) throws Exception {
        //System.out.println("Enviando: " + data.toString());
        connThread.out.writeObject(data);
    }
    
    public void closeConnection() {
        connThread.close();
    }
    
    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;
        private boolean stop = false;
        
        @Override
        public void run() {
            while (!stop) {
                try {
                    Socket socket = new Socket(getIP(), getPort());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    this.socket = socket;
                    this.out = out;
                    isConnected = true;
                    
                    while (true) {
                        Object data = in.readObject();
                        onReceiveCallback.accept(data);
                    }
                }
                catch (ConnectException ce) {
                    System.out.println("Não foi possível conectar ao servidor, tentando novamente...");
                    try { Thread.sleep(2000); } catch (InterruptedException ex) {}
                }                
                catch (IOException | ClassNotFoundException e) {
                    close();
                }
            }
        }
        
        public void close() {
            try {
                socket.close();
            } catch (Exception ex) {}
            stop = true;
        }
    }
    
}
