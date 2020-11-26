package tela.elemento;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import tela.SceneUtil;
import tictracktoe.TicTrackToe;

/**
 * Classe extensão de StackPane que modela um botão com estilo padrão dos
 * layouts.
 * 
 * @author Pedro Antônio de Souza
 */
public class Botao extends StackPane {
    protected Color corTexto, corBackground, corTextoEntered, corBackgroundEntered;
    private double fontSize, width, height;
    private String value;
    private boolean disable;
    private Rectangle background;
    private Text texto;
    
    /**
     * Construtor da classe que recebe apenas o texto do botão.
     * @param value Texto presente no botão.
     */
    public Botao (String value) {
        super();
        inicializaBotao(value, null, null, null);
    }
    
    /**
     * Construtor da classe que recebe o texto e a largura do botão.
     * @param value Texto presente no botão.
     * @param width Largura do botão.
     */
    public Botao (String value, double width) {
        super();
        inicializaBotao(value, null, width, null);
    }
    
    /**
     * Construtor da classe que recebe o texto, a largura e altura do botão.
     * @param value Texto presente no botão.
     * @param width Largura do botão.
     * @param height Altura do botão.
     */
    public Botao (String value, double width, double height) {
        super();
        inicializaBotao(value, null, width, height);
    }
    
    /**
     * Construtor da classe com todos os atributos.
     * @param value Texto presente no botão.
     * @param fontSize Tamanho da fonte do botão.
     * @param width Largura do botão.
     * @param height Altura do botão.
     */
    public Botao (String value, double fontSize, double width, double height) {
        super();
        inicializaBotao(value, fontSize, width, height);
    }
    
    /**
     * Inicializador do botão.
     * Inicializa todos os atributos da classe.
     * 
     * @param value Texto presente no botão.
     * @param fontSize Tamanho da fonte do botão.
     * @param width Largura do botão.
     * @param height Altura do botão.
     */
    public final void inicializaBotao (String value, Double fontSize, Double width, Double height) {
        this.background = new Rectangle();
        
        this.texto =  new Text();
        this.texto.setBoundsType(TextBoundsType.VISUAL);
        
        this.value = value;
        this.fontSize = fontSize != null ? fontSize : 24;
        this.width = width != null ? width : 240;
        this.height = height != null ? height : 60;
        
        this.corTexto = Atributo.ROXO_2;
        this.corTextoEntered = Atributo.ROXO_2;
        this.corBackground = Color.WHITE;
        this.corBackgroundEntered = Color.WHITE;
        
        // Inicializa com o estado inicial do botão.
        estadoInicial();
        
        this.getChildren().addAll(this.background, this.texto);
        
        // Adiciona efeitos visuais ao passar o cursor sobre o botão.
        adicionarEvento(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateTextColor(corTextoEntered);
                updateBackgroundColor(corBackgroundEntered);
                
                DropShadow brilho = new DropShadow();
                brilho.setBlurType(BlurType.GAUSSIAN);
                // Define a cor do brilho como a cor do background quando o
                // cursor está sobre o botão.
                brilho.setColor(
                    Color.rgb(
                        (int) Math.round(corBackgroundEntered.getRed()*255),
                        (int) Math.round(corBackgroundEntered.getGreen()*255),
                        (int) Math.round(corBackgroundEntered.getBlue()*255),
                        .4)
                );
                brilho.setHeight(background.getHeight());
                brilho.setWidth(background.getWidth());
                brilho.setOffsetX(0);  
                brilho.setOffsetY(0);   
                brilho.setSpread(0);
                brilho.setRadius(background.getArcHeight()*5);
                
                setEffect(brilho);
            }
        });
        
        // Retorna o botão para o estado inicial após o cursor sair do botão.
        adicionarEvento(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                estadoInicial();
            }
        });
    }
    
    /**
     * Retorna o estilo do botão para seu estado inicial.
     */
    protected void estadoInicial () {
        setCursor(Cursor.HAND);
        
        setText(value);
        setTextColor(corTexto);
        setTextSize(fontSize);
        
        setBackgroundColor(corBackground);
        setWidth(width);
        setHeight(height);
        
        setEffect(null);
        setOpacity(1);
    }
    
    /**
     * Define texto do botão.
     * 
     * @param value Texto do botão.
     */
    public void setText (String value) {
        if (value != null) {
            this.value = value;
            this.texto.setText(value);
        }
    }
    
    /**
     * Define cor do texto do botão.
     * 
     * @param cor Cor do texto do botão.
     */
    public final void setTextColor (Color cor) {
        this.corTexto = cor;
        updateTextColor(cor);
    }
    
    /**
     * Define cor do texto quando o cursor está sobre o botão.
     * 
     * @param cor Cor do texto quando o cursor está sobre o botão.
     */
    public final void setTextColorEntered (Color cor) {
        this.corTextoEntered = cor;
    }
    
    /**
     * Define tamanho do texto.
     * 
     * @param size Tamanho do texto.
     */
    public final void setTextSize (double size) {
        this.fontSize = size;
        updateTextSize(size);
    }
    
    /**
     * Define cor de fundo do botão.
     * @param background Cor de fundo.
     */
    public void setBackgroundColor (Color background) {
        this.corBackground = background;
        updateBackgroundColor(background);
        
    }
    
    /**
     * Define cor de fundo do botão quando o cursor está sobre o botão.
     * @param background Cor de fundo do botão quando o cursor está sobre o
     * botão.
     */
    public void setBackgroundColorEntered (Color background) {
        this.corBackgroundEntered = background;
        
    }
    
    /**
     * Define largura do botão.
     * 
     * @param width Largura do botão.
     */
    @Override
    public void setWidth (double width) {
        this.width = width;
        background.setWidth(width);
        setMaxWidth(width);
        super.setWidth(width);
        atualizaArco();
    }
    
    /**
     * Define altura do botão.
     * @param height Altura do botão.
     */
    @Override
    public void setHeight (double height) {
        this.height = height;
        background.setHeight(height);
        setMaxHeight(height);
        super.setHeight(height);
        atualizaArco();
    }
    
    /**
     * Getter do texto do botão.
     * @return String com o texto do botão.
     */
    public String getText () {
        return value;
    }
    
    /**
     * Método para verificar se o botão está desativado.
     * @return True se o botão estiver desativado, false caso contrário.
     */
    public boolean getDisable() {
        return disable;
    }
    
    /**
     * Modifica a cor do texto.
     * Esse método não altera a cor padrão (do estado inicial) do texto.
     * @param cor Cor do texto.
     */
    protected void updateTextColor (Color cor) {
        this.texto.setFill(cor);
    }
    
    /**
     * Modifica o tamanho da fonte do texto. 
     * Esse método não altera o tamanho padrão (do estado inicial) da fonte do
     * texto.
     * @param size Tamanho do texto.
     */
    protected void updateTextSize (double size) {
        this.texto.setFont(Font.loadFont(getClass().getResourceAsStream(Atributo.FONTE_PADRAO), size));
    }
    
    /**
     * Modifica a cor de fundo do botão. 
     * Esse método não altera a cor de fundo padrão (do estado inicial) do
     * botão.
     * @param background Cor de fundo.
     */
    protected void updateBackgroundColor (Color background) {
        this.background.setFill(background);
    }
    
    /**
     * Atualiza arco de arredondamento das quinas do botão de acordo com suas 
     * dimensões (largura e altura).
     */
    private void atualizaArco () {
        double arco = background.getWidth() < background.getHeight()
                    ? background.getWidth()*.18
                    : background.getHeight()*.18;
        
        background.setArcWidth(arco);
        background.setArcHeight(arco);
    }
    
    /**
     * Desativa ou ativa o botão.
     * @param disable True para desativar o botão e false para ativar.
     */
    public void disable(boolean disable) {
        this.disable = disable;
        setEffect(null);
        setCursor(disable ? Cursor.DEFAULT : Cursor.HAND);
        setOpacity(disable ? .4 : 1);
        this.setDisable(disable);
    }
    
    /**
     * Desativa o botão.
     */
    public void disable() {
        disable(true);
    }
    
    /**
     * Adiciona um evento de clique do mouse no botão.
     * @param eh Evento a ser adicionado.
     */
    public void onClick(EventHandler eh) {
        adicionarEvento(MouseEvent.MOUSE_CLICKED, eh);
    }
    
    /**
     * Adiciona um evento de mudança de tela ao clicar no botão.
     * @param tela Tela que deve ser aberta ao clicar no botão.
     */
    public void onClick(SceneUtil tela) {
        onClick(new EventHandler<MouseEvent> () { 
            @Override
            public void handle(MouseEvent t) {
                TicTrackToe.abrirTela(tela);
            }
    
        });
    }
    
    /**
     * Adiciona um evento a todos nós filhos do botão.
     * Esse método evita que eventos sejam percebidos no efeito de brilho do 
     * botão.
     * @param et Tipo de evento.
     * @param eh Evento.
     */
    public void adicionarEvento (EventType et, EventHandler eh) {
        getChildren().forEach(filho -> {
            filho.addEventHandler(et, eh);
        });
    }
}
