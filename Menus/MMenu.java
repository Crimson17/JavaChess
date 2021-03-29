import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MMenu implements ActionListener {
    JFrame mainMenuFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainMenuFrame.setVisible(true);
    }
}
