import java.util.Scanner;

public class SudokuCLI {

    public static void main(String[] args) {
        // Game Loop Controls
        boolean running = true;
        boolean first = true;
        String input;
        String usr_msg = "";

        // interface crap
        Scanner sys_in = new Scanner(System.in);
        clearScreen();

        Board.generateRandomBoard();
        Board.generateSolution();

        while (running) {
            if (first) {
                System.out.println("========================");
                System.out.println("|| Welcome To Sudoku! ||");
                System.out.println("========================");
                System.out.println("Enter \"q\" at any time to quit");
                System.out.println("Enter \"h\" or \"help\" for a summary of commands");
                first = false;
            }

            Board.printBoard();
            System.out.println(usr_msg);
            usr_msg = "";

            // generate report and clear mistakes IF board full

            System.out.print("Enter Command: ");
            input = sys_in.nextLine().trim().toUpperCase();
            if (input.equals("Q"))
                running = false;
            else if (input.equals("H") || input.equals("HELP"))
                showCommands(sys_in);
            else {
                String[] arg = input.split("\\s+"); // Split by whitespace to separate coords and value
                Co_ord target;
                int val = 0;
                try {
                    switch (arg[0]) {
                        case ("MARK"):
                            try {

                            } catch (IndexOutOfBoundsException e) {
                                // TODO: handle exception
                            } catch (NumberFormatException e) {
                                // TODO: handle exception
                            }
                        case ("CLEAR"):
                            try {

                            } catch (IndexOutOfBoundsException e) {
                                // TODO: handle exception
                            } catch (NumberFormatException e) {
                                // TODO: handle exception
                            }
                        case ("CANDIDATE"):
                            try {

                            } catch (IndexOutOfBoundsException e) {
                                // TODO: handle exception
                            } catch (NumberFormatException e) {
                                // TODO: handle exception
                            }
                        case ("CHECK"):

                        default:
                            usr_msg = "Invalid command! Enter \"help\" to see a list of commands";
                    }
                } catch (IndexOutOfBoundsException e) {
                    usr_msg = "No command received! Enter \"help\" to see a list of commands.";
                }
            }

            clearScreen();
        }

        sys_in.close();
        System.out.println("Thanks for Playing!");
    }

    public static void showCommands(Scanner sys_in) {
        clearScreen();

        System.out.println("==============");
        System.out.println("|| Commands ||");
        System.out.println("==============");
        System.out.println();

        System.out.println("mark x y n       fill the space at co-ordinate (x,y) with value n");
        System.out.println("clear x y        clear the value at co-ordinate (x,y)");
        System.out.println("candidate x y    list all possible values for space x, y");
        System.out.println("check            generate a full mistake report of your current solution");
        System.out.println();
        System.out.print("Press any key to continue: ");
        sys_in.nextLine();

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}