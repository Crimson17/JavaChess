import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessSP {
    Moving moving = new Moving();
    Check c = new Check();
    Mate m = new Mate();
    ESC e = new ESC();
    Quit q = new Quit();
    Resume r = new Resume();
    mMenu mMenu = new mMenu();

    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel titleText = new JLabel();
    JButton[] buttons = new JButton[64];

    JPanel escPanel = new JPanel();

    JPanel resumePanel = new JPanel();
    JButton resumeButton = new JButton();
    JPanel optionsPanel = new JPanel();
    JButton optionsButton = new JButton();
    JPanel mMenuPanel = new JPanel();
    JButton mMenuButton = new JButton();
    JPanel quitPanel = new JPanel();
    JButton quitButton = new JButton();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int windowWidth = (int)screenSize.getHeight() - 100;
    int windowHeight = windowWidth + (windowWidth/25);

    String DefaultFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    String WipFEN = "8/1Q3Pn1/3B4/1N6/4k1q1/2R5/1p1K1b2/7r";

    // Loading all image icons from res folder
    ImageIcon[] figures = {
            resImageToIcon("w_pawn.png"),
            resImageToIcon("w_knight.png"),
            resImageToIcon("w_bishop.png"),
            resImageToIcon("w_rook.png"),
            resImageToIcon("w_queen.png"),
            resImageToIcon("w_king.png"),
            resImageToIcon("b_pawn.png"),
            resImageToIcon("b_knight.png"),
            resImageToIcon("b_bishop.png"),
            resImageToIcon("b_rook.png"),
            resImageToIcon("b_queen.png"),
            resImageToIcon("b_king.png")};

    ChessSP(){
        // Assigning variables in the logic class
        moving.buttons = buttons;
        moving.titleText = titleText;
        moving.figures = figures;

        e.resumePanel = resumePanel;
        e.optionsPanel = optionsPanel;
        e.mainMenuPanel = mMenuPanel;
        e.quitPanel = quitPanel;
        e.escPanel = escPanel;
        e.buttons = buttons;

        r.resumePanel = resumePanel;
        r.optionsPanel = optionsPanel;
        r.mainMenuPanel = mMenuPanel;
        r.quitPanel = quitPanel;
        r.escPanel = escPanel;
        r.buttons = buttons;

        // Setting up the window and buttons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth,windowHeight);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setLocation(screenSize.width/2-windowWidth/2, (int)(screenSize.height/2.05-windowHeight/2));
        frame.setResizable(false);
        frame.addKeyListener(e);
        frame.setVisible(true);

        // North title
        titleText.setBackground(new Color(50,50,50));
        titleText.setForeground(new Color(250,250,250));
        titleText.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("White's turn!");
        titleText.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0,0,windowWidth,windowHeight-windowWidth);

        // Whole button panel
        buttonPanel.setLayout(new GridLayout(8,8));
        buttonPanel.setBackground(new Color(50,50,50));

        // Setting up all buttons
        for(int i=0; i<64; i++){
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setName("0");
            buttons[i].setHorizontalTextPosition(JLabel.CENTER);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45)));
            buttons[i].setFocusable(false);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(moving);
        }

        moving.Recolor();

        escPanel.setVisible(false);
        escPanel.setLayout(new BorderLayout());
        escPanel.setBounds(windowWidth/4-7,windowWidth/12, windowWidth/2, (int)(windowHeight/1.8));
        escPanel.setBackground(new Color(40, 40, 40));
        escPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        resumePanel.setVisible(false);
        resumePanel.setLayout(new BorderLayout());
        resumePanel.setBounds(windowWidth/3,windowHeight/8,windowWidth/3,windowHeight/16);
        resumeButton.setBackground(new Color(50,50,50));
        resumeButton.setForeground(new Color(250,250,250));
        resumeButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        resumeButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        resumeButton.setHorizontalAlignment(JLabel.CENTER);
        resumeButton.setText("Resume");
        resumeButton.setFocusable(false);
        resumeButton.addActionListener(r);
        resumeButton.setOpaque(true);

        optionsPanel.setVisible(false);
        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBounds(windowWidth/3,windowHeight/8*2,windowWidth/3,windowHeight/16);
        optionsButton.setBackground(new Color(50,50,50));
        optionsButton.setForeground(new Color(250,250,250));
        optionsButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        optionsButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        optionsButton.setHorizontalAlignment(JLabel.CENTER);
        optionsButton.setText("Options");
        optionsButton.setFocusable(false);
        //optionsButton.addActionListener();
        optionsButton.setOpaque(true);

        mMenuPanel.setVisible(false);
        mMenuPanel.setLayout(new BorderLayout());
        mMenuPanel.setBounds(windowWidth/3,windowHeight/8*3,windowWidth/3,windowHeight/16);
        mMenuButton.setBackground(new Color(50,50,50));
        mMenuButton.setForeground(new Color(250,250,250));
        mMenuButton.setFont(new Font("Ink Free", Font.BOLD, windowHeight-windowWidth));
        mMenuButton.setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
        mMenuButton.setHorizontalAlignment(JLabel.CENTER);
        mMenuButton.setText("Main Menu");
        mMenuButton.setFocusable(false);
        mMenuButton.addActionListener(mMenu);
        mMenuButton.setOpaque(true);

        quitPanel.setVisible(false);
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


        resumePanel.add(resumeButton);
        optionsPanel.add(optionsButton);
        mMenuPanel.add(mMenuButton);
        quitPanel.add(quitButton);
        titlePanel.add(titleText);

        frame.add(resumePanel);
        frame.add(optionsPanel);
        frame.add(mMenuPanel);
        frame.add(quitPanel);
        frame.add(escPanel);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        LoadFEN(DefaultFEN, buttons);
    }

    // Loading Image from res folder, scale the image to fit the button and return as ImageIcon
    public ImageIcon resImageToIcon(String FileName){
        Image loadedImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(FileName))).getImage();
        Image scaledImage = loadedImage.getScaledInstance(windowWidth/8, windowWidth/8, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Loading FEN type of key into the board
    public void LoadFEN(String key, JButton[] buttons){
        char[] FenLabels = {'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k'};
        int sum = 0;

        for(int i=0; i<key.length(); i++){
            if(key.charAt(i) == ' '){
                break;
            }
            if(key.charAt(i) > 48 && key.charAt(i) < 57){
                sum += key.charAt(i) - 48 - 1;
            }
            else if(key.charAt(i) != '/') {
                buttons[i + sum].setIcon(figures[new String(FenLabels).indexOf(key.charAt(i))]);
                buttons[i + sum].setName(String.valueOf(key.charAt(i)));
            }
            else{
                sum--;
            }
        }
        CheckSituation(buttons);
    }
    // Makes a FEN from given board
    public String MakeFEN(JButton[] buttons){
        char[] FenLabels = {'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k'};
        char[] fen = new char[72];
        int counter = 0;
        int j = 0;
        int i;

        for(i=0; i<buttons.length; i++){
            if(i != 0 && i % 8 == 0){
                fen[i+j] = '/';
                j++;
            }
            if(!buttons[i].getName().equals("") && !buttons[i].getName().equals("0")){
                for(int k=0; k<FenLabels.length; k++){
                    if(buttons[i].getName().toCharArray()[0] == FenLabels[k]){
                        fen[i+j] = FenLabels[k];
                        k = FenLabels.length;
                    }
                    else if(k == FenLabels.length-1){
                        System.out.println("Couldn't find figures name in FenLabels, returning empty string..");
                        return "";
                    }
                }
            }
            else{
                counter++;
                if(counter != 0){
                    if(i % 8 == 7 || (!buttons[i+1].getName().equals("") && !buttons[i+1].getName().equals("0"))){
                        fen[i+j] = (char)(counter + 48);
                        counter = 0;
                    }
                }
            }
        }
        fen[i+j] = ' ';
        return new String(fen);
    }

    public void CheckSituation(JButton[] buttons){
        Color checkMateHighlight = new Color(176, 25, 25);

        if(c.WhiteCheck(buttons)){
            for(int i=0; i<64; i++){
                if(buttons[i].getName().equals("K")){
                    buttons[i].setBackground(new Color(232, 143, 26));
                    break;
                }
            }
        }
        // Check for black check
        else if(c.BlackCheck(buttons)){
            for(int i=0; i<64; i++){
                if(buttons[i].getName().equals("k")){
                    buttons[i].setBackground(new Color(232, 143, 26));
                    break;
                }
            }
        }
        // Check for white cm
        if(c.WhiteCheck(buttons) && m.AllowedMovement(true, buttons) == 0){
            titleText.setText("White checkmated!");
            for(int i=0; i<64; i++){
                if(buttons[i].getName().equals("K")){
                    buttons[i].setBackground(checkMateHighlight);
                }
                buttons[i].setEnabled(false);
            }
        }
        // Check for black cm
        else if(c.BlackCheck(buttons) && m.AllowedMovement(false, buttons) == 0){
            titleText.setText("Black checkmated!");
            for(int i=0; i<64; i++){
                if(buttons[i].getName().equals("k")){
                    buttons[i].setBackground(checkMateHighlight);
                }
                buttons[i].setEnabled(false);
            }
        }

        // Check for draw
        else if((m.AllowedMovement(true, buttons) == 0 && !c.WhiteCheck(buttons)) || (m.AllowedMovement(false, buttons) == 0 && !c.BlackCheck(buttons))){
            titleText.setText("Draw!");
            for(int i=0; i<64; i++){
                buttons[i].setEnabled(false);
            }
        }
    }
}
