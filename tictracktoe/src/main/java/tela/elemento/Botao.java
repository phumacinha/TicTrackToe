/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.elemento;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
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
import ticTrackToe.TicTrackToe;

/**
 *
 * @author pedro
 */
public class Botao extends StackPane {
    protected Color corTexto, corBackground, corTextoEntered, corBackgroundEntered;
    private double fontSize, width, height;
    private String value;
    private boolean disable;
    private Rectangle background;
    private Text texto;
    
    public Botao (String value) {
        super();
        inicializaBotao(value, null, null, null);
    }
    
    public Botao (String value, double width) {
        super();
        inicializaBotao(value, null, width, null);
    }
    
    public Botao (String value, double width, double height) {
        super();
        inicializaBotao(value, null, width, height);
    }
    
    public Botao (String value, double fontSize, double width, double height) {
        super();
        inicializaBotao(value, fontSize, width, height);
    }
    
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
        
        
        estadoInicial();
        
        this.getChildren().addAll(this.background, this.texto);
        
        adicionarEvento(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateTextColor(corTextoEntered);
                updateBackgroundColor(corBackgroundEntered);
                
                DropShadow brilho = new DropShadow();
                brilho.setBlurType(BlurType.GAUSSIAN);
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
        adicionarEvento(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                estadoInicial();
            }
        });
    }
    
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
    
    public void setText (String value) {
        if (value != null) {
            this.value = value;
            this.texto.setText(value);
        }
    }
    
    public final void setTextColor (Color cor) {
        this.corTexto = cor;
        updateTextColor(cor);
    }
    
    public final void setTextColorEntered (Color cor) {
        this.corTextoEntered = cor;
    }
    
    public final void setTextSize (double size) {
        this.fontSize = size;
        updateTextSize(size);
    }
    
    public void setBackgroundColor (Color background) {
        this.corBackground = background;
        updateBackgroundColor(background);
        
    }
    
    public void setBackgroundColorEntered (Color background) {
        this.corBackgroundEntered = background;
        
    }
    
    @Override
    public void setWidth (double width) {
        this.width = width;
        background.setWidth(width);
        setMaxWidth(width);
        super.setWidth(width);
        atualizaArco();
    }
    
    @Override
    public void setHeight (double height) {
        this.height = height;
        background.setHeight(height);
        setMaxHeight(height);
        super.setHeight(height);
        atualizaArco();
    }
    
    public String getText () {
        return value;
    }
    
    public boolean getDisable() {
        return disable;
    }
    
    protected void updateTextColor (Color cor) {
        this.texto.setFill(cor);
    }
    
    protected void updateTextSize (double size) {
        this.texto.setFont(Font.loadFont(Atributo.FONTE_PADRAO, size));
    }
    
    protected void updateBackgroundColor (Color background) {
        this.background.setFill(background);
    }
    
    private void atualizaArco () {
        double arco = background.getWidth() < background.getHeight()
                    ? background.getWidth()*.18
                    : background.getHeight()*.18;
        
        background.setArcWidth(arco);
        background.setArcHeight(arco);
    }
    
    public void disable(boolean disable) {
        this.disable = disable;
        setEffect(null);
        setCursor(disable ? Cursor.DEFAULT : Cursor.HAND);
        setOpacity(disable ? .4 : 1);
        this.setDisable(disable);
    }
    
    public void disable() {
        disable(true);
    }
    
    public void onClick(EventHandler eh) {
        adicionarEvento(MouseEvent.MOUSE_CLICKED, eh);
    }
    
    public void onClick(SceneUtil tela) {
        onClick(new EventHandler<MouseEvent> () { 
            @Override
            public void handle(MouseEvent t) {
                TicTrackToe.abrirTela(tela);
            }
    
        });
    }
    
    public void adicionarEvento (EventType et, EventHandler eh) {
        getChildren().forEach(filho -> {
            filho.addEventHandler(et, eh);
        });
    }
}
