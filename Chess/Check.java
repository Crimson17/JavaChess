import javax.swing.*;

public class Check {

    String[] AttackMarks = new String[64];

    int whiteKingMovement;
    int blackKingMovement;

    // Check for check after the move( works for both colors)
    public boolean FutureCheck(int a, int b, JButton[] buttons, boolean checkWhite){
        JButton[] tempButtons = new JButton[64];
        for(int i=0; i<64; i++){
            tempButtons[i] = new JButton();
            tempButtons[i].setName(buttons[i].getName());
        }

        tempButtons[b].setName(tempButtons[a].getName());
        tempButtons[a].setName("0");

        return CheckForCheck(tempButtons, checkWhite);
    }

    public boolean CheckForCheck(JButton[] buttons, boolean checkWhite){
        if(checkWhite){
            return WhiteCheck(buttons);
        }
        else{
            return BlackCheck(buttons);
        }
    }

    // Check if it is a white check currently
    public boolean WhiteCheck(JButton[] buttons){
        MarkAttacks(false, buttons);
        for(int i=0; i<64; i++) {
            if (buttons[i].getName().equals("K")) {
                return AttackMarks[i].equals("ba");
            }
        }
        return false;
    }

    // Check if it is a black check currently
    public boolean BlackCheck(JButton[] buttons) {
        MarkAttacks(true, buttons);
        for (int i = 0; i < 64; i++) {
            if (buttons[i].getName().equals("k")) {
                return AttackMarks[i].equals("wa");
            }
        }
        return false;
    }

    public void MarkAttacks(boolean markWhiteAtk, JButton[] buttons){
        if(markWhiteAtk){
            ResetMarks();
            for(int i=0; i<64; i++){
                if(FigureIsWhite(i, buttons) == 1){
                    CheckAttacks(i, buttons);
                }
            }
        }
        else{
            ResetMarks();
            for(int i=0; i<64; i++){
                if(FigureIsWhite(i, buttons) == 0){
                    CheckAttacks(i, buttons);
                }
            }
        }
    }

    public void ResetMarks(){
        for(int i=0; i<64; i++){
            AttackMarks[i] = "00";
        }
    }

    // Uses a highlighting function depending on what type of figure is selected
    public void CheckAttacks(int i, JButton[] buttons){
        switch (buttons[i].getName()) {
            case "P":
            case "p":
                PawnAttack(i, buttons);
                break;
            case "N":
            case "n":
                KnightAttack(i, buttons);
                break;
            case "B":
            case "b":
                BishopAttack(i, buttons);
                break;
            case "R":
            case "r":
                RookAttack(i, buttons);
                break;
            case "Q":
            case "q":
                QueenAttack(i, buttons);
                break;
            case "K":
            case "k":
                KingAttack(i, buttons);
                break;
        }
    }

    // These are for specific figure highlighting, doesn't move the figure
    public void PawnAttack(int FigureIndex, JButton[] buttons){
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1) {
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {1, 2, 1, 1};

            // Check if pawn is on last row
            if(FigureIndex > 7){
                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex)){
                    AttackMarks[CalculateCords(FigureIndex, xCord[2], yCord[2])] = ("wa");
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex)){
                    AttackMarks[CalculateCords(FigureIndex, xCord[3], yCord[3])] = ("wa");
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex, buttons) == 0){
            int[] xCord = {0, 0, 1, -1};
            int[] yCord = {-1, -2, -1, -1};

            // Check if pawn is on last row
            if(FigureIndex < 56){
                // Check if the spot to side has an enemy
                if(!IsOnRightBound(FigureIndex)){
                    AttackMarks[CalculateCords(FigureIndex, xCord[2], yCord[2])] = ("ba");
                }
                // Check if the other spot to side has an enemy
                if(!IsOnLeftBound(FigureIndex)){
                    AttackMarks[CalculateCords(FigureIndex, xCord[3], yCord[3])] = ("ba");
                }
            }
        }
    }

    public void KnightAttack(int FigureIndex, JButton[] buttons){
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
                            AttackMarks[cords] = ("wa");
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            AttackMarks[cords] = ("wa");
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            AttackMarks[cords] = ("wa");
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6){
                            AttackMarks[cords] = ("wa");
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
                            AttackMarks[cords] = ("ba");
                        }
                        // Check if there is movement space to the left
                        else if(Math.abs(xCord[i]) == 2 && FigureIndex % 8 > 1){
                            AttackMarks[cords] = ("ba");
                        }
                    }
                    // Movement is to the right
                    else{
                        // Check if there is movement space to the right
                        if(xCord[i] == 1 && FigureIndex % 8 < 7){
                            AttackMarks[cords] = ("ba");
                        }
                        // Check if there is movement space to the right
                        else if(xCord[i] == 2 && FigureIndex % 8 < 6) {
                            AttackMarks[cords] = ("ba");
                        }
                    }
                }
            }
        }
    }

    public void BishopAttack(int FigureIndex, JButton[] buttons){
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
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight empty spots
                            else if(FigureIsWhite(cords, buttons) == -1){
                                AttackMarks[cords] = ("wa");
                            }
                            // If the spot has a black figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                AttackMarks[cords] = ("wa");
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
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                AttackMarks[cords] = ("ba");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 1){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords) && xCord > 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords) && yCord > 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                AttackMarks[cords] = ("ba");
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

    public void RookAttack(int FigureIndex, JButton[] buttons){
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
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                AttackMarks[cords] = ("wa");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                AttackMarks[cords] = ("wa");
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
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords, buttons) == -1){
                                AttackMarks[cords] = ("ba");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords, buttons) == 1){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            if(IsOnLeftBound(cords) && xCord < 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnRightBound(cords)  && xCord > 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnTopBound(cords)  && yCord > 0){
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight and stop if it hits a bound
                            else if(IsOnBottomBound(cords) && yCord < 0){
                                AttackMarks[cords] = ("ba");
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

    public void QueenAttack(int FigureIndex, JButton[] buttons){
        RookAttack(FigureIndex, buttons);
        BishopAttack(FigureIndex, buttons);
    }

    public void KingAttack(int FigureIndex, JButton[] buttons){
        whiteKingMovement = 0;
        blackKingMovement = 0;
        // White
        if(FigureIsWhite(FigureIndex, buttons) == 1) {
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            int cords;

            for(int i=0; i<8; i++) {
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    // Check if movement is to the left
                    if (xCord[i] < 0) {
                        // Check if there is movement space to the left
                        if (FigureIndex % 8 > 0) {
                            if (FigureIsWhite(cords, buttons) != 1) {
                                AttackMarks[cords] = ("wa");
                                whiteKingMovement++;
                            }
                        }
                    }
                    // Check if movement is to the right
                    else if (xCord[i] > 0) {
                        // Check if there is movement space to the right
                        if (FigureIndex % 8 < 7) {
                            if (FigureIsWhite(cords, buttons) != 1) {
                                AttackMarks[cords] = ("wa");
                                whiteKingMovement++;
                            }
                        }
                    }
                    // If x coordinate is 0 highlight spot because vertical bounds are already checked
                    else {
                        if (FigureIsWhite(cords, buttons) != 1) {
                            AttackMarks[cords] = ("wa");
                            whiteKingMovement++;
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex, buttons) == 0) {
            int[] xCord = {1, -1, 0, 0, 1, 1, -1, -1};
            int[] yCord = {0, 0, 1, -1, 1, -1, -1, 1};
            int cords;

            for(int i=0; i<8; i++) {
                cords = CalculateCords(FigureIndex, xCord[i], yCord[i]);
                // Check of the top and bottom bounds
                if (cords >= 0 && cords < 64) {
                    // Check if movement is to the left
                    if (xCord[i] < 0) {
                        // Check if there is movement space to the left
                        if (FigureIndex % 8 > 0) {
                            if (FigureIsWhite(cords, buttons) != 0) {
                                AttackMarks[cords] = ("ba");
                                blackKingMovement++;
                            }
                        }
                    }
                    // Check if movement is to the right
                    else if (xCord[i] > 0) {
                        // Check if there is movement space to the right
                        if (FigureIndex % 8 < 7) {
                            if (FigureIsWhite(cords, buttons) != 0) {
                                AttackMarks[cords] = ("ba");
                                blackKingMovement++;
                            }
                        }
                    }
                    // If x coordinate is 0 highlight spot because vertical bounds are already checked
                    else {
                        if (FigureIsWhite(cords, buttons) != 0) {
                            AttackMarks[cords] = ("ba");
                            blackKingMovement++;
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
