/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author pedro
 */
public class CaixaDeTexto extends Label {
    public CaixaDeTexto (String string, double size) {
        super(string);
        inicializar(string, size);
    }
    
    public CaixaDeTexto (String string) {
        super(string);
        inicializar(string, null);
    }
    
    private void inicializar (String string, Double size) {
        setTextFill(Color.WHITE);
        setWrapText(true);
        setFontSize(size == null ? 36 : size);
    }
    
    public final void setFontSize (double size) {
        this.setFont(Font.loadFont(Atributo.FONTE_PADRAO, size));
    }
}
