import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Visuals{
    Logic logic = new Logic();

    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel titleText = new JLabel();
    JButton[] buttons = new JButton[64];

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int windowWidth = (int)screenSize.getHeight() - 100;
    int windowHeight = windowWidth + (windowWidth/25);

    String whiteFirstFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

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

    Visuals(){
        // Assigning variables in the logic class
        logic.buttons = buttons;
        logic.titleText = titleText;
        logic.figures = figures;

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
            buttons[i].addActionListener(logic);
        }

        logic.Recolor();

        titlePanel.add(titleText);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        FEN(whiteFirstFEN);
    }

    // Loading Image from res folder, scale the image to fit the button and return as ImageIcon
    public ImageIcon resImageToIcon(String FileName){
        Image loadedImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(FileName))).getImage();
        Image scaledImage = loadedImage.getScaledInstance(windowWidth/8, windowWidth/8, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // Loading FEN type of key into the board
    public void FEN(String key){
        char[] FenLabels = {'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k'};
        int sum = 0;

        for(int i=0; i<key.length(); i++){
            if(key.charAt(i) > 48 && key.charAt(i) < 57){
                sum += key.charAt(i) - 48 - 1;
            }
            else if(key.charAt(i) == ' '){
                i = key.length();
            }
            else if(key.charAt(i) != '/') {
                buttons[i + sum].setIcon(figures[new String(FenLabels).indexOf(key.charAt(i))]);
                buttons[i + sum].setName(String.valueOf(key.charAt(i)));
            }
            else{
                sum--;
            }
        }
    }
}
