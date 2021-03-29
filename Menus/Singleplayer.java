import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Singleplayer implements ActionListener {

    JFrame mainMenuFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenuFrame.setVisible(false);
        Chess c = new Chess();
    }
}
