import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mMenu implements ActionListener {
    JFrame mainMenuFrame;
    JFrame chess;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenuFrame.setVisible(true);
        chess.setVisible(false);
        chess = null;
    }
}
