import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Chess {
    Moving moving = new Moving();
    Check c = new Check();
    Mate m = new Mate();

    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel titleText = new JLabel();
    JButton[] buttons = new JButton[64];

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

    Chess(){
        // Assigning variables in the logic class
        moving.buttons = buttons;
        moving.titleText = titleText;
        moving.figures = figures;

        // Setting up the window and buttons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth,windowHeight);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);

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

        titlePanel.add(titleText);
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
