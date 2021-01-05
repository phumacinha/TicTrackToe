/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.function.Consumer;
import etgames.mensagens.MensagemParaServidor;

/**Classe responsável por criar uma conexão com o servidor.
 *
 * @author Pedro Antônio de Souza
 */
public class ClientConnection {
    
    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final int SERVER_PORT = 4545;
    private final ConnectionThread connThread;
    private final Consumer<Object> onReceiveCallback;
    private boolean isConnected = false;
    
    /**Construtor da classe.
     * 
     * @param onReceiveCallback 
     */
    public ClientConnection(Consumer<Object> onReceiveCallback) {
        this.connThread = new ConnectionThread();
        this.onReceiveCallback = onReceiveCallback;
    }
    
    /**Getter do SERVER_ADDRESS.
     * 
     * @return Endereço de ip do servidor.
     */
    private String getIpAddress() {
           return SERVER_ADDRESS; 
    }

    /**Getter do SERVER_PORT.
     * 
     * @return Porta do servidor.
     */
    private int getPort() {
        return SERVER_PORT;
    }
    
    /**Inicializa a thread de conexão.
     * 
     * @throws Exception 
     */
    public void startConnection() throws Exception {
        connThread.start();
    }
    
    /**Método para verificar conexão.
     * 
     * @return True se estiver conectado, False caso contrário.
     */
    public boolean connected() {
        return isConnected;
    }
    
    /**Método para envio de mensagens ao servidor.
     * 
     * @param data Objeto contendo a mensagem a ser enviada.
     * @throws Exception 
     */
    public void send(MensagemParaServidor data) throws Exception {
        //System.out.println("Enviando: " + data.toString());
        connThread.out.writeObject(data);
    }
    
    /**Método para fechar a thread de conexão.
     * 
     */
    public void closeConnection() {
        connThread.close();
    }
    
    /**Classe responsável por criar nova thread para conexão com servidor.
     * 
     * @author Pedro Antônio de Souza
     */
    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;
        private boolean stop = false;
        
        /**
         * Sobrecarga do método Thread.run().
         */
        @Override
        public void run() {
            while (!stop) {
                try {
                    //Instancia novo socket.
                    Socket socket = new Socket(getIpAddress(), getPort());
                    //Objeto para saída de dados.
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //Objeto para entrada de dados.
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    this.socket = socket;
                    this.out = out;
                    isConnected = true;
                    
                    //Laço infinito para verificar a recepção de dados.
                    while (true) {
                        Object data = in.readObject();
                        onReceiveCallback.accept(data);
                    }
                }
                catch (ConnectException ce) {
                    System.out.println("Não foi possível conectar ao servidor, tentando novamente...");
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException ex) {
                        //TO DO (reiniciar software).
                    }
                }                
                catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
                    close();
                }
            }
        }
        
        /**Método para fechamento de socket.
         * 
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {}
            stop = true;
            isConnected = false;
        }
    }
    
}
