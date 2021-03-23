import javax.swing.*;

public class Attack {

    JButton[] buttons = new JButton[64];
    String[] AttackMarks = new String[64];

    int whiteKingMovement;
    int blackKingMovement;

    Attack(){
        ResetMarks();
        for(int i=0; i<64; i++){
            buttons[i] = new JButton();
            buttons[i].setName("0");
        }
    }

    // Uses a highlighting function depending on what type of figure is selected
    public void RenameFigure(int i){
        switch (buttons[i].getName()) {
            case "P":
            case "p":
                RenamePawn(i);
                break;
            case "N":
            case "n":
                RenameKnight(i);

                break;
            case "B":
            case "b":
                RenameBishop(i);

                break;
            case "R":
            case "r":
                RenameRook(i);

                break;
            case "Q":
            case "q":
                RenameQueen(i);

                break;
            case "K":
            case "k":
                RenameKing(i);

                break;
        }
    }

    // These are for specific figure highlighting, doesn't move the figure
    public void RenamePawn(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1) {
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
        else if(FigureIsWhite(FigureIndex) == 0){
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

    public void RenameKnight(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1){
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
        else if(FigureIsWhite(FigureIndex) == 0){
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

    public void RenameBishop(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1){
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
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight empty spots
                            else if(FigureIsWhite(cords) == -1){
                                AttackMarks[cords] = ("wa");
                            }
                            // If the spot has a black figure highlight and stop
                            else if(FigureIsWhite(cords) == 0){
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
        else if(FigureIsWhite(FigureIndex) == 0){
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
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                AttackMarks[cords] = ("ba");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 1){
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

    public void RenameRook(int FigureIndex){
        // White
        if(FigureIsWhite(FigureIndex) == 1){
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
                                AttackMarks[cords] = ("wa");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                AttackMarks[cords] = ("wa");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 0){
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
        else if(FigureIsWhite(FigureIndex) == 0) {
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
                                AttackMarks[cords] = ("ba");
                                break;
                            }
                            // Highlight empty spots
                            if(FigureIsWhite(cords) == -1){
                                AttackMarks[cords] = ("ba");
                            }
                            // If the spot has a white figure highlight and stop
                            else if(FigureIsWhite(cords) == 1){
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

    public void RenameQueen(int FigureIndex){
        RenameRook(FigureIndex);
        RenameBishop(FigureIndex);
    }

    public void RenameKing(int FigureIndex){
        whiteKingMovement = 0;
        blackKingMovement = 0;
        // White
        if(FigureIsWhite(FigureIndex) == 1) {
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
                            if (FigureIsWhite(cords) != 1) {
                                AttackMarks[cords] = ("wa");
                                whiteKingMovement++;
                            }
                        }
                    }
                    // Check if movement is to the right
                    else if (xCord[i] > 0) {
                        // Check if there is movement space to the right
                        if (FigureIndex % 8 < 7) {
                            if (FigureIsWhite(cords) != 1) {
                                AttackMarks[cords] = ("wa");
                                whiteKingMovement++;
                            }
                        }
                    }
                    // If x coordinate is 0 highlight spot because vertical bounds are already checked
                    else {
                        if (FigureIsWhite(cords) != 1) {
                            AttackMarks[cords] = ("wa");
                            whiteKingMovement++;
                        }
                    }
                }
            }
        }
        // Black
        else if(FigureIsWhite(FigureIndex) == 0) {
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
                            if (FigureIsWhite(cords) != 0) {
                                AttackMarks[cords] = ("ba");
                                blackKingMovement++;
                            }
                        }
                    }
                    // Check if movement is to the right
                    else if (xCord[i] > 0) {
                        // Check if there is movement space to the right
                        if (FigureIndex % 8 < 7) {
                            if (FigureIsWhite(cords) != 0) {
                                AttackMarks[cords] = ("ba");
                                blackKingMovement++;
                            }
                        }
                    }
                    // If x coordinate is 0 highlight spot because vertical bounds are already checked
                    else {
                        if (FigureIsWhite(cords) != 0) {
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

    public void MarkAttack(boolean markWhite){
        if(markWhite){
            ResetMarks();
            for(int i=0; i<64; i++){
                if(FigureIsWhite(i) == 1){
                    RenameFigure(i);
                }
            }
        }
        else{
            ResetMarks();
            for(int i=0; i<64; i++){
                if(FigureIsWhite(i) == 0){
                    RenameFigure(i);
                }
            }
        }
    }

    public void ResetMarks(){
        for(int i=0; i<64; i++){
            AttackMarks[i] = "00";
        }
    }

    public void DevTool(boolean devTool){
        // Dev Tool
        if (devTool) {
            System.out.println("\n---------------\n");
            for (int i = 0; i < 64; i++) {
                if (i != 0 && i % 8 == 0) {
                    System.out.println();
                }
                System.out.print(AttackMarks[i] + " ");
            }
        }
    }
}
