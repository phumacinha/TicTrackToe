/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author pedro
 */
public class PopUp extends VBox {
    private VBox botoes;
    private Botao fechar;
    private boolean closeable = false;
    
    public PopUp () {
        super();
    }
    
    public PopUp(String textoTitulo, String textoDetalhe) {
        super();
        inicializar(textoTitulo, textoDetalhe);
    }
    
    public PopUp(String textoDetalhe) {
        super();
        inicializar(null, textoDetalhe);
    }
    
    private void inicializar(String textoTitulo, String textoDetalhe) {
        botoes = new VBox();
        botoes.setSpacing(30);
        botoes.setPadding(new Insets(66, 0, 0, 0));
        botoes.setAlignment(Pos.CENTER);
        
        fechar = new Botao("Fechar aviso");
        fechar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                fechar();
            }
        });
        
        RadialGradient rdGra = new RadialGradient(0, 0, .5, .5, .75, true, CycleMethod.NO_CYCLE, new Stop[] {
        new Stop(0, Color.rgb(255, 255, 255, 1)),
        new Stop(.40, Color.rgb(255, 255, 255, .9375)),
        new Stop(.75, Color.rgb(255, 255, 255, .89375)),
        new Stop(1, Color.rgb(255, 255, 255, .8))
    });
        BackgroundFill bgFill = new BackgroundFill(rdGra, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(bgFill));
        
        CaixaDeTexto titulo = new CaixaDeTexto(textoTitulo, 24);
        titulo.setTextAlignment(TextAlignment.CENTER);
        titulo.setAlignment(Pos.CENTER);
        titulo.setTextFill(Atributo.ROXO_2);
        
        CaixaDeTexto detalhe = new CaixaDeTexto(textoDetalhe, 42);
        detalhe.setTextAlignment(TextAlignment.CENTER);
        detalhe.setAlignment(Pos.CENTER);
        detalhe.setTextFill(Atributo.ROXO_2);
        
        VBox conteudo = new VBox(titulo, detalhe);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setSpacing(60);
        
        setPadding(new Insets(0, 0, 100, 0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(conteudo, botoes);
    }
    
    private void fechar () {
        if (getParent() instanceof Pane) {
            ((Pane) getParent()).getChildren().remove(this);
        }
    }
    
    public void setCloseable() {
        setCloseable(fechar.getText());
    }
    
    public void setCloseable(String string) {
        fechar.setText(string);
        addBotao(fechar);
        closeable = true;
    }
    
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
