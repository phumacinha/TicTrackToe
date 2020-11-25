/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author pedro
 */
public class Atributo {
    public static final int SCREEN_WIDTH = 990; //(int) Screen.getPrimary().getBounds().getWidth();
    public static final int SCREEN_HEIGHT = 720; //(int) Screen.getPrimary().getBounds().getHeight();
    
    public static final double FATOR_ESCALA = ((double)SCREEN_WIDTH/990) > ((double)SCREEN_HEIGHT/720)
                                            ? ((double)SCREEN_WIDTH/990)
                                            : ((double)SCREEN_HEIGHT/720);

    public static final String FONTE_PADRAO = "file:resources/fonts/FredokaOne-Regular.ttf";
    
    public static final Color ROXO_1 = Color.rgb(89, 32, 187);
    public static final Color ROXO_2 = Color.rgb(55, 5, 140);
    public static final Color ROXO_3 = Color.rgb(41, 5, 94);
    public static final Color ROXO_4 = Color.rgb(25, 5, 40);
    public static final Color VERDE = Color.rgb(0, 255, 135);
   
    public static final RadialGradient BACKGROUND_RADIAL = new RadialGradient(0, 0, SCREEN_WIDTH*.5, 0, (SCREEN_HEIGHT+SCREEN_WIDTH)/2, false, CycleMethod.NO_CYCLE, new Stop[] {
        new Stop(0, ROXO_1),
        new Stop(.27, ROXO_1),
        new Stop(.55, ROXO_2),
        new Stop(.776, ROXO_3),
        new Stop(1, ROXO_4)
    });
    
    public static Color corJogador (int jogador) {
        switch (jogador) {
            case 0:
                return ROXO_3;
            case 1:
                return ROXO_1;
        }
        
        return null;
    }
}
