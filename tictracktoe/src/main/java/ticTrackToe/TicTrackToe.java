package ticTrackToe;

import MensagemSocket.MensagemParaCliente;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.StageStyle;

import socket.ClientConnection;
import tela.SceneUtil;
import tela.Scene_Conectando;

/**
 *
 * @author pedro
 */
public class TicTrackToe extends Application {

//<editor-fold defaultstate="collapsed" desc="atributos">
    public static final ClientConnection CONNECTION = criarConexao();
    private static Stage primaryStage;    
    private static SceneUtil sceneAtual = null;
//</editor-fold>
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        CONNECTION.startConnection();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setResizable(false);
        //primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNIFIED);
        
        primaryStage.setTitle("TicTrackToe");
        abrirTela(new Scene_Conectando());
        primaryStage.show();
    }
    
    @Override
    public void stop() throws Exception {
        CONNECTION.closeConnection();
    }
    
    private static ClientConnection criarConexao() {
        return new ClientConnection(data -> {
            Platform.runLater(() -> {
                if (sceneAtual != null) {
                    try {
                        sceneAtual.trataMensagem((MensagemParaCliente) data);
                    }
                    catch (Exception e) {}
                }
            });
        });
    }
    
    public static void abrirTela (SceneUtil tela) {
        sceneAtual = tela;
        primaryStage.setScene(sceneAtual.getScene());
    }
    
    public static void fecharJogo () {
        primaryStage.close();
    }
}
