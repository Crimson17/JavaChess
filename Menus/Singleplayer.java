import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Singleplayer implements ActionListener {
    JFrame mainMenuFrame;
    Chess chess;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenuFrame.setVisible(false);
        chess = new Chess();
        chess.mMenu.mainMenuFrame = mainMenuFrame;
        chess.mMenu.chess = chess.frame;
    }
}
