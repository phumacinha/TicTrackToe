/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author pedro
 */
public final class Logotipo extends HBox {
    public Logotipo() {
        inicializaLogotipo(null);
    }
    
    
    public Logotipo(double tamanho) {
        inicializaLogotipo(tamanho);
    }
    
    
    private void inicializaLogotipo(Double tamanho) {
        tamanho = tamanho != null ? tamanho : 36;
        this.setAlignment(Pos.CENTER);
        
        Text tic = new Texto("tic", (double) tamanho);
        Text track = new Texto("track", (double) tamanho);
        Text toe = new Texto("toe", (double) tamanho);
        
        track.setFill(Atributo.VERDE);
        
        this.getChildren().addAll(tic, track, toe);
    }
}
