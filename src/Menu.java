import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Menu {

    Singleplayer sp = new Singleplayer();
    Multiplayer mp = new Multiplayer();
    Options op = new Options();
    Quit q = new Quit();

    JFrame mainMenu = new JFrame();
    JPanel menuPanel = new JPanel();
    JLabel menuLabel = new JLabel();
    JPanel spPanel = new JPanel();
    JButton spButton = new JButton();
    JPanel mpPanel = new JPanel();
    JButton mpButton = new JButton();
    JPanel optionsPanel = new JPanel();
    JButton optionsButton = new JButton();
    JPanel quitPanel = new JPanel();
    JButton quitButton = new JButton();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int windowWidth = (int)screenSize.getHeight() - 100;
    int windowHeight = windowWidth + (windowWidth/25);

    Menu(){
        sp.mainMenuFrame = mainMenu;

        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setSize(windowWidth,windowHeight);
        mainMenu.getContentPane().setBackground(new Color(50, 50, 50));
        mainMenu.setLayout(new BorderLayout());
        mainMenu.setVisible(true);
        mainMenu.setResizable(false);

        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBounds(0,0,windowWidth,windowHeight);
        menuLabel.setHorizontalAlignment(JLabel.CENTER);
        menuLabel.setOpaque(true);
        menuLabel.setIcon(icons[0]);

        spPanel.setLayout(new BorderLayout());
        spPanel.setBounds(windowWidth/3,windowHeight/8,windowWidth/3,windowHeight/16);
        spButton.setBackground(new Color(50,50,50));
        spButton.setForeground(new Color(250,250,250));
        spButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        spButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        spButton.setHorizontalAlignment(JLabel.CENTER);
        spButton.setText("Singleplayer");
        spButton.setFocusable(false);
        spButton.addActionListener(sp);
        spButton.setOpaque(true);

        mpPanel.setLayout(new BorderLayout());
        mpPanel.setBounds(windowWidth/3,windowHeight/8*2,windowWidth/3,windowHeight/16);
        mpButton.setBackground(new Color(50,50,50));
        mpButton.setForeground(new Color(250,250,250));
        mpButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        mpButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        mpButton.setHorizontalAlignment(JLabel.CENTER);
        mpButton.setText("Multiplayer");
        mpButton.setFocusable(false);
        mpButton.addActionListener(mp);
        mpButton.setOpaque(true);

        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBounds(windowWidth/3,windowHeight/8*3,windowWidth/3,windowHeight/16);
        optionsButton.setBackground(new Color(50,50,50));
        optionsButton.setForeground(new Color(250,250,250));
        optionsButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        optionsButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        optionsButton.setHorizontalAlignment(JLabel.CENTER);
        optionsButton.setText("Options");
        optionsButton.setFocusable(false);
        optionsButton.addActionListener(op);
        optionsButton.setOpaque(true);

        quitPanel.setLayout(new BorderLayout());
        quitPanel.setBounds(windowWidth/3,windowHeight/8*4,windowWidth/3,windowHeight/16);
        quitButton.setBackground(new Color(50,50,50));
        quitButton.setForeground(new Color(250,250,250));
        quitButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        quitButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        quitButton.setHorizontalAlignment(JLabel.CENTER);
        quitButton.setText("Quit");
        quitButton.setFocusable(false);
        quitButton.addActionListener(q);
        quitButton.setOpaque(true);

        // Adding labels to panels and panels to frame
        menuPanel.add(menuLabel);
        spPanel.add(spButton);
        mpPanel.add(mpButton);
        optionsPanel.add(optionsButton);
        quitPanel.add(quitButton);

        mainMenu.add(spPanel);
        mainMenu.add(mpPanel);
        mainMenu.add(optionsPanel);
        mainMenu.add(quitPanel);
        mainMenu.add(menuPanel);
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
