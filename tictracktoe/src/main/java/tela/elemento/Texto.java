/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author pedro
 */
public class Texto extends Text {
    public Texto () {
        inicializaTexto(null, null);
    }
    
    public Texto (String texto) {
        inicializaTexto(texto, null);
    }
    
    public Texto (String texto, double tamanho) {
        inicializaTexto(texto, tamanho);
    }
    
    private void inicializaTexto(String texto, Double tamanho) {
        this.setText(texto != null ? texto.trim() : "");
        this.setBoundsType(TextBoundsType.VISUAL);
        this.setFill(Color.WHITE);
        setSize(tamanho != null ? tamanho : 36);
    }
    
    public void setSize (double tamanho) {
        this.setFont(Font.loadFont(Atributo.FONTE_PADRAO, tamanho));
    }
}
