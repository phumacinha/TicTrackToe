package tela;

import MensagemSocket.Acao;
import MensagemSocket.MensagemParaCliente;
import MensagemSocket.MensagemParaServidor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tela.elemento.Logotipo;
import tela.elemento.Spinner;
import tela.elemento.Texto;
import ticTrackToe.TicTrackToe;

/**
 * Tela para aguardar a procura de um adversário.
 * 
 * @author Pedro Antônio de Souza
 */
public class Scene_ProcurandoAdversario extends SceneUtil {
    /**
     * Construtor da classe.
     */
    public Scene_ProcurandoAdversario () {
        super();
    }
    
    /**
     * Método que informa ao servidor que o usuário está procurando uma sala
     * para jogar.
     */
    public final void procurarSala () {
        try {
            TicTrackToe.CONNECTION.send(new MensagemParaServidor(Acao.PROCURANDO_SALA));
        }
        catch (Exception e) {
            //TO DO
        }
    }
    
    /**
     * Gera layout informando que o jogo está se conectando com o servidor.
     * 
     * @return VBox com o conteúdo inicial da tela.
     */
    @Override
    protected final Node conteudoInicial () {
        procurarSala();
        
        HBox logotipo = new Logotipo(24);

        Text aguarde = new Texto("Aguarde,", 36);
        Text conectando = new Texto("estamos procurando um adversário...", 24);
        
        VBox texto = new VBox();
        texto.setAlignment(Pos.CENTER);
        texto.getChildren().addAll(aguarde, conectando);
        texto.setPadding(new Insets(0, 0, 24, 0));
        
        ProgressIndicator spinner = new Spinner(120);

        VBox meio = new VBox();
        meio.getChildren().addAll(texto, spinner);
        meio.setAlignment(Pos.CENTER);
        VBox.setVgrow(meio, Priority.ALWAYS);
        
        VBox cont = new VBox();
        
        cont.setAlignment(Pos.CENTER);
        cont.getChildren().clear();
        cont.getChildren().addAll(logotipo, meio);
        cont.setPadding(new Insets(60, 0, 60, 0));
        
        return cont;
    }
    
    /**Método sobrecarregado para tratar mensagens recebidas do servidor.
     * 
     * @param mensagem Mensagem recebida do servidor.
     */
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        switch (mensagem.getAcao()) {
            case SALA_ENCONTRADA:
                TicTrackToe.abrirTela(new Scene_Tabuleiro());
                break;
        }
    }
}
