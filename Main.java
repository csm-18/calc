import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("calc ('q' for quit!)");
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            } else if (input.equals("q")) {
                scanner.close();
                exit(0);
            } else if (symbol_error(input)) {
                System.out.println("error: invalid chars!");
                continue;
            }
            System.out.println(input);

        }
    }

    static boolean symbol_error(String input) {
        char[] valid_symbols = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '+', '-', '*', '/', '%', '(', ')'};
        boolean error = false;
        for (char c : input.toCharArray()){
            error = true;
            for (char s : valid_symbols) {
                if (c == s){
                    error = false;
                    break;
                }
            }
            if (error){
                return error;
            }
        }
        return error;
    }
}
