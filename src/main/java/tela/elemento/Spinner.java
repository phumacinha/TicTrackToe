package tela.elemento;

import javafx.scene.control.ProgressIndicator;

/**
 * Extensão de ProgressIndicator que modela um spinner  com o estilo padrão de
 * layout do jogo.
 * @author Pedro Antônio de Souza
 */
public final class Spinner extends ProgressIndicator {
    /**
     * Constrói um spinner com tamanho padrão.
     */
    public Spinner() {
        inicializarSpinner(null);
    }
    
    /**
     * Constrói um spinner com um tamanho determinado.
     * @param tamanho Tamanho do spinner.
     */
    public Spinner(double tamanho) {
        inicializarSpinner(tamanho);
    }
    
    /**
     * Inicializa o spinner.
     * @param tamanho Tamanho do spinner.
     */
    void inicializarSpinner (Double tamanho) {
        tamanho = tamanho != null ? tamanho : 90;
        this.setStyle("-fx-progress-color: white;");
        this.setPrefHeight(tamanho);
        this.setPrefWidth(tamanho);
        this.setMinHeight(tamanho);
        this.setMinWidth(tamanho);
    }
}
