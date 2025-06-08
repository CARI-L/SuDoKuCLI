import java.util.Arrays;

public class Board {
    static int[][] values;
    static int[][] solution;

    static void generateRandomBoard(){
        values = new int[9][9];     // initialize empty board

        while(!boardFull()){
            int x = randInt() - 1;
            int y = randInt() - 1;
            if (values[y][x] == 0){
                // pick value that is not surrounding current position
                values[y][x] = findValidRand(getValidNumbers(values, y, x));      
                // if no solution exists with chosen value, reset
                if(Arrays.deepEquals(solve(), new int[9][9])){
                    values[y][x] = 0;   
                }
            }
        }

        // generate list of all co-ordinates, and shuffle them
        int[][] positions = new int[81][2];
        int n = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                positions[n][0] = i;
                positions[n][1] = j;
                n++;
            }
        }
        shufflePos(positions);

        for(int[] position: positions){
            int y = position[0];
            int x = position[1];
            int val = values[y][x];

            values[y][x] = 0;   // set to empty
            // if more than 1 unique solution exists, restore value
            if(!(Arrays.deepEquals(solve(), solve()))){
                values[y][x] = val;
            }
        }
    }

    static void generateSolution(){
        solution = solve();
    }

    static int[][] solve() {
        int[][] tempBoard = new int[9][9];
        
        // Create a copy of the current board to work with
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tempBoard[i][j] = values[i][j];
            }
        }
        
        if (solveBacktrack(tempBoard)) {
            return tempBoard;
        } else {
            return new int[9][9]; // Return empty board if no solution exists
        }
    }

    // Helper method for the backtracking algorithm
    private static boolean solveBacktrack(int[][] board) {
        // Find an empty cell
        int[] emptyCell = findEmptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];
        
        // If no empty cell is found, the board is solved
        if (row == -1 && col == -1) {
            return true;
        }
        
        // Get valid numbers for this cell
        int[] validNumbers = getValidNumbers(board, row, col);
        
        // Shuffle the valid numbers for randomness
        shuffleArray(validNumbers);
        
        // Try each valid number
        for (int num : validNumbers) {
            if (num == 0) continue; // Skip if the number is 0 (not valid)
            
            // Place the number
            board[row][col] = num;
            
            // Recursively try to solve the rest of the board
            if (solveBacktrack(board)) {
                return true;
            }
            
            // If we get here, this number didn't work, reset and try next
            board[row][col] = 0;
        }
        
        // No valid number worked, need to backtrack
        return false;
    }

    // Find the first empty cell (value 0)
    private static int[] findEmptyCell(int[][] board) {
        int[] result = {-1, -1};
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result; // No empty cell found
    }

    // Get all valid numbers for a cell
    private static int[] getValidNumbers(int[][] board, int row, int col) {
        boolean[] isValid = new boolean[10]; // 0-9, but we'll ignore 0
        for (int i = 1; i <= 9; i++) {
            isValid[i] = true;
        }
        
        // Check row
        for (int j = 0; j < 9; j++) {
            if (board[row][j] != 0) {
                isValid[board[row][j]] = false;
            }
        }
        
        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] != 0) {
                isValid[board[i][col]] = false;
            }
        }
        
        // Check 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] != 0) {
                    isValid[board[i][j]] = false;
                }
            }
        }
        
        // Convert to array of valid numbers
        int count = 0;
        for (int i = 1; i <= 9; i++) {
            if (isValid[i]) count++;
        }
        
        int[] validNumbers = new int[count];
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            if (isValid[i]) {
                validNumbers[index++] = i;
            }
        }
        
        return validNumbers;
    }

    // Shuffle an array using Fisher-Yates algorithm
    private static void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            // Swap
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Shuffle an array of positions using Fisher-Yates algorithm
    private static void shufflePos(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            // Swap
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    
    // returns true only when there is no 0 in values
    static boolean boardFull(){
        for (int[] a : values){
            for(int i : a){
                if (i == 0) return false;
            }
        }
        return true;
    }

    // returns true if value in is found in array a
    static boolean includes(int in, int[] a){
        for(int i : a){
            if(i == in) return true;
        }
        return false;
    }

    // generates a random value from 1 to 9
    static int randInt(){
        return ((int) Math.round(Math.random() * 8)) + 1;
    }

    // generates a random value in list
    static int findValidRand(int[] in){
        int ret = 0;
        while(ret == 0){
            int tst = randInt();
            if(includes(tst, in)) ret = tst;
        }
        return ret;
    }

    // prints the sudoku board to the console
    static void printBoard(){
        for(int i = 0; i < 9; i++){
            if(i == 3 || i == 6) System.out.println("=========================");
            for(int j = 0; j < 9; j++){
                if(j == 0) System.out.print("|");
                if(j == 3 || j == 6) System.out.print("||");
                if(values[i][j] == 0) System.out.print("| ");
                else System.out.printf("|%d", values[i][j]);
                if(j == 8) System.out.println("||");
            }
        }
    }

}