/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author pedro
 */
public class Simbolo extends Texto {
    public enum Jogador {
        X, O;
        
        @Override
        public String toString() {
            switch (this) {
                case X: return "x";
                case O: return "o";
            }
            
            return null;
        }
        
        public static Jogador getById (int id) {
            for(Jogador e : values()) {
                if(e.ordinal() == id) return e;
            }
            return null;
        }
        
        public Jogador getOponente() {
            if (this == X) return O;
            else return X;
        }
    };
    private final List<Color> COR = List.of(Atributo.ROXO_2, Atributo.ROXO_4);
    private Jogador jogador;
    
    public Simbolo (Jogador jogador) {
        super();
        inicializarSimbolo(jogador, null);
    }
    
    public Simbolo (Jogador jogador, double tamanho) {
        super();
        inicializarSimbolo(jogador, tamanho);
    }
    
    private void inicializarSimbolo(Jogador jogador, Double tamanho) {
        this.jogador = jogador;
        this.setText(jogador.toString());
        setSize(tamanho != null ? tamanho : 180);
        this.setFill(Atributo.ROXO_3);
    }
    
    public void corDoJogador() {
        this.setFill(COR.get(jogador.ordinal()));
    }
    
    @Override
    public String toString() {
        return jogador.toString();
    }
}
