package tela.elemento;

import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe Cursor.
 * @author Pedro Antônio de Souza
 */
public class CursorCirculo extends ImageCursor { 
    public static ImageCursor getCursor () {
        
        Circle visao = new Circle(30, Color.rgb(255, 255, 0, .8));
        
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        
        Image image = visao.snapshot(sp, null); 
        
        return new ImageCursor(image, visao.getRadius(), visao.getRadius());
    }

}
