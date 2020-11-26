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
 * Tela com tabuleiro e placar do jogo da velha.
 * É nessa classe em que é gerada a tela de jogo para o usuário e há métodos
 * para efetuação das jogadas.
 * 
 * @author Pedro Antônio de Souza
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
    private int tempoDeRecomeco = 7;
    
    /**
     * Construtor da classe.
     */
    public Scene_Tabuleiro() {
        super();
    }
    
    /**
     * Gera layout com tabuleiro, placar e botões de abandonoDoOponente de partida e
 ajuda.
     * 
     * @return VBox com o conteúdo inicial da tela.
     */
    @Override
    protected Node conteudoInicial() {
        Logotipo logotipo = new Logotipo(24);
        logotipo.setAlignment(Pos.CENTER);
        
        /*
         * TESTE
         */
        EventHandler e = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                novoJogo();
            }
        };
        
        logotipo.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
        /*
         * FIM TESTE
         */
        
        tabuleiro = new Tabuleiro();
        
        informacao = new Texto("");
        
        //Início instanciação do placar.
        placarVoce = new Placar(SIMBOLO);
        placarEmpate = new Placar(null);
        placarOponente = new Placar(SIMBOLO.getOponente());
        placar = new HBox(placarVoce, placarEmpate, placarOponente);
        placar.setSpacing(30);
        placar.setAlignment(Pos.CENTER);
        //Fim instanciação do placar.
        
        ajuda = new Botao("Ajuda");
        ajuda.disable();
        //TO DO ajuda.onClick();
        sair = new Botao("Sair");
        
        sair.onClick(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                abrirPopUp(popUpSair());
            }
        });
        
        menu = new VBox(ajuda, sair);
        menu.setSpacing(30);
        menu.setAlignment(Pos.CENTER);
        
        conteudoDireito = new VBox(informacao, placar, menu);
        conteudoDireito.setSpacing(60);
        conteudoDireito.setAlignment(Pos.CENTER);
        
        // Força quebra de linha no objeto informacao para não exceder a largura
        // de conteudoDireito.
        informacao.wrappingWidthProperty().bind(conteudoDireito.widthProperty());
      
        conteudoEsquerdo = new StackPane();
        conteudoEsquerdo.setMaxSize(474, 474);
        // Método que redefine o jogo e insere o tabuleiro em conteudoEsquerdo.
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
    
    /**
     * Gera PopUp para confirmar abandonoDoOponente de partida.
     * 
     * @return PopUp pedindo confirmação de abandonoDoOponente de partida.
     */
    private PopUp popUpSair() {
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
    
    /**
     * Verifica se é a vez do jogador.
     * 
     * @return True se for o turno do jogador, false se for turno do oponente.
     */
    private boolean minhaVez() {
        return minhaId.equals(turnoId);
    }

    /**
     * Redefine variáveis iniciais de jogo e gera tabuleiro vazio.
     */
    private void novoJogo () {
        fimDeJogo = false;
        deuVelha = false;
        tabuleiro.limparTabuleiro();
        conteudoEsquerdo.getChildren().clear();
        conteudoEsquerdo.getChildren().add(tabuleiro);
    }
    
    /**
     * Atualiza e exibe na tela a informação de status atual do jogo.
     */
    private void atualizaInformacao () {
        if (fimDeJogo || deuVelha)
            setTimer();
        else if (minhaVez())
            informacao.setText("Jogue, é sua vez!");
        else
            informacao.setText("Aguarde sua vez!");
    }
    
    public void setTimer() {
        /*Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(tempoDeRecomeco > 1) {
                    Platform.runLater(() -> informacao.setText("A partida irá recomeçar em "+tempoDeRecomeco+"..."));
                    tempoDeRecomeco--;
                }
                else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
        
        
        tempoDeRecomeco = 7;*/
        novoJogo();
        tabuleiro.desativarTabuleiro(!minhaVez());
    }
    
    /**
     * Define o turno da partida.
     * 
     * @param novoTurno Inteiro que representa o identificador do jogador do 
     * novo turno.
     */
    private void novoTurno (Integer novoTurno) {
        turnoId = novoTurno;
        tabuleiro.desativarTabuleiro(!minhaVez());
    }
    
    /**
     * Trata jogada recebida do servidor.
     * 
     * @param param Parâmetros da jogada recebida do servidor.
     */
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
    
    /**
     * Verifica se o jogo terminou (há vencedor ou deu velha) de acordo com a
     * resposta do servidor após uma jogada.
     * 
     * @param resposta Estado do jogo recebido do servidor.
     */
    private void checaFimDeJogo (List<Integer> resposta) {
        //Deu velha
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
            //Chama método que exibe vencedor na tela.
            haGanhador(resposta.get(1));
        }
    }

    /**
     * Altera layout quando há vencedor.
     * 1 quando o vencedor completou a primeira linha do tabuleiro.
     * 2 quando o vencedor completou a segunda linha do tabuleiro.
     * 3 quando o vencedor completou a terceira linha do tabuleiro.
     * 4 quando o vencedor completou a primeira coluna do tabuleiro.
     * 5 quando o vencedor completou a segunda coluna do tabuleiro.
     * 6 quando o vencedor completou a terceira coluna do tabuleiro.
     * 7 quando o vencedor completou a diagonal principal do tabuleiro.
     * 8 quando o vencedor completou a diagonal secundaria do tabuleiro.
     * 
     * @param tipo Tipo de vitória.
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
    
    /**
     * Exibe opções em PopUp quando o oponente abandona a partida.
     */
    private void abandonoDoOponente () {
        Botao procurar = new Botao("Procurar novo adversário", 350);
        procurar.onClick(new Scene_ProcurandoAdversario());

        Botao voltar = new Botao("Voltar ao início");
        voltar.onClick(new Scene_Inicial());

        PopUp popup = new PopUp("Seu adversário abandonou a sala.");
        popup.addBotao(procurar);
        popup.addBotao(voltar);

        abrirPopUp(popup);
    }
    
    /**Método sobrecarregado para tratar mensagens recebidas do servidor.
     * 
     * @param mensagem Mensagem recebida do servidor.
     */
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
                abandonoDoOponente();
                break;
            default:
                System.out.println("Mensagem não tratável." + mensagem.toString());
                break;
        }
        
        atualizaInformacao();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Classes privadas">
    /**
     * Classe privada extendida de Botao para instanciar os campos do tabuleiro.
     */
    private class Campo extends Botao {
        private Jogador jogador = null;
        private final int posicao;
        
        /**
         * Construtor da classe.
         * 
         * @param posicao Posição do campo em relação ao tabuleiro (de 0 a 8).
         */
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
        
        /**
         * Método que preenche campo com o símbolo referente ao jogador.
         * 
         * @param jogador Símbolo referente ao jogador que selecionou o campo.
         */
        public void preencherCampo (Jogador jogador) {
            if (!fimDeJogo && !deuVelha && this.jogador == null) {
                this.jogador = jogador;
                setText(jogador.toString());
                //Desativa campo após preenche-lo.
                disable();
                
                // Se for vez do jogador, envia jogada para o servidor.
                if (minhaVez()) {
                    try {
                        MensagemParaServidor jogada = new MensagemParaServidor(Acao.JOGADA, posicao);
                        TicTrackToe.CONNECTION.send(jogada);
                        
                        System.out.println("Enviando: "+jogada+ " | campo: "+jogada.getParametro());
                    } catch (Exception ex) {
                        // Se não for possível enviar a jogada, redefine o campo.
                        limpar();
                        disable(false);
                        System.out.println("Erro ao enviar jogada!");
                    }
                }
            }
        }
        
        /**
         * Estiliza campo em caso de vitória.
         */
        public void setVitoria() {
            updateBackgroundColor(Atributo.VERDE);
            updateTextColor(Color.WHITE);
        }
        
        /**
         * Remove preenchimento do campo.
         */
        public void limpar () {
            this.jogador = null;
            super.setText("");
            super.estadoInicial();
            disable(super.getDisable());
        }
        
        /**
         * Verifica se o campo já está preenchido.
         * 
         * @return True se o campo estiver preenchido, false caso contrário.
         */
        public boolean estaPreenchido() {
            return jogador != null;
        }
        
        /**
         * Método sobrecarragado que desativa o campo sem alterar sua opacidade.
         * 
         * @param desativar Booleando que define se o campo será desativado
         * (true) ou ativado (false).
         */
        @Override
        public void disable (boolean desativar) {
            super.disable(desativar);
            setOpacity(1);
        }
        
        /**
         * Método para desativar o campo.
         */
        @Override
        public void disable () {
            this.disable(true);
        }
    }
    
    /**
     * Classe privada extendida de GridPane para instanciar o tabuleiro de jogo.
     */
    private class Tabuleiro extends GridPane {
        /**
         * Construtor da classe.
         */
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
        
        /**
         * Prenche campo do tabuleiro com o símbolo referente a um determinado
         * jogador.
         * 
         * @param pos Posição do campo a ser preenchido.
         * @param jogador Jogador referência do símbolo a ser desenhado.
         */
        public void preencher(int pos, Jogador jogador) {
            ((Campo) getChildren().get(pos)).preencherCampo(jogador);
        }
        
        /**
         * Estiliza tabuleiro em caso de vitória.
         * 
         * @param campos Lista com os três campos referentes a vitória.
         */
        public void vitoria(List<Integer> campos) {
            desativarTabuleiro(true);
            
            getChildren().forEach(filho -> {
                Campo campo = (Campo) filho;
                if (campos.contains(getChildren().indexOf(campo)))
                    campo.setVitoria();
                
            });
        }
        
        /**
         * Desativa tabuleiro impedindo preenchimento de campos.
         * 
         * @param desativar Booleando que define se o tabuleiro será desativado
         * (true) ou ativado (false).
         */
        public void desativarTabuleiro (boolean desativar) {
            getChildren().forEach(filho -> {
                Campo campo = (Campo) filho;
                // Campos preenchidos não podem ser ativados, por isso a
                // primeira parte da condição (antes do or).
                if ((campo.estaPreenchido() && desativar) || !campo.estaPreenchido()) {
                    campo.setDisable(desativar);
                }
            });
        }
        
        /**
         * Remove preenchimentos do tabuleiro.
         */
        public void limparTabuleiro () {
            getChildren().forEach(filho -> {
                Campo campo = (Campo) filho;
                campo.limpar();
            });
        }
    }
    
    /**
     * Classe privada extendida de StackPane para instanciar placares.
     */
    private class Placar extends StackPane {
        private Jogador jogador;
        private Integer contagem;
        private final Texto valorPlacar;
        private final Texto identificador;
        private final StackPane simbolo;
        private final Rectangle background;
        private final VBox conteudo;
        
        /**
         * Construtor da classe.
         * 
         * @param jogador Jogador que será contabilizado o placar ou null para
         * o placar de empate.
         */
        public Placar (Jogador jogador) {
            super();
            
            this.jogador = jogador;
            
            contagem = 0;
            
            background = new Rectangle(84, 150);
            background.setArcHeight(84*.18);
            background.setArcWidth(84*.18);
            background.setFill(Atributo.ROXO_3);
            
            simbolo = geraSimbolo();
            identificador = geraIdentificador();
            valorPlacar = new Texto(contagem.toString(), 36);
            
            conteudo = new VBox(simbolo, identificador, valorPlacar);
            conteudo.setAlignment(Pos.TOP_CENTER);
            conteudo.setSpacing(12);
            conteudo.setPadding(new Insets(12, 0, 0, 0));
            
            setMaxSize(84, 150);
            getChildren().addAll(background, conteudo);
        }
        
        /**
         * Gera símbolo referente ao jogador do placar ou ao empate.
         * 
         * @return StackPane com o símbolo que representa algum dos dois
         * jogadores ou empate, dependendo do que esse placar contabiliza.
         */
        private StackPane geraSimbolo () {
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
        
        /**
         * Exibe a contagem do placar.
         */
        private void atualizarPlacar () {
            valorPlacar.setText(contagem.toString());
        }
        
        /**
         * Incrementa e atualiza a contagem do placar.
         */
        private void incrementar() {
            ++contagem;
            atualizarPlacar();
        }
        
        /**
         * Gera um texto identificador do que é contabilizado no placar.
         * 
         * @return Objeto Texto com o identificador do placar.
         */
        private Texto geraIdentificador() {
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
    //</editor-fold>
}