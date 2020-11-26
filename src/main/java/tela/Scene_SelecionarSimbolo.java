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
 * Tela para seleção de símbolo.
 * 
 * @author Pedro Antônio de Souza
 */
public class Scene_SelecionarSimbolo extends SceneUtil {
    Botao procurarAdversario;
    private HBox opcoes;
    
    /**
     * Construtor da classe.
     */
    public Scene_SelecionarSimbolo() {
        super();
    }
    
    /**
     * Método que gera e retorna um objeto Botao que, ao ser clicado, procura um
     * adversário.
     * 
     * @return Objeto Botao que, ao ser clicado, procura um adversário.
     */
    private Botao gerarBotaoProcurarAdversario () {
        Botao botao = new Botao("Procurar adversário", 348, 60);
        
        botao.onClick(new EventHandler<MouseEvent> () { 
            @Override
            public void handle(MouseEvent t) {
                // Desativa o botao.
                botao.disable();
                // Desativa a seleção de símbolos.
                disableSelect(true);
                TicTrackToe.abrirTela(new Scene_ProcurandoAdversario());
            }
        });
        
        botao.disable();
        
        return botao;
    }
    
    /**
     * Desativa a seleção dos síbolos.
     * 
     * @param disable Booleando que indica se a seleção deve ser (true)
     * desativada ou não (false).
     */
    private void disableSelect(boolean disable) {
        opcoes.getChildren().forEach(botao -> {
            ((BotaoSelecao) botao).disable(disable);
        });
    }
    
    /**
     * Método que gera e retorna um objeto BotaoSelecao com algum símbolo
     * utilizado no jogo da velha.
     * 
     * @param jogador Objeto do tipo Jogador que define qual será o símbolo do 
     * botão retornado.
     * @return Objeto BotaoSelecao com o símbolo definido.
     */
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

    /**
     * Método que gera e retorna um objeto Botao que, ao ser clicado, retorna
     * para a tela inicial.
     * 
     * @return Objeto Botao que, ao ser clicado, retorna à tela inicial.
     */
    private Botao gerarBotaoVoltar () {
        Botao botao = new Botao("Voltar");
        botao.onClick(new Scene_Inicial());
        return botao;
    }
    
    /**
     * Gera layout exibindo opções de seleção de símbolo.
     * 
     * @return VBox com o conteúdo inicial da tela.
     */
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

    /**Método sobrecarregado para tratar mensagens recebidas do servidor.
     * 
     * @param mensagem Mensagem recebida do servidor.
     */
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
