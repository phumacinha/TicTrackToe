package tela.elemento;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Extensão de Text que modela um texto com o estilo padrão de layout do jogo.
 * @author Pedro Antônio de Souza
 */
public class Texto extends Text {
    /**
     * Constrói um objeto vazio.
     */
    public Texto () {
        inicializaTexto(null, null);
    }
    
    /**
     * Constrói um objeto com um determinado texto com tamanho padrão.
     * @param texto Texto do objeto.
     */
    public Texto (String texto) {
        inicializaTexto(texto, null);
    }
    
    /**
     * Constrói um objeto com um determinado texto e tamanho definido.
     * @param texto Texto do objeto.
     * @param tamanho Tamanho do texto.
     */
    public Texto (String texto, double tamanho) {
        inicializaTexto(texto, tamanho);
    }
    
    /**
     * Inicializa o texto.
     * @param texto Texto do objeto.
     * @param tamanho Tamanho do texto.
     */
    private void inicializaTexto(String texto, Double tamanho) {
        this.setText(texto != null ? texto.trim() : "");
        this.setBoundsType(TextBoundsType.VISUAL);
        this.setFill(Color.WHITE);
        setSize(tamanho != null ? tamanho : 36);
    }
    
    /**
     * Define tamanho do texto.
     * @param tamanho Tamanho do texto.
     */
    public void setSize (double tamanho) {
        this.setFont(Font.loadFont(getClass().getResourceAsStream(Atributo.FONTE_PADRAO), tamanho));
    }
}
