import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logic implements ActionListener{

    // Aren't the copies but actual buttons and tittleText :) (I finally get how classes work)
    JButton[] buttons = {};
    JLabel titleText;
    ImageIcon[] figures;

    boolean devTool = true;
    boolean figureIsSelected = false;
    boolean whitesTurn = true;

    boolean wKingHasMoved = false;
    boolean wRRookHasMoved = false;
    boolean wLRookHasMoved = false;
    boolean bKingHasMoved = false;
    boolean bRRookHasMoved = false;
    boolean bLRookHasMoved = false;

    Color highlightColor = new Color(137, 207, 240);
    Color stationaryHighlightColor = new Color(100, 170, 180);
    Color castlingHighlight = new Color(113, 194, 50);

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
        if(FigureIsWhite(FigureIndex) == 1 && whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {1, 2, 1, 1};

            // Check if pawn is on last row
            if(FigureIndex > 7){
                // Check if one spot in front is empty
                if(FigureIsWhite(CalculateCords(FigureIndex, xCord[0], yCord[0])) == -1){
                    buttons[CalculateCords(FigureIndex, xCord[0], yCord[0])].setBackground(highlightColor);
                    // Check if spot two steps in front is empty
                    if(FigureIndex > 47 && FigureIsWhite(CalculateCords(FigureIndex, xCord[1], yCord[1])) == -1){
                        buttons[CalculateCords(FigureIndex, xCord[1], yCord[1])].setBackground(highlightColor);
                    }
                }
                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[2], yCord[2])) == 0){
                    buttons[CalculateCords(FigureIndex, xCord[2], yCord[2])].setBackground(highlightColor);
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[3], yCord[3])) == 0){
                    buttons[CalculateCords(FigureIndex, xCord[3], yCord[3])].setBackground(highlightColor);
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {-1, -2, -1, -1};

            // Check if pawn is on last row
            if(FigureIndex < 56){
                // Check if one spot in front is empty
                if(FigureIsWhite(CalculateCords(FigureIndex, xCord[0], yCord[0])) == -1){
                    buttons[CalculateCords(FigureIndex, xCord[0], yCord[0])].setBackground(highlightColor);
                    // Check if spot two steps in front is empty
                    if(FigureIndex < 16 && FigureIsWhite(CalculateCords(FigureIndex, xCord[1], yCord[1])) == -1){
                        buttons[CalculateCords(FigureIndex, xCord[1], yCord[1])].setBackground(highlightColor);
                    }
                }
                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[2], yCord[2])) == 1){
                    buttons[CalculateCords(FigureIndex, xCord[2], yCord[2])].setBackground(highlightColor);
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[3], yCord[3])) == 1){
                    buttons[CalculateCords(FigureIndex, xCord[3], yCord[3])].setBackground(highlightColor);
                }
            }
        }
    }

    public void HighlightKnight(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1 && whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {-1, 1, 2, 2, -1, 1, -2, -2};
            int[] yCord = {2, 2, 1, -1, -2, -2, -1, 1};
            int cords;

            for(int i=0; i<xCord.length; i++){
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check if movement is inside the board
                if(cords >= 0 && cords < 64){
                    // Check if movement is to the left
                    if(xCord[i] < 0){
                        // Check if there is movement space to the left
                        if(Math.abs(xCord[i]) == 1 && FigureIndex % 8 > 0){
                            if(FigureIsWhite(cords) != 1){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            if(FigureIsWhite(cords) != 1){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            if(FigureIsWhite(cords) != 1){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6){
                            if(FigureIsWhite(cords) != 1){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {-1, 1, 2, 2, -1, 1, -2, -2};
            int[] yCord = {2, 2, 1, -1, -2, -2, -1, 1};
            int cords;

            for(int i=0; i<xCord.length; i++){
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check if movement is inside the board
                if(cords >= 0 && cords < 64){
                    // Check if movement is to the left
                    if(xCord[i] < 0){
                        // Check if there is movement space to the left
                        if(Math.abs(xCord[i]) == 1 && FigureIndex % 8 > 0){
                            if(FigureIsWhite(cords) != 0){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            if(FigureIsWhite(cords) != 0){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            if(FigureIsWhite(cords) != 0){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6){
                            if(FigureIsWhite(cords) != 0){
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                }
            }
        }
    }

    public void HighlightBishop(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1 && whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int xCord;
            int yCord;
            int[] xIncr = {1, 1, -1, -1};
            int[] yIncr = {1, -1, -1, 1};
            int cords;

            for(int i=0; i<4; i++){
                xCord = xIncr[i];
                yCord = yIncr[i];
                cords = CalculateCords(FigureIndex, xCord, yCord);
                // First check of the top and bottom bounds
                if(cords >= 0 && cords < 64){
                    // First check of the side bounds
                    if((!IsOnLeftBound(FigureIndex) && xCord < 0) || (!IsOnRightBound(FigureIndex) && xCord > 0)) {
                        // Highlight until the spot has a white figure
                        while(true){
                            cords = CalculateCords(FigureIndex, xCord, yCord);
                            // Check if figures are not the same color
                            if(FigureIsWhite(cords) == 1){
                                break;
                            }
                            // Highlight empty spots
                            else if(FigureIsWhite(cords) == -1){
                                buttons[cords].setBackground(highlightColor);
                            }
                            // If the spot has a black figure highlight and stop
                            else if(FigureIsWhite(cords) == 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            xCord += xIncr[i];
                            yCord += yIncr[i];
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0 && !whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int xCord;
            int yCord;
            int[] xIncr = {1, 1, -1, -1};
            int[] yIncr = {1, -1, -1, 1};
            int cords;

            for(int i=0; i<4; i++){
                xCord = xIncr[i];
                yCord = yIncr[i];
                cords = CalculateCords(FigureIndex, xCord, yCord);
                // First check of the top and bottom bounds
                if(cords >= 0 && cords < 64){
                    // First check of the side bounds
                    if((!IsOnLeftBound(FigureIndex) && xCord < 0) || (!IsOnRightBound(FigureIndex) && xCord > 0)) {
                        // Highlight until the spot has a black figure
                        while(true){
                            cords = CalculateCords(FigureIndex, xCord, yCord);
                            // Check if figures are not the same color
                            if(FigureIsWhite(cords) == 0){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                buttons[cords].setBackground(highlightColor);
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 1){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            xCord += xIncr[i];
                            yCord += yIncr[i];
                        }
                    }
                }
            }
        }
    }

    public void HighlightRook(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1 && whitesTurn){
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int xCord;
            int yCord;
            int[] xIncr = {1, -1, 0, 0};
            int[] yIncr = {0, 0, 1, -1};
            int cords;

            for(int i=0; i<4; i++){
                xCord = xIncr[i];
                yCord = yIncr[i];
                cords = CalculateCords(FigureIndex, xCord, yCord);
                // First check of the top and bottom bounds
                if(cords >= 0 && cords < 64){
                    // First check of the side bounds
                    if((!IsOnLeftBound(FigureIndex) && xCord < 0) || (!IsOnRightBound(FigureIndex) && xCord > 0) || xCord == 0) {
                        // Highlight until the spot has a black figure
                        while(true){
                            cords = CalculateCords(FigureIndex, xCord, yCord);
                            // Check if figures are not the same color
                            if(FigureIsWhite(cords) == 1){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                buttons[cords].setBackground(highlightColor);
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            xCord += xIncr[i];
                            yCord += yIncr[i];
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0 && !whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int xCord;
            int yCord;
            int[] xIncr = {1, -1, 0, 0};
            int[] yIncr = {0, 0, 1, -1};
            int cords;

            for(int i=0; i<4; i++){
                xCord = xIncr[i];
                yCord = yIncr[i];
                cords = CalculateCords(FigureIndex, xCord, yCord);
                // First check of the top and bottom bounds
                if(cords >= 0 && cords < 64){
                    // First check of the side bounds
                    if((!IsOnLeftBound(FigureIndex) && xCord < 0) || (!IsOnRightBound(FigureIndex) && xCord > 0) || xCord == 0) {
                        // Highlight until the spot has a black figure
                        while(true){
                            cords = CalculateCords(FigureIndex, xCord, yCord);
                            // Check if figures are not the same color
                            if(FigureIsWhite(cords) == 0){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                buttons[cords].setBackground(highlightColor);
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 1){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                buttons[cords].setBackground(highlightColor);
                                break;
                            }
                            xCord += xIncr[i];
                            yCord += yIncr[i];
                        }
                    }
                }
            }
        }
    }

    public void HighlightQueen(int FigureIndex){
        HighlightRook(FigureIndex);
        HighlightBishop(FigureIndex);
    }
    public void HighlightKing(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1 && whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            boolean otherKingIsClose;
            int cords, cords2;

            for(int i=0; i<8; i++) {
                otherKingIsClose = false;
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    for(int j=0; j<8; j++){
                        cords2 = CalculateCords(cords, xCord[j], yCord[j]);
                        if(cords2 >= 0 && cords2 < 64){
                            if(buttons[cords2].getName().equals("k")){
                                otherKingIsClose = true;
                                break;
                            }
                        }
                    }
                    if(!otherKingIsClose) {
                        // Check if movement is to the left
                        if (xCord[i] < 0) {
                            // Check if there is movement space to the left
                            if (FigureIndex % 8 > 0) {
                                if (FigureIsWhite(cords) != 1) {
                                    buttons[cords].setBackground(highlightColor);
                                }
                            }
                        }
                        // Check if movement is to the right
                        else if (xCord[i] > 0) {
                            // Check if there is movement space to the right
                            if (FigureIndex % 8 < 7) {
                                if (FigureIsWhite(cords) != 1) {
                                    buttons[cords].setBackground(highlightColor);
                                }
                            }
                        }
                        // If x coordinate is 0 highlight spot because vertical bounds are al ready checked
                        else {
                            if (FigureIsWhite(cords) != 1) {
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                }
            }
            // Castling
            if(!wKingHasMoved && !wLRookHasMoved){
                if(FigureIsWhite(57) == -1 && FigureIsWhite(58) == -1 && FigureIsWhite(59) == -1){
                    buttons[58].setBackground(castlingHighlight);
                }
            }
            if(!wKingHasMoved && !wRRookHasMoved){
                if(FigureIsWhite(61) == -1 && FigureIsWhite(62) == -1){
                    buttons[62].setBackground(castlingHighlight);
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0 && !whitesTurn) {
            buttons[FigureIndex].setBackground(stationaryHighlightColor);
            figureIsSelected = true;
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            boolean otherKingIsClose;
            int cords, cords2;

            for(int i=0; i<8; i++) {
                otherKingIsClose = false;
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    for(int j=0; j<8; j++){
                        cords2 = CalculateCords(cords, xCord[j], yCord[j]);
                        if(cords2 >= 0 && cords2 < 64){
                            if(buttons[cords2].getName().equals("K")){
                                otherKingIsClose = true;
                                break;
                            }
                        }
                    }
                    if(!otherKingIsClose) {
                        // Check if movement is to the left
                        if (xCord[i] < 0) {
                            // Check if there is movement space to the left
                            if (FigureIndex % 8 > 0) {
                                if (FigureIsWhite(cords) != 0) {
                                    buttons[cords].setBackground(highlightColor);
                                }
                            }
                        }
                        // Check if movement is to the right
                        else if (xCord[i] > 0) {
                            // Check if there is movement space to the right
                            if (FigureIndex % 8 < 7) {
                                if (FigureIsWhite(cords) != 0) {
                                    buttons[cords].setBackground(highlightColor);
                                }
                            }
                        }
                        // If x coordinate is 0 highlight spot because vertical bounds are al ready checked
                        else {
                            if (FigureIsWhite(cords) != 0) {
                                buttons[cords].setBackground(highlightColor);
                            }
                        }
                    }
                }
            }
            // Castling
            if(!bKingHasMoved && !bLRookHasMoved){
                if(FigureIsWhite(1) == -1 && FigureIsWhite(2) == -1 && FigureIsWhite(3) == -1){
                    buttons[2].setBackground(castlingHighlight);
                }
            }
            if(!bKingHasMoved && !bRRookHasMoved){
                if(FigureIsWhite(5) == -1 && FigureIsWhite(6) == -1){
                    buttons[6].setBackground(castlingHighlight);
                }
            }
        }
    }

    // Moves the figure to the pressed button if the button is already highlighted
    public void MoveFigure(ActionEvent e){
        int rememberButtonIndex = -1;
        int castlingIndex = -1;
        for(int i=0; i<64; i++){
            if(buttons[i].getBackground() == stationaryHighlightColor){
                rememberButtonIndex = i;
                i=64;
            }
        }
        for(int i=0; i<64; i++){
            if(e.getSource() == buttons[i]){
                // Normal figure movement
                if(buttons[i].getBackground() == highlightColor){
                    CheckCastlingMovement(rememberButtonIndex);
                    buttons[i].setName(buttons[rememberButtonIndex].getName());
                    buttons[i].setIcon(buttons[rememberButtonIndex].getIcon());
                    buttons[rememberButtonIndex].setName("0");
                    buttons[rememberButtonIndex].setIcon(null);
                    Recolor();
                    WhosTurn();
                    figureIsSelected = false;
                    CheckPawnToQueen();
                }
                // Does castling
                else if(buttons[i].getBackground() == castlingHighlight){
                    CheckCastlingMovement(rememberButtonIndex);
                    buttons[i].setName(buttons[rememberButtonIndex].getName());
                    buttons[i].setIcon(buttons[rememberButtonIndex].getIcon());
                    buttons[rememberButtonIndex].setName("0");
                    buttons[rememberButtonIndex].setIcon(null);
                    if(buttons[i+1].getName().equals("r") || buttons[i+1].getName().equals("R")){
                        rememberButtonIndex = i+1;
                        castlingIndex = i-1;
                    }
                    else if(buttons[i-2].getName().equals("r") || buttons[i-2].getName().equals("R")){
                        rememberButtonIndex = i-2;
                        castlingIndex = i+1;
                    }
                    else{
                        Recolor();
                        figureIsSelected = false;
                        break;
                    }
                    buttons[castlingIndex].setName(buttons[rememberButtonIndex].getName());
                    buttons[castlingIndex].setIcon(buttons[rememberButtonIndex].getIcon());
                    buttons[rememberButtonIndex].setName("0");
                    buttons[rememberButtonIndex].setIcon(null);
                    Recolor();
                    WhosTurn();
                    figureIsSelected = false;
                }
                // Reset if the figure is the same as selected one
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

    // Checks if the figure is white, black or empty
    public int FigureIsWhite(int d){
        String figureName = buttons[d].getName();
        String[] whites = {"P", "N", "B", "R", "Q", "K"};
        if(buttons[d].getName().equals("0")){
            return -1;
        }
        for(String x : whites){
            if(figureName.equals(x)){
                return 1;
            }
        }
        return 0;
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

    // Calculate coordinates from starting position, x and y
    public int CalculateCords(int pos, int x, int y){
        return pos-((y*8)-x);
    }

    // Check if figure is on top
    public boolean IsOnTopBound(int index){
        return index < 8;
    }

    // Check if figure is on bottom
    public boolean IsOnBottomBound(int index){
        return index > 55;
    }

    // Check if figure is on left
    public boolean IsOnLeftBound(int index){
        return index % 8 == 0;
    }

    // Check if figure is on right
    public boolean IsOnRightBound(int index){
        return index % 8 == 7;
    }

    // Checks if pawns are on top and bottom bounds
    public void CheckPawnToQueen(){
        for(int i=0; i<8; i++){
            if(buttons[i].getName().equals("P")){
                buttons[i].setName("Q");
                buttons[i].setIcon(figures[4]);
                break;
            }
        }
        for(int i=56; i<64; i++){
            if(buttons[i].getName().equals("p")){
                buttons[i].setName("q");
                buttons[i].setIcon(figures[10]);
                break;
            }
        }
    }

    // Check if rooks or king have moved
    public void CheckCastlingMovement(int FigureIndex){
        if(FigureIndex == 56 && buttons[FigureIndex].getName().equals("R")){
            wLRookHasMoved = true;
        }
        else if(FigureIndex == 60 && buttons[FigureIndex].getName().equals("K")){
            wKingHasMoved = true;
        }
        else if(FigureIndex == 63 && buttons[FigureIndex].getName().equals("R")){
            wRRookHasMoved = true;
        }
        else if(FigureIndex == 0 && buttons[FigureIndex].getName().equals("r")){
            bLRookHasMoved = true;
        }
        else if(FigureIndex == 4 && buttons[FigureIndex].getName().equals("k")){
            bKingHasMoved = true;
        }
        else if(FigureIndex == 7 && buttons[FigureIndex].getName().equals("r")){
            bRRookHasMoved = true;
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