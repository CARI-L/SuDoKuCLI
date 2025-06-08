import java.util.Scanner;

public class SudokuCLI {

    public static void main(String[] args) {
        // Game Loop Controls
        boolean running = true;
        String guess;

        // interface crap
        Scanner input = new Scanner(System.in);

        System.out.println("========================");
        System.out.println("|| Welcome To Sudoku! ||");
        System.out.println("========================");
        System.out.println("Enter \"q\" at any time to quit");

        Board.generateRandomBoard();
        Board.generateSolution();

        while (running) {
            Board.printBoard();
            System.out.print("\nEnter Co-ordinates: ");
            guess = input.nextLine().toUpperCase();
            clearScreen();
            if (guess.equals("Q")) {
                System.out.println("Thanks for Playing!");
                running = false;
            }
            
        }
        input.close();
    }
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}