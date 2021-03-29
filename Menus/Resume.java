import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resume implements ActionListener {
    JPanel resumePanel;
    JPanel optionsPanel;
    JPanel mainMenuPanel;
    JPanel quitPanel;
    JPanel escPanel;
    JButton[] buttons;

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0; i<64; i++){
            buttons[i].setEnabled(true);
        }
        escPanel.setVisible(false);
        resumePanel.setVisible(false);
        optionsPanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        quitPanel.setVisible(false);
    }
}
