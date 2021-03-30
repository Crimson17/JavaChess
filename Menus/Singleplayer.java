import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Singleplayer implements ActionListener {
    JFrame mainMenuFrame;
    ChessSP chessSP;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenuFrame.setVisible(false);
        chessSP = new ChessSP();
        chessSP.mMenu.mainMenuFrame = mainMenuFrame;
        chessSP.mMenu.chess = chessSP.frame;
    }
}
