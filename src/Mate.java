import javax.swing.*;

public class Mate {

    Check c = new Check();

    int whiteAllowedMov;
    int blackAllowedMov;


    public int AllowedMovement(boolean white, JButton[] buttons){
        whiteAllowedMov = 0;
        blackAllowedMov = 0;
        CheckPossibleMovements(buttons);
        if(white){
            System.out.println("White: " + whiteAllowedMov);
            return whiteAllowedMov;
        }
        else{
            System.out.println("Black: " + blackAllowedMov);
            return blackAllowedMov;
        }
    }

    // Uses a highlighting function depending on what type of figure is selected
    public void CheckPossibleMovements(JButton[] buttons){
        for(int i=0; i<64; i++){
            switch (buttons[i].getName()) {
                case "P":
                case "p":
                    CheckPawnMovement(i, buttons);
                    break;
                case "N":
                case "n":
                    CheckKnightMovement(i, buttons);
                    break;
                case "B":
                case "b":
                    CheckBishopMovement(i, buttons);
                    break;
                case "R":
                case "r":
                    CheckRookMovement(i, buttons);
                    break;
                case "Q":
                case "q":
                    CheckQueenMovement(i, buttons);
                    break;
                case "K":
                case "k":
                    CheckKingMovement(i, buttons);
                    break;
            }
        }
    }

    // These are for specific figure highlighting, doesn't move the figure
    public void CheckPawnMovement(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1) {
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {1, 2, 1, 1};

            // Check if pawn is on last row
            if(FigureIndex > 7){
                // Check if one spot in front is empty
                if(FigureIsWhite(CalculateCords(FigureIndex, xCord[0], yCord[0]), buttons) == -1){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[0], yCord[0]), buttons, true)){
                        whiteAllowedMov++;
                    }
                    // Check if spot two steps in front are empty
                    if(FigureIndex > 47 && FigureIsWhite(CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons) == -1){
                        if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons, true)){
                            whiteAllowedMov++;
                        }
                    }
                }

                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[2], yCord[2]), buttons) == 0){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[2], yCord[2]), buttons, true)){
                        whiteAllowedMov++;
                    }
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[3], yCord[3]), buttons) == 0){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[3], yCord[3]), buttons, true)){
                        whiteAllowedMov++;
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex, buttons) == 0){
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {-1, -2, -1, -1};

            // Check if pawn is on last row
            if(FigureIndex < 56){
                // Check if one spot in front is empty
                if(FigureIsWhite(CalculateCords(FigureIndex, xCord[0], yCord[0]), buttons) == -1){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[0], yCord[0]), buttons, false)){
                        blackAllowedMov++;
                    }
                    // Check if spot two steps in front are empty
                    if(FigureIndex < 16 && FigureIsWhite(CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons) == -1){
                        if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons, false)){
                            blackAllowedMov++;
                        }
                    }
                }
                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[2], yCord[2]), buttons) == 1){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons, false)){
                        blackAllowedMov++;
                    }
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex) && FigureIsWhite(CalculateCords(FigureIndex, xCord[3], yCord[3]), buttons) == 1){
                    if(!c.FutureCheck(FigureIndex, CalculateCords(FigureIndex, xCord[1], yCord[1]), buttons, false)){
                        blackAllowedMov++;
                    }
                }
            }
        }
    }

    public void CheckKnightMovement(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1){
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
                            if(FigureIsWhite(cords, buttons) != 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            if(FigureIsWhite(cords, buttons) != 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            if(FigureIsWhite(cords, buttons) != 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6){
                            if(FigureIsWhite(cords, buttons) != 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex, buttons) == 0){
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
                            if(FigureIsWhite(cords, buttons) != 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            if(FigureIsWhite(cords, buttons) != 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            if(FigureIsWhite(cords, buttons) != 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6){
                            if(FigureIsWhite(cords, buttons) != 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void CheckBishopMovement(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1){
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
                            if(FigureIsWhite(cords, buttons) == 1){
                                break;
                            }
                            // Highlight empty spots
                            else if(FigureIsWhite(cords, buttons) == -1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                            // If the spot has a black figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
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
        else if(FigureIsWhite(FigureIndex, buttons) == 0){
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
                            if(FigureIsWhite(cords, buttons) == 0){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
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

    public void CheckRookMovement(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1){
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
                            if(FigureIsWhite(cords, buttons) == 1){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, true)){
                                    whiteAllowedMov++;
                                }
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
        else if(FigureIsWhite(FigureIndex, buttons) == 0) {
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
                            if(FigureIsWhite(cords, buttons) == 0){
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 1){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                if(!c.FutureCheck(FigureIndex, cords, buttons, false)){
                                    blackAllowedMov++;
                                }
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

    public void CheckQueenMovement(int FigureIndex, JButton[] buttons){
        CheckRookMovement(FigureIndex, buttons);
        CheckBishopMovement(FigureIndex, buttons);
    }
    public void CheckKingMovement(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1) {
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            boolean otherKingIsClose;
            boolean spotAttacked;
            int cords, cords2;

            for(int i=0; i<8; i++) {
                otherKingIsClose = false;
                spotAttacked = false;
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    // Check if spot is being attacked
                    c.MarkAttacks(false, buttons);
                    if(c.AttackMarks[CalculateCords(FigureIndex, xCord[i], yCord[i])].equals("ba")){
                        spotAttacked = true;
                    }
                    // Check if other king is close
                    for(int j=0; j<8; j++){
                        cords2 = CalculateCords(cords, xCord[j], yCord[j]);
                        if(cords2 >= 0 && cords2 < 64){
                            if(buttons[cords2].getName().equals("k")){
                                otherKingIsClose = true;
                                break;
                            }
                        }
                    }
                    if(!otherKingIsClose && !spotAttacked) {
                        // Check if movement is to the left
                        if (xCord[i] < 0) {
                            // Check if there is movement space to the left
                            if (FigureIndex % 8 > 0) {
                                if (FigureIsWhite(cords, buttons) != 1) {
                                    whiteAllowedMov++;
                                }
                            }
                        }
                        // Check if movement is to the right
                        else if (xCord[i] > 0) {
                            // Check if there is movement space to the right
                            if (FigureIndex % 8 < 7) {
                                if (FigureIsWhite(cords, buttons) != 1) {
                                    whiteAllowedMov++;
                                }
                            }
                        }
                        // If x coordinate is 0 highlight spot because vertical bounds are already checked
                        else {
                            if (FigureIsWhite(cords, buttons) != 1) {
                                whiteAllowedMov++;
                            }
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex, buttons) == 0) {
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            boolean otherKingIsClose;
            boolean spotAttacked;
            int cords, cords2;

            for(int i=0; i<8; i++) {
                otherKingIsClose = false;
                spotAttacked = false;
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    // Check if spot is being attacked
                    c.MarkAttacks(true, buttons);
                    if(c.AttackMarks[CalculateCords(FigureIndex, xCord[i], yCord[i])].equals("wa")){
                        spotAttacked = true;
                    }
                    // Check if other king is close
                    for(int j=0; j<8; j++){
                        cords2 = CalculateCords(cords, xCord[j], yCord[j]);
                        if(cords2 >= 0 && cords2 < 64){
                            if(buttons[cords2].getName().equals("K")){
                                otherKingIsClose = true;
                                break;
                            }
                        }
                    }
                    if(!otherKingIsClose && !spotAttacked) {
                        // Check if movement is to the left
                        if (xCord[i] < 0) {
                            // Check if there is movement space to the left
                            if (FigureIndex % 8 > 0) {
                                if (FigureIsWhite(cords, buttons) != 0) {
                                    blackAllowedMov++;
                                }
                            }
                        }
                        // Check if movement is to the right
                        else if (xCord[i] > 0) {
                            // Check if there is movement space to the right
                            if (FigureIndex % 8 < 7) {
                                if (FigureIsWhite(cords, buttons) != 0) {
                                    blackAllowedMov++;
                                }
                            }
                        }
                        // If x coordinate is 0 highlight spot because vertical bounds are already checked
                        else {
                            if (FigureIsWhite(cords, buttons) != 0) {
                                blackAllowedMov++;
                            }
                        }
                    }
                }
            }
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

    // Checks if the figure is white, black or empty
    public int FigureIsWhite(int d, JButton[] buttons){
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

}
