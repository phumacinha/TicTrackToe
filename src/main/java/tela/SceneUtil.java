package tela;

import MensagemSocket.MensagemParaCliente;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import tela.elemento.Atributo;
import tela.elemento.PopUp;

/**Classe abstrata para padronização de telas.
 *
 * @author Pedro Antônio de Souza
 */
public abstract class SceneUtil {
    private final StackPane root;
    private PopUp popup = new PopUp();
    protected double fatorDeEscala = 1;
    
    /**Construtor da classe.
     * 
     */
    public SceneUtil () {
        fatorDeEscala();
        
        root = new StackPane();
      //  root.setScaleX(fatorDeEscala);
       // root.setScaleY(fatorDeEscala);
    }
    
    /**Cria e retorna um objeto do tipo Scene de acordo com os padrões
     * definidos.
     * 
     * 
     * @return Objeto do tipo Scene com com o conteúdo obtido no método
     * SceneUtil.conteudoInicial().
     */
    public final Scene getScene() {
        Scene scene = new Scene(root, Atributo.SCREEN_WIDTH, Atributo.SCREEN_HEIGHT);
        scene.setFill(Atributo.BACKGROUND_RADIAL);
        
        clearRoot();
        insereConteudo(conteudoInicial());
        
        return scene;
    }
    
    /**Limpa o nó raiz da cena.
     * 
     */
    public void clearRoot() {
        root.setBackground(Background.EMPTY);
        root.getChildren().clear();
    }
    
    /**Insere conteúdo no nó de cena raiz.
     * 
     * @param conteudo Nó a ser inserido como filho da raiz.
     */
    protected void insereConteudo (Node conteudo) {
        root.getChildren().add(0, conteudo);
    }
    
    /**Método para inserir conteúdo de aviso sobreposto ao conteúdo da tela.
     * 
     * @param popup Objeto PopUp com o conteúdo de aviso.
     */
    protected void abrirPopUp (PopUp popup) {
        if (root.getChildren().contains(this.popup)) {
            root.getChildren().remove(this.popup);
        }
        this.popup = popup;
        root.getChildren().add(this.popup);
    }
    
    private void fatorDeEscala () {
        double fatorX = ((double) Atributo.SCREEN_WIDTH)/Atributo.IDEAL_WIDTH;
        double fatorY = ((double) Atributo.SCREEN_HEIGHT)/Atributo.IDEAL_HEIGHT;
        
        if ((fatorX >= 1 && fatorY >= 1)
            || (fatorX <= 1 && fatorY <= 1)) {
            fatorDeEscala = fatorX < fatorY ? fatorX : fatorY;
        }
        else {
            fatorDeEscala = fatorX < 1 ? fatorX : fatorY;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="metodos abstratos">
    /**Método abstrato para retornar o conteúdo inicial da tela.
     * 
     * @return Node com conteúdo inicial da tela.
     */
    protected abstract Node conteudoInicial();
    
    /**Método abstrato para tratar mensagens recebidas do servidor.
     * 
     * @param mensagem Mensagem recebida do servidor.
     */
    public abstract void trataMensagem (MensagemParaCliente mensagem);
    //</editor-fold>
}
