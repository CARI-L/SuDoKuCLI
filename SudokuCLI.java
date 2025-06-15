import java.util.HashSet;
import java.util.Scanner;

public class SudokuCLI {

    public static void main(String[] args) {
        // Game Loop Controls
        boolean running = true;
        boolean first = true;
        String input;
        String usr_msg = "";
        HashSet<Co_ord> user_mod = new HashSet<Co_ord>();

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

            System.out.print("Enter Command: ");
            input = sys_in.nextLine().trim().toUpperCase();
            if (input.equals("Q"))
                running = false;
            else if (input.equals("H") || input.equals("HELP"))
                showCommands(sys_in);
            else {
                String[] arg = input.split("\s+"); // Split by whitespace to separate coords and value
                Co_ord target;
                try {
                    if (arg[0].equals("FILL")) {
                        try {
                            target = new Co_ord(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
                            int val = Integer.parseInt(arg[3]);
                            if (val > 9 || val < 1)
                                throw new NumberFormatException();
                            int i = target.y_to_list();
                            int j = target.x_to_list();

                            if (Board.base[i][j] != 0)
                                throw new IllegalAccessException();
                            if (Board.values[i][j] == val)
                                throw new Exception();

                            Board.values[i][j] = val;
                            user_mod.add(target);
                            usr_msg = String.format("Move Accepted: %d @ (%d, %d)", val, target.x, target.y);

                        } catch (IndexOutOfBoundsException e) {
                            usr_msg = "Incorrect amount of arguments! Format is: fill x y n";
                        }
                    } else if (arg[0].equals("CLEAR")) {
                        try {
                            target = new Co_ord(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
                            int i = target.y_to_list();
                            int j = target.x_to_list();

                            if (Board.base[i][j] != 0)
                                throw new IllegalAccessException();
                            if (Board.values[i][j] == 0)
                                throw new Exception();

                            Board.values[i][j] = 0;
                            user_mod.add(target);
                            usr_msg = String.format("Cell Cleared: (%d, %d)", target.x, target.y);

                        } catch (IndexOutOfBoundsException e) {
                            usr_msg = "Incorrect amount of arguments! Format is: clear x y";
                        }
                    } else if (arg[0].equals("CANDIDATE")) {
                        try {
                            usr_msg = generateCandidates(
                                    new Co_ord(Integer.parseInt(arg[1]), Integer.parseInt(arg[2])));
                        } catch (IndexOutOfBoundsException e) {
                            usr_msg = "Incorrect amount of arguments! Format is: candidate x y";
                        }
                    } else if (arg[0].equals("CHECK")) {
                        clearScreen();
                        System.out.println(generateReport(user_mod));
                        System.out.print("Press any key to continue: ");
                        sys_in.nextLine();
                    } else if (arg[0].equals(""))
                        usr_msg = "No command received! Enter \"help\" to see a list of commands.";
                    else
                        usr_msg = "Invalid command! Enter \"help\" to see a list of commands";

                } catch (NumberFormatException e) {
                    usr_msg = "Incorrect type of argument! Only numbers from 1 to 9 are accepted.";
                } catch (IllegalAccessException e) {
                    usr_msg = "Cannot edit immutable puzzle cells! Move not accepted.";
                } catch (Exception e) {
                    usr_msg = "Redundant action! Move not accepted";
                }
            }

            if (Board.boardFull()) {
                if (solved()) {
                    first = true;
                    running = false;
                } else {
                    clearScreen();
                    System.out.println("Board is full, but some errors still exist.");
                    System.out.println(generateReport(user_mod));
                    clearMistakes();
                    System.out.println("Your mistakes have been cleared.");
                    System.out.print("Press enter to continue: ");
                    sys_in.nextLine();

                }

            }

            clearScreen();
        }

        sys_in.close();
        if (first) {
            System.out.println("Congratulations! You solved the puzzle!");
            Board.printBoard();
        }
        System.out.println("Thanks for Playing!");
    }

    static String generateCandidates(Co_ord cell) {
        if (Board.values[cell.y_to_list()][cell.x_to_list()] != 0)
            return "Cell must be empty";
        int[] candidates = Board.getValidNumbers(Board.values, cell.y_to_list(), cell.x_to_list());
        String result = "";

        for (int c : candidates) {
            if (c != 0)
                result += String.format("%d, ", c);
        }

        if (result.equals(""))
            return String.format("no candidates found for space (%d, %d)", cell.x, cell.y);
        return String.format("Candidates (%d, %d): ", cell.x, cell.y) + result.substring(0, result.length() - 2);
    }

    static String generateReport(HashSet<Co_ord> user_mod) {
        if (user_mod.isEmpty())
            return "No spaces modified";

        String result = "";

        for (Co_ord cell : user_mod) {
            int i = cell.y_to_list();
            int j = cell.x_to_list();

            if (Board.values[i][j] != Board.solution[i][j])
                result += String.format("(%d, %d) - value: %d\n", cell.x, cell.y, Board.values[i][j]);

        }

        if (result.equals(""))
            return "All moves are correct!";
        result = "Mistake Spaces:\n\n" + result;
        return result;
    }

    static boolean solved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Board.values[i][j] != Board.solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static void clearMistakes() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Board.values[i][j] != Board.solution[i][j]) {
                    Board.values[i][j] = 0;
                }
            }
        }
    }

    static void showCommands(Scanner sys_in) {
        clearScreen();

        System.out.println("==============");
        System.out.println("|| Commands ||");
        System.out.println("==============");
        System.out.println();

        System.out.println("fill x y n       fill the space at co-ordinate (x,y) with value n");
        System.out.println("clear x y        clear the value at co-ordinate (x,y)");
        System.out.println("candidate x y    list all possible values for space x, y");
        System.out.println("check            generate a full mistake report of your current solution");
        System.out.println();
        System.out.print("Press any key to continue: ");
        sys_in.next();

    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}