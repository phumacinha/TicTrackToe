/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author pedro
 */
public final class Spinner extends ProgressIndicator {
    public Spinner() {
        inicializarSpinner(null);
    }
    
    
    public Spinner(double tamanho) {
        inicializarSpinner(tamanho);
    }
    
    void inicializarSpinner (Double tamanho) {
        tamanho = tamanho != null ? tamanho : 90;
        this.setStyle("-fx-progress-color: white;");
        this.setPrefHeight(tamanho);
        this.setMinHeight(tamanho);
        this.setMinWidth(tamanho);
    }
}
