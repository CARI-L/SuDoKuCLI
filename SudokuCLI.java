import java.util.Scanner;

public class SudokuCLI {

    public static void main(String[] args) {
        // Game Loop Controls
        boolean running = true;
        String input;
        String usr_msg = "";

        // interface crap
        Scanner sys_in = new Scanner(System.in);
        clearScreen();

        System.out.println("========================");
        System.out.println("|| Welcome To Sudoku! ||");
        System.out.println("========================");
        System.out.println("Enter \"q\" at any time to quit");

        Board.generateRandomBoard();
        Board.generateSolution();

        while (running) {
            Board.printBoard();
            System.out.println(usr_msg);
            usr_msg = "";

            System.out.print("Enter Co-ordinates: ");
            input = sys_in.nextLine().toUpperCase();
            if (input.equals("Q")) {
                running = false; 
            } else {
                // parse input
                // after, prompt for value input
            }
            clearScreen();
        }


        sys_in.close();
        System.out.println("Thanks for Playing!");
    }

    public static int[] parseCo(String in) {
        String[] input = in.split(",");
        int[] result = new int[2];
        // check for bracketing
        // else, parse as normal.
        return result;
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}