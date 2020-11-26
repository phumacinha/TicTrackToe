package tela.elemento;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;

/**
 * Classe que modela um pop up.
 * @author Pedro Antônio de Souza
 */
public class PopUp extends VBox {
    private VBox botoes;
    private Botao fechar;
    private boolean closeable = false;
    
    /**
     * Construtor vazio.
     */
    public PopUp () {
        super();
    }
    
    /**
     * Construtor que recebe o título e o texto detalhado.
     * @param textoTitulo Título do pop up.
     * @param textoDetalhe Texto do pop up.
     */
    public PopUp(String textoTitulo, String textoDetalhe) {
        super();
        inicializar(textoTitulo, textoDetalhe);
    }
    
    /**
     * Construtor que recebe apenas o texto detalhado.
     * @param textoDetalhe Texto do pop up.
     */
    public PopUp(String textoDetalhe) {
        super();
        inicializar(null, textoDetalhe);
    }
    
    /**
     * Inicializador do pop up.
     * @param textoTitulo Título do pop up.
     * @param textoDetalhe Texto do pop up.
     */
    private void inicializar(String textoTitulo, String textoDetalhe) {
        botoes = new VBox();
        botoes.setSpacing(30);
        botoes.setPadding(new Insets(66, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);
        
        // Inializa botão de fechar
        fechar = new Botao("Fechar aviso");
        fechar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                fechar();
            }
        });
        
        // Background do pop up
        RadialGradient rdGra = new RadialGradient(0, 0, .5, .5, .75, true, CycleMethod.NO_CYCLE, new Stop[] {
            new Stop(0, Color.rgb(255, 255, 255, 1)),
            new Stop(.40, Color.rgb(255, 255, 255, .9375)),
            new Stop(.75, Color.rgb(255, 255, 255, .89375)),
            new Stop(1, Color.rgb(255, 255, 255, .8))
        });
        BackgroundFill bgFill = new BackgroundFill(rdGra, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(bgFill));
        
        // Titulo
        CaixaDeTexto titulo = new CaixaDeTexto(textoTitulo, 24);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setAlignment(Pos.CENTER);
        titulo.setTextFill(Atributo.ROXO_2);
        
        // Texto detalhado
        CaixaDeTexto detalhe = new CaixaDeTexto(textoDetalhe, 42);
        detalhe.setTextAlignment(TextAlignment.CENTER);
        detalhe.setAlignment(Pos.CENTER);
        detalhe.setTextFill(Atributo.ROXO_2);
        
        // Conteúdo escrito
        VBox conteudo = new VBox(titulo, detalhe);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setSpacing(60);
        
        // Formata pop up e insere conteúdo
        setPadding(new Insets(0, 0, 100, 0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(conteudo, botoes);
    }
    
    /**
     * Fecha pop up.
     */
    private void fechar () {
        if (getParent() instanceof Pane) {
            ((Pane) getParent()).getChildren().remove(this);
        }
    }
   
    /**
     * Insere botão com função de fechar o pop up.
     */
    public void setCloseable() {
        setCloseable(fechar.getText());
    }
    
    /**
     * Insere botão com função de fechar o pop up com um texto personalizado. 
     * @param string Texto do botão.
     */
    public void setCloseable(String string) {
        fechar.setText(string);
        addBotao(fechar);
        closeable = true;
    }
    
    /**
     * Adiciona botão após o texto do pop up.
     * @param botao Botão a ser inserido.
     */
    public void addBotao (Botao botao) {
        botao.setTextSize(24);
        botao.setBackgroundColor(Atributo.ROXO_2);
        botao.setTextColor(Color.WHITE);
        botao.setBackgroundColorEntered(Atributo.ROXO_1);
        botao.setTextColorEntered(Color.WHITE);
        
        if (!botoes.getChildren().contains(botao)) {
            int pos = closeable ? botoes.getChildren().size()-1 : botoes.getChildren().size();
            
            botoes.getChildren().add(pos, botao);
        }
    }
}
