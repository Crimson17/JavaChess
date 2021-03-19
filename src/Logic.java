import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logic implements ActionListener{

    // Aren't the copies but actual buttons and tittleText :) (I finally get how classes work)
    JButton[] buttons = {};
    JLabel titleText;

    boolean devTool = true;
    boolean figureIsSelected = false;
    boolean whitesTurn = true;

    Color highlightColor = new Color(137, 207, 240);
    Color stationaryHighlightColor = new Color(100, 170, 180);

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
        // White
        if(FigureIsBlack(FigureIndex) == 0 && FigureIndex > 7 && whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIndex > 47 && FigureIndex < 56 && FigureIsBlack(FigureIndex-8) == -1 && FigureIsBlack(FigureIndex-16) == -1){
                buttons[FigureIndex - 16].setBackground(highlightColor);
            }
            if(FigureIsBlack(FigureIndex-8) == -1){
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
        // Black
        else if(FigureIsBlack(FigureIndex) == 1 && FigureIndex < 56 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIndex > 7 && FigureIndex < 16 && FigureIsBlack(FigureIndex+8) == -1 && FigureIsBlack(FigureIndex+16) == -1){
                buttons[FigureIndex + 16].setBackground(highlightColor);
            }
            if(FigureIsBlack(FigureIndex+8) == -1){
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
        // White
        if(FigureIsBlack(FigureIndex) == 0 && whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIsBlack(FigureIndex-15) == -1 || FigureIsBlack(FigureIndex-15) == 1 && FigureIndex > 15){
                buttons[FigureIndex - 15].setBackground(highlightColor);
            }
            figureIsSelected = true;
        }
        // Black
        else if(FigureIsBlack(FigureIndex) == 1 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            if(FigureIsBlack(FigureIndex-15) == -1 || FigureIsBlack(FigureIndex-15) == 0 && FigureIndex > 15){
                buttons[FigureIndex - 15].setBackground(highlightColor);
            }
            figureIsSelected = true;
        }

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
        int rememberButtonIndex = -1;
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
                    WhosTurn();
                    figureIsSelected = false;
                }
                else if(buttons[i].getBackground() == stationaryHighlightColor){
                    Recolor();
                    figureIsSelected = false;
                }
                i = 64;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(figureIsSelected){
            MoveFigure(e);
        }
        else{
            HighlightFigure(e);
        }

        // Dev Tool
        if (devTool) {
            System.out.println("\n---------------\n");
            for (int i = 0; i < 64; i++) {
                if (i != 0 && i % 8 == 0) {
                    System.out.println();
                }
                System.out.print(buttons[i].getName() + " ");
            }
        }
    }
}