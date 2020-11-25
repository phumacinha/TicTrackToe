/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela;

import MensagemSocket.Acao;
import MensagemSocket.MensagemParaCliente;
import MensagemSocket.MensagemParaServidor;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tela.elemento.Atributo;
import tela.elemento.Botao;
import tela.elemento.Logotipo;
import tela.elemento.PopUp;
import tela.elemento.Simbolo;
import tela.elemento.Simbolo.Jogador;
import tela.elemento.Texto;
import ticTrackToe.TicTrackToe;

/**
 *
 * @author pedro
 */
public class Scene_Tabuleiro extends SceneUtil {
    public static Jogador SIMBOLO;
    private Integer minhaId, turnoId;
    private boolean fimDeJogo, deuVelha;
    private VBox tela;
    private HBox conteudo;
    private Tabuleiro tabuleiro;
    private VBox conteudoDireito;
    private StackPane conteudoEsquerdo;
    private Texto informacao;
    private HBox placar;
    private Placar placarVoce, placarEmpate, placarOponente;
    private VBox menu;
    private Botao ajuda, sair;
    
    public Scene_Tabuleiro() {
        super();
    }
    
    @Override
    protected Node conteudoInicial() {
        Logotipo logotipo = new Logotipo(24);
        logotipo.setAlignment(Pos.CENTER);
        
        EventHandler e = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                novoJogo();
            }
        };
        
        logotipo.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
        
        informacao = new Texto("");
        
        placarVoce = new Placar(SIMBOLO);
        placarEmpate = new Placar(null);
        placarOponente = new Placar(SIMBOLO.getOponente());
        placar = new HBox(placarVoce, placarEmpate, placarOponente);
        placar.setSpacing(30);
        placar.setAlignment(Pos.CENTER);
        
        ajuda = new Botao("Ajuda");
        ajuda.disable();
        //TO DO ajuda.onClick();
        sair = new Botao("Sair");
        sair.onClick(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                abrirPopUp(popupSair());
            }
        });
        //TO DO sair.onClick();
        menu = new VBox(ajuda, sair);
        menu.setSpacing(30);
        menu.setAlignment(Pos.CENTER);
        
        conteudoDireito = new VBox(informacao, placar, menu);
        conteudoDireito.setSpacing(60);
        conteudoDireito.setAlignment(Pos.CENTER);
        informacao.wrappingWidthProperty().bind(conteudoDireito.widthProperty());
        
      
        conteudoEsquerdo = new StackPane();
        conteudoEsquerdo.setMaxSize(474, 474);
        novoJogo();
        
        conteudo = new HBox(conteudoEsquerdo, conteudoDireito);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setSpacing(60);
        
        
        VBox.setVgrow(logotipo, Priority.NEVER);
        VBox.setVgrow(conteudo, Priority.ALWAYS);
        tela = new VBox(logotipo, conteudo);
        tela.setPadding(new Insets(60, 0, 60, 0));
        tela.setAlignment(Pos.CENTER);
        return tela;
    }
    
    private PopUp popupSair() {
        PopUp popup = new PopUp("Confirme sua ação", "Deseja abandonar a partida?");
        popup.setCloseable("Cancelar");
        Botao btnSim = new Botao("Sim");
        btnSim.onClick(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                try {
                    TicTrackToe.CONNECTION.send(new MensagemParaServidor(Acao.ABANDONO));
                    TicTrackToe.abrirTela(new Scene_Inicial());
                }
                catch (Exception e) {}
            }
        });
        
        popup.addBotao(btnSim);
        
        return popup;
    }
    
    private boolean minhaVez() {
        return minhaId.equals(turnoId);
    }

    private void novoJogo () {
        fimDeJogo = false;
        deuVelha = false;
        tabuleiro = new Tabuleiro();
        conteudoEsquerdo.getChildren().clear();
        conteudoEsquerdo.getChildren().add(tabuleiro);
    }
    
    private void atualizaInformacao () {
        if (fimDeJogo || deuVelha)
            informacao.setText("A partida");
        else if (minhaVez())
            informacao.setText("Jogue, é sua vez!");
        else
            informacao.setText("Aguarde sua vez!");
    }
    
    private void novoTurno (Integer novoTurno) {
        turnoId = novoTurno;
        if (minhaVez()) tabuleiro.desativarTabuleiro(false);
        else tabuleiro.desativarTabuleiro(true);
    }
    
    private void recebeJogada (List<Object> param) {
        if (!deuVelha && !fimDeJogo) {
            Integer idJogador = (Integer) param.get(0);
            Integer pos = (Integer) param.get(1);
            List<Integer> resultado = (List<Integer>) param.get(2);

            if (!idJogador.equals(minhaId)) {
                tabuleiro.preencher(pos, SIMBOLO.getOponente());
            }

            checaFimDeJogo(resultado);
        }
    }
    
    private void checaFimDeJogo (List<Integer> resposta) {
        if (resposta.get(1) == -1) {
            deuVelha = true;
            placarEmpate.incrementar();
        }
        else if (resposta.get(1) != 0) {
            if (minhaId.equals(resposta.get(0)))
                placarVoce.incrementar();
            else
                placarOponente.incrementar();
            fimDeJogo = true;
            haGanhador(resposta.get(1));
        }
    }

    /**
     * Altera layout quando há vencedor.
     * @param tipo Tipo de vitória.
     * 1 quando o vencedor completou a primeira linha do tabuleiro.
     * 2 quando o vencedor completou a segunda linha do tabuleiro.
     * 3 quando o vencedor completou a terceira linha do tabuleiro.
     * 4 quando o vencedor completou a primeira coluna do tabuleiro.
     * 5 quando o vencedor completou a segunda coluna do tabuleiro.
     * 6 quando o vencedor completou a terceira coluna do tabuleiro.
     * 7 quando o vencedor completou a diagonal principal do tabuleiro.
     * 8 quando o vencedor completou a diagonal secundaria do tabuleiro.
     */
    private void haGanhador (int tipo) {
        List<Integer> camposGanhador = new ArrayList<>();
        switch (tipo) {
            case 1:
                //primeira linha
                camposGanhador = List.of(0, 1, 2);
                break;
            case 2:
                //segunda linha
                camposGanhador = List.of(3, 4, 5);
                break;
            case 3:
                //terceira linha
                camposGanhador = List.of(6, 7, 8);
                break;
            case 4:
                //primeira coluna
                camposGanhador = List.of(0, 3, 6);
                break;
            case 5:
                //segunda coluna
                camposGanhador = List.of(1, 4, 7);
                break;
            case 6:
                //terceira coluna
                camposGanhador = List.of(2, 5, 8);
                break;
            case 7:
                //diagonal principal
                camposGanhador = List.of(0, 4, 8);
                break;
            case 8:
                //diagonal secundaria
                camposGanhador = List.of(2, 4, 6);
                break;
        }
        
        tabuleiro.vitoria(camposGanhador);
    }
    
    private void abandono () {
        try {            
            Botao procurar = new Botao("Procurar novo adversário", 350);
            procurar.onClick(new Scene_ProcurandoAdversario());

            Botao voltar = new Botao("Voltar ao início");
            voltar.onClick(new Scene_Inicial());

            PopUp popup = new PopUp("Seu adversário abandonou a sala.");
            popup.addBotao(procurar);
            popup.addBotao(voltar);
            abrirPopUp(popup);
        } catch (Exception ex) {
            
        }
    }
    
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        System.out.println("Mensagem: " + mensagem.toString());
        switch (mensagem.getAcao()) {
            case JOGADA:
                List<Object> param = (List<Object>) mensagem.getParametro();
                recebeJogada(param);
                break;
            case SET_ID:
                minhaId = ((Integer) mensagem.getParametro());
                break;
            case NOVO_TURNO:
                novoTurno((Integer) mensagem.getParametro());
                break;
            case ABANDONO:
                abandono();
                break;
            default:
                System.out.println("Mensagem não tratável." + mensagem.toString());
                break;
        }
        
        atualizaInformacao();
    }
    
    private class Campo extends Botao {
        private Jogador jogador = null;
        private final int posicao;
        
        public Campo(int posicao) {
            super("", 180, 150, 150);
            this.posicao = posicao;
            setMaxSize(150, 150);
            EventHandler preencher = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    preencherCampo(SIMBOLO);
                }
            };
            onClick(preencher);
        }
        
        public void preencherCampo (Jogador jogador) {
            if (!fimDeJogo && !deuVelha && this.jogador == null) {
                this.jogador = jogador;
                setText(jogador.toString());
                disable();
                
                if (minhaVez()) {
                    try {
                        MensagemParaServidor jogada = new MensagemParaServidor(Acao.JOGADA, posicao);
                        System.out.println("Enviando: "+jogada+ " | campo: "+jogada.getParametro());
                        TicTrackToe.CONNECTION.send(jogada);
                    } catch (Exception ex) {
                        this.jogador = null;
                        setText("");
                        disable(false);
                        System.out.println("Erro ao enviar jogada!");
                    }
                }
            }
        }
        
        public void setVitoria() {
            setBackgroundColor(Atributo.VERDE);
            setTextColor(Color.WHITE);
        }
        
        public boolean estaPreenchido() {
            return jogador != null;
        }
        
        @Override
        public void disable (boolean desativar) {
            super.disable(desativar);
            setOpacity(1);
        }
        
        @Override
        public void disable () {
            disable(true);
        }
    }
    
    private class Tabuleiro extends GridPane {
        public Tabuleiro() {
            super();
            setVgap(12); 
            setHgap(12);
            setMaxSize(474, 474);
            setAlignment(Pos.CENTER);
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    add(new Campo(3*i + j), j, i);
                }
            }
        }
        
        public void preencher(int pos, Jogador jogador) {
            ((Campo) getChildren().get(pos)).preencherCampo(jogador);
        }
        
        public void vitoria(List<Integer> campos) {
            desativarTabuleiro(true);
            getChildren().forEach(filho -> {
                Campo campo = (Campo) filho;
                if (campos.contains(getChildren().indexOf(campo)))
                    campo.setVitoria();
                
            });
        }
        
        public void desativarTabuleiro (boolean desativar) {
            getChildren().forEach(filho -> {
                Campo campo = (Campo) filho;
                if ((campo.estaPreenchido() && desativar) || !campo.estaPreenchido()) {
                    campo.setDisable(desativar);
                }
            });
        }
    }
    
    private class Placar extends StackPane {
        private Integer contagem;
        private final Texto valorPlacar;
        private final Texto identificador;
        private final StackPane simbolo;
        private final Rectangle background;
        private final VBox conteudo;
        
        public Placar (Jogador jogador) {
            super();
            
            contagem = 0;
            
            background = new Rectangle(84, 150);
            background.setArcHeight(84*.18);
            background.setArcWidth(84*.18);
            background.setFill(Atributo.ROXO_3);
            
            simbolo = geraSimbolo(jogador);
            identificador = geraIdentificador(jogador);
            valorPlacar = new Texto(contagem.toString(), 36);
                        
            conteudo = new VBox(simbolo, identificador, valorPlacar);
            conteudo.setAlignment(Pos.TOP_CENTER);
            conteudo.setSpacing(12);
            conteudo.setPadding(new Insets(12, 0, 0, 0));
            
            setMaxSize(84, 150);
            getChildren().addAll(background, conteudo);
        }
        
        private StackPane geraSimbolo (Jogador jogador) {
            StackPane retorno = new StackPane();
            
            Rectangle background = new Rectangle(60, 60);
            background.setArcHeight(60*.18);
            background.setArcWidth(60*.18);
            background.setFill(Color.WHITE);
            
            Node simbolo;
            if (jogador != null) {
                simbolo = new Simbolo(jogador, 72);
            }
            else {
                simbolo = new GridPane();
                ((GridPane) simbolo).add(new Simbolo(Jogador.X, 36), 0, 0);
                ((GridPane) simbolo).add(new Texto("", 36), 0, 1);
                ((GridPane) simbolo).add(new Texto("", 36), 1, 0);
                ((GridPane) simbolo).add(new Simbolo(Jogador.O, 36), 1, 1);
                ((GridPane) simbolo).setAlignment(Pos.CENTER);
            }
            
            return new StackPane(background, simbolo);
        }
        
        private void atualizarPlacar () {
            valorPlacar.setText(contagem.toString());
        }
        
        private void incrementar() {
            ++contagem;
            atualizarPlacar();
        }

        private Texto geraIdentificador(Jogador jogador) {
            String id = null;
            if (jogador == SIMBOLO)
                id = "VOCÊ";
            else if (jogador == null)
                id = "EMPATE";
            else
                id = "OPONENTE";
            
            return new Texto(id, 12);
        }
    }
}