package tela;

import mensagemsocket.MensagemParaCliente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tela.elemento.Logotipo;
import tela.elemento.Spinner;
import tela.elemento.Texto;
import tictracktoe.TicTrackToe;

/** Tela de espera de conexão com o servidor.
 *
 * @author Pedro Antônio de Souza
 */
public class Scene_Conectando extends SceneUtil {  
    /**Construtor da classe.
     * 
     */
    public Scene_Conectando () {
        super();
    }
    
    
    /**Gera layout informando que o jogo está se conectando com o servidor.
     * 
     * @return VBox com o conteúdo da inicial da tela.
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
    
    /**Implementação do método abstrato para tratamento de mensagens do
     * servidor.
     * 
     * @param mensagem Mensagem recebida do servidor a ser tratada.
     */
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        switch (mensagem.getAcao()) {
            case CONECTADO:
                TicTrackToe.abrirTela(new Scene_Inicial());
                break;
        }
    }
}
