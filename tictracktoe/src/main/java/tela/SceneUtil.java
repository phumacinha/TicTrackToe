package tela;


import MensagemSocket.MensagemParaCliente;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import tela.elemento.Atributo;
import tela.elemento.PopUp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author pedro
 */
public abstract class SceneUtil {
    private final StackPane root;
    private PopUp popup = new PopUp();
    
    public SceneUtil () {
        root = new StackPane();
    }
    
    public final Scene getScene() {
        Scene scene = new Scene(root, Atributo.SCREEN_WIDTH, Atributo.SCREEN_HEIGHT);
        scene.setFill(Atributo.BACKGROUND_RADIAL);
        
        clearRoot();
        insereConteudo(conteudoInicial());
        
        return scene;
    }
    
    public void clearRoot() {
        root.setBackground(Background.EMPTY);
        root.getChildren().clear();
    }
    
    protected void insereConteudo (Node conteudo) {
        root.getChildren().add(0, conteudo);
    }
    
    protected void abrirPopUp (PopUp popup) {
        if (root.getChildren().contains(this.popup)) {
            root.getChildren().remove(this.popup);
        }
        this.popup = popup;
        root.getChildren().add(this.popup);
    }
    
    //<editor-fold defaultstate="collapsed" desc="metodos abstratos">    
    protected abstract Node conteudoInicial();
    
    public abstract void trataMensagem (MensagemParaCliente mensagem);
//</editor-fold>
}
