package tictracktoe;

import etgames.mensagens.MensagemParaCliente;
import java.util.function.Consumer;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import socket.ClientConnection;
import tela.SceneUtil;
import tela.Scene_Conectando;

/**
 * Classe de interface gráfica.
 * @author Pedro Antônio de Souza
 */
public class TicTrackToe extends Application {

//<editor-fold defaultstate="collapsed" desc="atributos">
    public static final ClientConnection CONNECTION = criarConexao();
    private static Stage primaryStage;    
    private static SceneUtil sceneAtual = null;
//</editor-fold>
    
    /**
     * Método principal.
     * @param args Array de dados da command-line para a aplicação.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Prepara a classe antes da exibição da interface gráfica.
     * @throws Exception 
     */
    @Override
    public void init() throws Exception {
        CONNECTION.startConnection();
    }
    
    /**
     * Inicia a interface gráfica.
     * @param stage Stage principal da aplicação onde as telas serão definidas.
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        //primaryStage.initStyle(StageStyle.UNIFIED);
        
        primaryStage.setTitle("TicTrackToe");
        abrirTela(new Scene_Conectando());
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
    
    /**
     * Prepara ambiente para a aplicação ser interrompida.
     * @throws Exception 
     */
    @Override
    public void stop() throws Exception {
        CONNECTION.closeConnection();
    }
    
    /**
     * Cria conexão com o servidor.
     * @return Conexão com o servidor.
     */
    private static ClientConnection criarConexao() {
        return new ClientConnection((Object data) -> {
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
    
    /**
     * Abre tela no stage principal.
     * @param tela Tela a ser aberta.
     */
    public static void abrirTela (SceneUtil tela) {
        sceneAtual = tela;
        primaryStage.setScene(sceneAtual.getScene());
    }
    
    /**
     * Fecha o stage principal.
     */
    public static void fecharJogo () {
        primaryStage.close();
    }
}
