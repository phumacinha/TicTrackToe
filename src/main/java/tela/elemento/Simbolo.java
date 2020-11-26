package tela.elemento;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * Classe que representa os símbolos dos jogadores.
 * 
 * @author Pedro Antônio de Souza
 */
public class Simbolo extends Texto {
    /**
     * Enum para os símbolos possíveis dos jogadores.
     */
    public enum Jogador {
        X, O;
        
        /**
         * Método que retorna, em caixa-baixa, os caracteres respectivos dos
         * elementos do enum.
         * @return Caractere em caixa-baixa dos respectivos elementos do eneum. 
         */
        @Override
        public String toString() {
            switch (this) {
                case X: return "x";
                case O: return "o";
            }
            
            return null;
        }
        
        /**
         * Retorna o elemento do enum de acordo com sua posição.
         * @param pos Posição do elemento.
         * @return Elemento do enum da posição passada por parâmetro.
         */
        public static Jogador getById (int pos) {
            for(Jogador e : values()) {
                if(e.ordinal() == pos) return e;
            }
            return null;
        }
        
        /**
         * Retorna o outro elemeto do enum.
         * @return Outro elemeto do enum.
         */
        public Jogador getOponente() {
            if (this == X) return O;
            else return X;
        }
    };
 
    private final List<Color> COR = List.of(Atributo.ROXO_2, Atributo.ROXO_4);
    private Jogador jogador;
    
    /**
     * Constrói o símbolo de um determinado jogador com o tamanho padrão.
     * @param jogador Jogador que deseja-se desenhar o símbolo.
     */
    public Simbolo (Jogador jogador) {
        super();
        inicializarSimbolo(jogador, null);
    }
    
    /**
     * Constrói o símbolo de um determinado jogador com um tamanho específico.
     * @param jogador Jogador que deseja-se desenhar o símbolo.
     * @param tamanho Tamanho do símbolo.
     */
    public Simbolo (Jogador jogador, double tamanho) {
        super();
        inicializarSimbolo(jogador, tamanho);
    }
    
    /**
     * Inicializa o símbolo.
     * @param jogador Jogador que deseja-se desenhar o símbolo.
     * @param tamanho Tamanho do símbolo.
     */
    private void inicializarSimbolo(Jogador jogador, Double tamanho) {
        this.jogador = jogador;
        this.setText(jogador.toString());
        setSize(tamanho != null ? tamanho : 180);
        this.setFill(Atributo.ROXO_3);
    }
    
    /**
     * Retorna string de acordo com o atributo jogador definido.
     * @return Caractere em caixa-baixa respectivo ao jogador definido.
     */
    @Override
    public String toString() {
        return jogador.toString();
    }
}
