/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela;

import MensagemSocket.MensagemParaCliente;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tela.elemento.Botao;
import tela.elemento.Logotipo;
import tela.elemento.PopUp;
import tela.elemento.Spinner;
import tela.elemento.Texto;
import ticTrackToe.TicTrackToe;

/**
 *
 * @author pedro
 */
public class Scene_Conectando extends SceneUtil {   
    public Scene_Conectando () {
        super();
    }
    
    
    /**
     * Gera layout informando que o jogo está se conectando com o servidor.
     */
    @Override
    protected final Node conteudoInicial () {
        HBox logotipo = new Logotipo(24);
        logotipo.setPadding(new Insets(60, 0, 15, 0));
        VBox.setVgrow(logotipo, Priority.NEVER);
        
        VBox texto = new VBox(new Texto("Aguarde,", 36), new Texto("estamos conectando ao servidor...", 24));
        texto.setAlignment(Pos.CENTER);
        texto.setPadding(new Insets(0, 0, 24, 0));
        
        VBox meio = new VBox(texto, new Spinner(120));
        meio.setAlignment(Pos.CENTER);
        meio.setPadding(new Insets(15, 0, 60, 0));
        VBox.setVgrow(meio, Priority.SOMETIMES);
        
        
        VBox conteudo = new VBox(logotipo, meio);
        conteudo.setAlignment(Pos.CENTER);
        
        return conteudo;
    }
    
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        switch (mensagem.getAcao()) {
            case CONECTADO:
                TicTrackToe.abrirTela(new Scene_Inicial());
                break;
        }
    }
}
