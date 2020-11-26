package tela.elemento;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Modela o logotipo do jogo.
 * @author Pedo Antônio de Souza
 */
public final class Logotipo extends HBox {
    /**
     * Constrói logotipo com tamanho de fonte padrão.
     */
    public Logotipo() {
        inicializaLogotipo(null);
    }
    
    /**
     * Constrói logotipo.
     * @param tamanho Tamanho da fonte do logotipo.
     */
    public Logotipo(double tamanho) {
        inicializaLogotipo(tamanho);
    }
    
    /**
     * Inicializa logotipo.
     * @param tamanho Tamanho da fonte do logotipo.
     */
    private void inicializaLogotipo(Double tamanho) {
        tamanho = tamanho != null ? tamanho : 36;
        this.setAlignment(Pos.CENTER);
        
        Text tic = new Texto("tic", tamanho);
        Text track = new Texto("track", tamanho);
        Text toe = new Texto("toe", tamanho);
        
        track.setFill(Atributo.VERDE);
        
        this.getChildren().addAll(tic, track, toe);
    }
}
