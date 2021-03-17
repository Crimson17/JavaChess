import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chess implements ActionListener {

    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel titleText = new JLabel();
    JButton[] buttons = new JButton[64];

    String whiteFirstFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    int rememberButtonIndex;

    boolean figureIsSelected = false;
    boolean whitesTurn = true;

    Color highlightColor = new Color(137, 207, 240);
    Color stationaryHighlightColor = new Color(100, 170, 180);



    /*
    0 Pawn
    1 Knight
    2 Bishop
    3 Rook
    4 Queen
    6 King
    +
    0 White
    6 Black
    */
    // Loading figure images
    Icon[] figures = {
            new ImageIcon("data/w_pawn.png"),
            new ImageIcon("data/w_knight.png"),
            new ImageIcon("data/w_bishop.png"),
            new ImageIcon("data/w_rook.png"),
            new ImageIcon("data/w_queen.png"),
            new ImageIcon("data/w_king.png"),
            new ImageIcon("data/b_pawn.png"),
            new ImageIcon("data/b_knight.png"),
            new ImageIcon("data/b_bishop.png"),
            new ImageIcon("data/b_rook.png"),
            new ImageIcon("data/b_queen.png"),
            new ImageIcon("data/b_king.png")};


    Chess(){

        // Setting up the window and buttons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1050);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);

        titleText.setBackground(new Color(50,50,50));
        titleText.setForeground(new Color(250,250,250));
        titleText.setFont(new Font("Ink Free", Font.BOLD, 75));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("White's turn!");
        titleText.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0,0,1000,50);

        buttonPanel.setLayout(new GridLayout(8,8));
        buttonPanel.setBackground(new Color(50,50,50));

        for(int i=0; i<64; i++){
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setName("0");
            buttons[i].setHorizontalTextPosition(JLabel.CENTER);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45)));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        Recolor();

        titlePanel.add(titleText);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        FEN(whiteFirstFEN);
    }

    // Runs on every button click
    @Override
    public void actionPerformed(ActionEvent e) {
        if(figureIsSelected){
            MoveFigure(e);
        }
        else{
            HighlightFigure(e);
        }
        // Dev Tool

        System.out.println("\n---------------\n");
        for(int i=0; i<64; i++){
            if(i != 0 && i % 8 == 0){
                System.out.println();
            }
            System.out.print(buttons[i].getName() + " ");
        }

    }

    // Uses a highlighting function depending on what type of figure is selected
    public void HighlightFigure(ActionEvent e){
        for(int i=0; i<64; i++){
            if(e.getSource() == buttons[i]){
                switch (buttons[i].getName()) {
                    case "P":
                    case "p":
                        HighlightPawn(i);
                        i=64;
                        break;
                    case "N":
                    case "n":
                        HighlightKnight(i);
                        i=64;
                        break;
                    case "B":
                    case "b":
                        HighlightBishop(i);
                        i=64;
                        break;
                    case "R":
                    case "r":
                        HighlightRook(i);
                        i=64;
                        break;
                    case "Q":
                    case "q":
                        HighlightQueen(i);
                        i=64;
                        break;
                    case "K":
                    case "k":
                        HighlightKing(i);
                        i=64;
                        break;
                }
            }
        }
    }

    // These are for specific figure highlighting, doesn't move the figure
    public void HighlightPawn(int FigureIndex){
        if(FigureIsBlack(FigureIndex) == 0 && FigureIndex > 7 && whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIndex > 47 && FigureIndex < 56 && buttons[FigureIndex - 8].getName().equals("0") && buttons[FigureIndex - 16].getName().equals("0")){
                buttons[FigureIndex - 16].setBackground(highlightColor);
            }
            if(buttons[FigureIndex - 8].getName().equals("0")){
                buttons[FigureIndex - 8].setBackground(highlightColor);
            }
            if(FigureIndex % 8 != 0 && FigureIsBlack(FigureIndex - 9) == 1){
                buttons[FigureIndex - 9].setBackground(highlightColor);
            }
            if(FigureIndex % 8 != 7 && FigureIsBlack(FigureIndex - 7) == 1){
                buttons[FigureIndex - 7].setBackground(highlightColor);
            }
            figureIsSelected = true;
        }
        else if(FigureIsBlack(FigureIndex) == 1 && FigureIndex < 56 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIndex > 7 && FigureIndex < 16 && buttons[FigureIndex + 8].getName().equals("0") && buttons[FigureIndex + 16].getName().equals("0")){
                buttons[FigureIndex + 16].setBackground(highlightColor);
            }
            if(buttons[FigureIndex + 8].getName().equals("0")){
                buttons[FigureIndex + 8].setBackground(highlightColor);
            }
            if(FigureIndex % 8 != 0 && FigureIsBlack(FigureIndex + 7) == 0){
                buttons[FigureIndex + 7].setBackground(highlightColor);
            }
            if(FigureIndex % 8 != 7 && FigureIsBlack(FigureIndex + 9) == 0){
                buttons[FigureIndex + 9].setBackground(highlightColor);
            }
            figureIsSelected = true;
        }
    }
    public void HighlightKnight(int FigureIndex){

    }
    public void HighlightBishop(int FigureIndex){

    }
    public void HighlightRook(int FigureIndex){

    }
    public void HighlightQueen(int FigureIndex){

    }
    public void HighlightKing(int FigureIndex){

    }

    // Moves the figure to the pressed button if the button is already highlighted
    public void MoveFigure(ActionEvent e){
        for(int i=0; i<64; i++){
            if(buttons[i].getBackground() == stationaryHighlightColor){
                rememberButtonIndex = i;
                i=64;
            }
        }
        for(int i=0; i<64; i++){
            if(e.getSource() == buttons[i]){
                if(buttons[i].getBackground() == highlightColor){
                    buttons[i].setName(buttons[rememberButtonIndex].getName());
                    buttons[i].setIcon(buttons[rememberButtonIndex].getIcon());
                    buttons[rememberButtonIndex].setName("0");
                    buttons[rememberButtonIndex].setIcon(null);
                    Recolor();
                    figureIsSelected = false;
                    WhosTurn();
                    i=64;
                }
            }
        }
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

    // Resets the colors for the whole board
    public void Recolor(){
        boolean useEven = true;
        for(int i=0; i<64; i++){
            if(i != 0 && i % 8 == 0){
                useEven = !useEven;
            }
            if(i % 2 == 0 && useEven || i % 2 != 0 && !useEven){
                buttons[i].setBackground(new Color(240, 217, 183));
            }
            else{
                buttons[i].setBackground(new Color(180, 136, 102));
            }
        }
    }

    // Checks what color is the figure at certain spot
    public int FigureIsBlack(int n){
        String figureName = buttons[n].getName();
        if(figureName.equals("0")){
            //System.out.println("zero");
            return -1;
        }
        String[] whites = {"P", "N", "B", "R", "Q", "K"};
        for(String x : whites){
            if(figureName.equals(x)){
                //System.out.println("white");
                return 0;
            }
        }
        //System.out.println("black");
        return 1;
    }

    // Self explanatory
    public void WhosTurn(){
        if(whitesTurn){
            whitesTurn = false;
            titleText.setText("Black's turn!");
        }
        else{
            whitesTurn = true;
            titleText.setText("White's turn!");
        }
    }
}