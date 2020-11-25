package tela;

import MensagemSocket.MensagemParaCliente;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tela.elemento.Atributo;
import tela.elemento.Botao;
import tela.elemento.Logotipo;
import tela.elemento.PopUp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */


public class Scene_Inicial extends SceneUtil {
    public Scene_Inicial () {
        super();
    }
    
    private Botao botaoNovoJogo () {
        Botao botao = new Botao("NOVO JOGO", 36, 360, 90);
        botao.setBackgroundColor(Atributo.VERDE);
        botao.onClick(new Scene_SelecionarSimbolo());
        return botao;
    }
    
    private Botao botaoAjuda () {
        Botao botao = new Botao("Ajuda");
        botao.disable();
        //botao.onClick(tela ajuda);
        return botao;
    }
    
    private Botao botaoSobre () {
        Botao botao = new Botao("Sobre");
        botao.disable();
        //botao.onClick(tela sobre);
        return botao;
    }
    
    @Override
    protected final Node conteudoInicial () {
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(12);
        menu.getChildren().addAll(botaoAjuda(), botaoSobre());
        
        VBox conteudo = new VBox();
        conteudo.setAlignment(Pos.CENTER);
        conteudo.getChildren().addAll(new Logotipo(48), botaoNovoJogo(), menu);
        
        VBox.setVgrow(conteudo, Priority.ALWAYS);
        conteudo.setSpacing(30);
        
        return conteudo;
    }
    
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        
    }
    
}
