import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;

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
            var tokens = lexer(input.toCharArray());
            if (tokens.contains("error")){
                System.out.println("error: invalid number!");
                continue;
            }

            for (String str : tokens){
                out.println("debug: "+str);
            }

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

    static ArrayList<String> lexer(char[] input) {
        char[] math_symbols = {'+','-','*','/','%','(',')'};
        ArrayList<String> tokens = new ArrayList<>();
        int x = 0;
        while (x < input.length) {
            boolean is_math_symbol = false;
            for (char ms : math_symbols) {
                if (input[x] == ms) {
                    is_math_symbol = true;
                    tokens.add(String.valueOf(input[x]));
                    break;
                }
            }
            if (is_math_symbol){
                x += 1;
                continue;
            }

            String num =  "";
            int y = x;
            while (y < input.length && is_num_symbol(input[y])) {
                num += input[y];
                y += 1;
            }
            x = y - 1;
            if (num.charAt(0) == '.' || num.charAt(num.length()-1) == '.' || num.chars().filter(ch -> ch == '.').count() > 1){
                tokens.add("error");
            }else {
                tokens.add(num);
            }
            x += 1;

        }
        return tokens;
    }
    static boolean is_num_symbol(char c) {
        char[] num_symbols = {'0','1','2','3','4','5','6','7','8','9','.'};
        boolean num_symbol = false;
        for (char n_s : num_symbols) {
            if (c == n_s){
                num_symbol = true;
                break;
            }
        }
        return num_symbol;
    }
}
