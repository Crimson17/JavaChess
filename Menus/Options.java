import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Options implements ActionListener {
    JFrame mainMenuFrame;

    Light l = new Light();
    Dark d = new Dark();

    JFrame optionsFrame = new JFrame();
    JPanel optionsPanel = new JPanel();
    JLabel optionsLabel = new JLabel();
    JPanel lightButtonPanel = new JPanel();
    JButton lightButtonButton = new JButton();
    JPanel darkButtonPanel = new JPanel();
    JButton darkButtonButton = new JButton();
    JPanel mMenuPanel = new JPanel();
    JButton mMenuButton = new JButton();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int windowWidth = (int)screenSize.getHeight() - 100;
    int windowHeight = windowWidth + (windowWidth/25);

    boolean firstShowOptions = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(firstShowOptions){
            optionsFrame.setVisible(!optionsFrame.isVisible());
            mainMenuFrame.setVisible(!mainMenuFrame.isVisible());
        }
        else{
            mainMenuFrame.setVisible(!mainMenuFrame.isVisible());
            optionsFrame.setVisible(!optionsFrame.isVisible());
        }
        firstShowOptions = !firstShowOptions;
    }

    Options(){
        optionsFrame.setVisible(true);
        optionsFrame.setVisible(false);
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setSize(windowWidth,windowHeight);
        optionsFrame.getContentPane().setBackground(new Color(50, 50, 50));
        optionsFrame.setLayout(new BorderLayout());
        optionsFrame.setLocation(screenSize.width/2-windowWidth/2, (int)(screenSize.height/2.05-windowHeight/2));
        optionsFrame.setResizable(false);

        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBounds(0,0,windowWidth,windowHeight);
        optionsLabel.setHorizontalAlignment(JLabel.CENTER);
        optionsLabel.setOpaque(true);
        optionsLabel.setIcon(icons[0]);

        lightButtonPanel.setLayout(new BorderLayout());
        lightButtonPanel.setBounds(windowWidth/3,windowHeight/8,windowWidth/3,windowHeight/16);
        lightButtonButton.setBackground(new Color(50,50,50));
        lightButtonButton.setForeground(new Color(250,250,250));
        lightButtonButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        lightButtonButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        lightButtonButton.setHorizontalAlignment(JLabel.CENTER);
        lightButtonButton.setText("Light button: ");
        lightButtonButton.setFocusable(false);
        lightButtonButton.addActionListener(l);
        lightButtonButton.setOpaque(true);

        darkButtonPanel.setLayout(new BorderLayout());
        darkButtonPanel.setBounds(windowWidth/3,windowHeight/8*2,windowWidth/3,windowHeight/16);
        darkButtonButton.setBackground(new Color(50,50,50));
        darkButtonButton.setForeground(new Color(250,250,250));
        darkButtonButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        darkButtonButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        darkButtonButton.setHorizontalAlignment(JLabel.CENTER);
        darkButtonButton.setText("Dark button: ");
        darkButtonButton.setFocusable(false);
        darkButtonButton.addActionListener(d);
        darkButtonButton.setOpaque(true);

        mMenuPanel.setLayout(new BorderLayout());
        mMenuPanel.setBounds(windowWidth/3,windowHeight/8*3,windowWidth/3,windowHeight/16);
        mMenuButton.setBackground(new Color(50,50,50));
        mMenuButton.setForeground(new Color(250,250,250));
        mMenuButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        mMenuButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        mMenuButton.setHorizontalAlignment(JLabel.CENTER);
        mMenuButton.setText("Apply/Back");
        mMenuButton.setFocusable(false);
        mMenuButton.addActionListener(this);
        mMenuButton.setOpaque(true);


        // Adding labels to panels and panels to frame
        optionsPanel.add(optionsLabel);
        lightButtonPanel.add(lightButtonButton);
        darkButtonPanel.add(darkButtonButton);
        mMenuPanel.add(mMenuButton);

        optionsFrame.add(lightButtonPanel);
        optionsFrame.add(darkButtonPanel);
        optionsFrame.add(mMenuPanel);
        optionsFrame.add(optionsPanel);
    }

    // Loading all image icons from res folder
    ImageIcon[] icons = {
            ImageToIcon("mainmenu.jpg")};

    // Loading Image from res folder, scale the image to fit the button and return as ImageIcon
    public ImageIcon ImageToIcon(String FileName){
        Image loadedImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(FileName))).getImage();
        Image scaledImage = loadedImage.getScaledInstance(windowWidth, windowHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
