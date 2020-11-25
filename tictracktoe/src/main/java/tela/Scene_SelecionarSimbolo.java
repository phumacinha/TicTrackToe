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
import javafx.scene.paint.Color;
import tela.elemento.Atributo;
import tela.elemento.Botao;
import tela.elemento.Logotipo;
import tela.elemento.Simbolo.Jogador;
import tela.elemento.Texto;
import ticTrackToe.TicTrackToe;

/**
 *
 * @author pedro
 */
public class Scene_SelecionarSimbolo extends SceneUtil {
    Botao procurarAdversario;
    private HBox opcoes;
    
    
    public Scene_SelecionarSimbolo() {
        super();
    }
    
    private Botao gerarBotaoProcurarAdversario () {
        Botao botao = new Botao("Procurar adversário", 348, 60);
        
        botao.onClick(new EventHandler<MouseEvent> () { 
            @Override
            public void handle(MouseEvent t) {
                botao.disable();
                disableSelect(true);
                TicTrackToe.abrirTela(new Scene_ProcurandoAdversario());
            }
        });
        
        botao.disable();
        
        return botao;
    }
    
    private void disableSelect(boolean disable) {
        opcoes.getChildren().forEach(botao -> {
            ((BotaoSelecao) botao).disable(disable);
        });
    }
    
    private BotaoSelecao gerarBotaoSimbolo (Jogador jogador) {
        BotaoSelecao botao = new BotaoSelecao(jogador);
        EventHandler selecionar = new EventHandler<MouseEvent> () { 
            @Override
            public void handle(MouseEvent t) {
                Scene_Tabuleiro.SIMBOLO = jogador;
                procurarAdversario.disable(false);
                opcoes.getChildren().forEach(irmao -> {
                    ((BotaoSelecao) irmao).select(irmao.equals(botao));
                });
            }
        };
        botao.onClick(selecionar);
        return botao;
    }

    private Botao gerarBotaoVoltar () {
        Botao botao = new Botao("Voltar");
        botao.onClick(new Scene_Inicial());
        return botao;
    }
    
    @Override
    protected Node conteudoInicial() {
        
        opcoes = new HBox(gerarBotaoSimbolo(Jogador.X), gerarBotaoSimbolo(Jogador.O));
        opcoes.setSpacing(90);
        opcoes.setAlignment(Pos.CENTER);
        
        procurarAdversario = gerarBotaoProcurarAdversario();
        
        VBox meio = new VBox(new Texto("Com qual símbolo você quer jogar?"), opcoes, procurarAdversario, gerarBotaoVoltar());
        meio.setAlignment(Pos.CENTER);
        meio.setSpacing(30);
        meio.setAlignment(Pos.CENTER);
        VBox.setVgrow(meio, Priority.SOMETIMES);
        
        
        
        Logotipo logotipo = new Logotipo(24);
        VBox.setVgrow(logotipo, Priority.NEVER);
        
        VBox conteudo = new VBox(logotipo, meio);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setPadding(new Insets(60, 0, 60, 0));
        
        return conteudo;
    }

    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {}
    
    
    private class BotaoSelecao extends Botao {
        boolean isSelected, disable;
        
        public BotaoSelecao (Jogador jogador) {
            super(jogador.toString(), 288, 240, 240);
            isSelected = false;
            
            adicionarEvento(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    select(!isSelected);
                }
            });
        }
        
        public void select (boolean select) {
            this.isSelected = select;
            
            disable(select);
            setTextColor(select ? Color.WHITE : Atributo.ROXO_2);
            setBackgroundColor(select ? Atributo.VERDE : Color.WHITE);
        }
        
        @Override
        public void disable (boolean disable) {
            if (disable || (!disable && !isSelected))
                super.disable(disable);
        }
    }
    
}
