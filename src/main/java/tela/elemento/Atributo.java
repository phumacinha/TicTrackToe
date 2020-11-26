package tela.elemento;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;
import tela.elemento.Simbolo.Jogador;

/**
 * Classe com métodos e variáveis que retornam e armazenam propriedades do
 * layout das telas.
 * Todos os métodos e variáveis são estáticos e finais.
 * 
 * @author Pedro Antônio de Souza
 */
public class Atributo {
    public static final int IDEAL_WIDTH = 990;
    public static final int IDEAL_HEIGHT = 720;
    public static final int SCREEN_WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
    public static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();

    public static final String FONTE_PADRAO = "/fonts/FredokaOne-Regular.ttf";
    
    public static final Color ROXO_1 = Color.rgb(89, 32, 187);
    public static final Color ROXO_2 = Color.rgb(55, 5, 140);
    public static final Color ROXO_3 = Color.rgb(41, 5, 94);
    public static final Color ROXO_4 = Color.rgb(25, 5, 40);
    public static final Color VERDE = Color.rgb(0, 255, 135);
   
    public static final RadialGradient BACKGROUND_RADIAL = new RadialGradient(0, 0, SCREEN_WIDTH*.5, 0, (SCREEN_WIDTH+SCREEN_WIDTH)/2, false, CycleMethod.NO_CYCLE, new Stop[] {
        new Stop(0, ROXO_1),
        new Stop(.27, ROXO_1),
        new Stop(.55, ROXO_2),
        new Stop(.776, ROXO_3),
        new Stop(1, ROXO_4)
    });
    
    /**
     * Método que retorna uma cor específica para cada jogador.
     * 
     * @param jogador Jogador que deseja-se retornar a cor.
     * @return Objeto Color respectivo do jogador.
     */
    public static final Color corJogador (Jogador jogador) {
        switch (jogador) {
            case X:
                return ROXO_3;
            case O:
                return ROXO_1;
        }
        
        return null;
    }
}
