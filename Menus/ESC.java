import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ESC implements KeyListener {
    JPanel resumePanel;
    JPanel optionsPanel;
    JPanel mainMenuPanel;
    JPanel quitPanel;
    JPanel escPanel;
    JButton[] buttons;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ESCAPE){
            for(int i=0; i<64; i++){
                buttons[i].setEnabled(false);
            }
            escPanel.setVisible(true);
            resumePanel.setVisible(true);
            mainMenuPanel.setVisible(true);
            quitPanel.setVisible(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
