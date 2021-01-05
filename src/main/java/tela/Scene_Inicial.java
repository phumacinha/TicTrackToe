package tela;

import etgames.mensagens.MensagemParaCliente;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tela.elemento.Atributo;
import tela.elemento.Botao;
import tela.elemento.Logotipo;

/** Tela inicial do jogo, onde são exibidas opções de iniciar novo jogo, sobre o
 * sistema e ajuda.
 *
 * @author Pedro Antônio de Souza
 */
public class Scene_Inicial extends SceneUtil {
    /**Construtor da classe.
     * 
     */
    public Scene_Inicial () {
        super();
    }
    
    /**Método que gera e retorna um objeto Botao que, ao ser clicado, inicia um
     * novo jogo.
     * 
     * @return Botao que, ao ser clicado, inicia um novo jogo.
     */
    private Botao botaoNovoJogo () {
        Botao botao = new Botao("NOVO JOGO", 36, 360, 90);
        botao.setBackgroundColor(Atributo.VERDE);
        botao.onClick(new Scene_SelecionarSimbolo());
        return botao;
    }
    
    /**Método que gera e retorna um objeto Botao que, ao ser clicado, exibe
     * informações de ajuda.
     * 
     * @return Botao que, ao ser clicado, exibe informações de ajuda.
     */
    private Botao botaoAjuda () {
        Botao botao = new Botao("Ajuda");
        botao.disable();
        //TO DO botao.onClick(tela ajuda);
        return botao;
    }
    
    /**Método que gera e retorna um objeto Botao que, ao ser clicado, exibe
     * informações do software.
     * 
     * @return Botao que, ao ser clicado, exibe informações do software.
     */
    private Botao botaoSobre () {
        Botao botao = new Botao("Sobre");
        botao.disable();
        //TO DO botao.onClick(tela sobre);
        return botao;
    }
    
    /**Método que gera e retorna conteúdo inicial da tela.
     * 
     * @return VBox com conteúdo inicial da tela.
     */
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
    
    /**Método sobrecarregado para tratar mensagens recebidas do servidor.
     * 
     * @param mensagem Mensagem recebida do servidor.
     */
    @Override
    public void trataMensagem(MensagemParaCliente mensagem) {
        
    }
    
}
