package tela.elemento;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Classe que extende Label e modela uma caixa de texto com o estilo padrão de
 * layout do jogo.
 * 
 * @author Pedro Antônio de Souza
 */
public class CaixaDeTexto extends Label {
    /**
     * Construtor da classe.
     * 
     * @param string Texto da caixa.
     * @param size Tamanho da fonte do texto.
     */
    public CaixaDeTexto (String string, double size) {
        super();
        inicializar(string, size);
    }
    
    /**
     * Construtor da classe que recebe apenas o texto.
     * @param string Texto da caixa.
     */
    public CaixaDeTexto (String string) {
        super();
        inicializar(string, null);
    }
    
    /**
     * Inicializador da caixa de texto.
     * @param string Texto da caixa.
     * @param size Tamanho da fonte do texto.
     */
    private void inicializar (String string, Double size) {
        super.setText(string);
        super.setTextFill(Color.WHITE);
        super.setWrapText(true);
        
        setFontSize(size == null ? 36 : size);
    }
    
    /**
     * Define o tamanho da fonte do texto.
     * @param size Tamanho da fonte do texto.
     */
    public final void setFontSize (double size) {
        this.setFont(Font.loadFont(
                getClass().getResourceAsStream(Atributo.FONTE_PADRAO), size)
        );
    }
}
