package rastreadordevisao;


import com.theeyetribe.clientsdk.IGazeListener;
import com.theeyetribe.clientsdk.data.GazeData;
import java.awt.AWTException;
import java.awt.Robot;

/**
 * Classe GazeListener.
 * @author Pedro Antônio de Souza
 */
public class GazeListener implements IGazeListener {
        private Robot mouse;
        
        public GazeListener() throws AWTException {
            mouse = new Robot();
        }
        
        @Override
        public void onGazeUpdate(GazeData gazeData)
        {
            mouse.mouseMove((int) gazeData.smoothedCoordinates.x, (int) gazeData.smoothedCoordinates.y);
        }
        
        
    }