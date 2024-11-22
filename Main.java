import java.util.ArrayList;
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
            var tokens = lexer(input.toCharArray());
            if (tokens.contains("error")){
                System.out.println("error: invalid number!");
                continue;
            }

            for (String str : tokens){
                System.out.println("debug: "+str);
            }

        }
    }

    //Input Symbol Validity Check: start
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
    //Input Symbol Validity Check: end

    //Tokenization: start
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
    //Tokenization: end

    //Parsing: start
    static void parser(ArrayList<String> tokens) {
        int x = 0;
        while(x < tokens.size()){
            if(tokens.get(x).equals("(")){
                int close_paren = 0;
                int y = x + 1;
                while(y < tokens.size()){
                    if (tokens.get(y).equals(")")){
                        close_paren = y;
                        break;
                    }else {
                        //error: no end parenthesis
                        System.out.println("error: no end parenthesis!");
                        exit(1);
                    }
                    y += 1;
                }
                ArrayList<String> expression = (ArrayList<String>) tokens.subList(x+1,close_paren);
                if (expression.contains("(")){
                    x += 1;
                    continue;
                }else {
                    expression = multiply_divide_modulo(expression);
                    expression = addition_subtraction(expression);

                    if (x == 0 && tokens.size()-1 == close_paren){
                        tokens = expression;
                        break;
                    } else if (x > 0 && close_paren < tokens.size()-1) {
                        if (tokens.get(x-1).equals("+") || tokens.get(x-1).equals("-") || tokens.get(x-1).equals("*") || tokens.get(x-1).equals("/") || tokens.get(x-1).equals("%") && tokens.get(close_paren+1).equals("+") || tokens.get(close_paren+1).equals("-") || tokens.get(close_paren+1).equals("*") || tokens.get(close_paren+1).equals("/") || tokens.get(close_paren+1).equals("%")){
                            var temp = (ArrayList<String>) tokens.subList(0,x);
                            temp.add(expression.getFirst());
                            temp.addAll(tokens.subList(close_paren+1, tokens.size()));
                            tokens = temp;
                            x = 0;
                            continue;
                        }else if(!tokens.get(x-1).equals("+") && !tokens.get(x-1).equals("-") && !tokens.get(x-1).equals("*") && !tokens.get(x-1).equals("/") && !tokens.get(x-1).equals("%") && tokens.get(close_paren+1).equals("+") || tokens.get(close_paren+1).equals("-") || tokens.get(close_paren+1).equals("*") || tokens.get(close_paren+1).equals("/") || tokens.get(close_paren+1).equals("%")) {
                            expression.addFirst("*");
                            var temp = (ArrayList<String>) tokens.subList(0,x);
                            temp.addAll(expression);
                            temp.addAll(tokens.subList(close_paren+1, tokens.size()));
                            tokens = temp;
                            x = 0;
                            continue;
                        }else if (tokens.get(x-1).equals("+") || tokens.get(x-1).equals("-") || tokens.get(x-1).equals("*") || tokens.get(x-1).equals("/") || tokens.get(x-1).equals("%") && !tokens.get(close_paren+1).equals("+") && !tokens.get(close_paren+1).equals("-") && !tokens.get(close_paren+1).equals("*") && !tokens.get(close_paren+1).equals("/") && !tokens.get(close_paren+1).equals("%")){
                            expression.addLast("*");
                            var temp = (ArrayList<String>) tokens.subList(0,x);
                            temp.addAll(expression);
                            temp.addAll(tokens.subList(close_paren+1, tokens.size()));
                            tokens = temp;
                            x = 0;
                            continue;
                        }
                    } else if (x > 0 && close_paren == tokens.size()-1){
                        if(!tokens.get(x-1).equals("+") && !tokens.get(x-1).equals("-") && !tokens.get(x-1).equals("*") && !tokens.get(x-1).equals("/") && !tokens.get(x-1).equals("%")){
                            expression.addFirst("*");
                            var temp = (ArrayList<String>) tokens.subList(0,x);
                            temp.addAll(expression);
                            tokens = temp;
                            x = 0;
                            continue;
                        } else if (tokens.get(x-1).equals("+") || tokens.get(x-1).equals("-") || tokens.get(x-1).equals("*") || tokens.get(x-1).equals("/") || tokens.get(x-1).equals("%")) {
                            var temp = (ArrayList<String>) tokens.subList(0,x);
                            temp.addAll(expression);
                            tokens = temp;
                            x = 0;
                            continue;
                        }

                    } else if (x == 0 && close_paren < tokens.size()-1) {
                        if (!tokens.get(close_paren+1).equals("+") && !tokens.get(close_paren+1).equals("-") && !tokens.get(close_paren+1).equals("*") && !tokens.get(close_paren+1).equals("/") && !tokens.get(close_paren+1).equals("%")){
                            expression.addLast("*");
                            var temp = new ArrayList<String>();
                            temp.addAll(expression);
                            temp.addAll(tokens.subList(close_paren+1, tokens.size()));
                            tokens = temp;
                            x = 0;
                            continue;
                        } else if (tokens.get(close_paren+1).equals("+") || tokens.get(close_paren+1).equals("-") || tokens.get(close_paren+1).equals("*") || tokens.get(close_paren+1).equals("/") || tokens.get(close_paren+1).equals("%")) {
                            var temp = new ArrayList<String>();
                            temp.addAll(expression);
                            temp.addAll(tokens.subList(close_paren+1, tokens.size()));
                            tokens = temp;
                            x = 0;
                            continue;
                        }
                    }

                }
            }
            x += 1;
        }

    }
    static ArrayList<String> multiply_divide_modulo(ArrayList<String> simple_exp) {
        int x = 0;
        while (x < simple_exp.size()) {
            if (x != 0 && simple_exp.get(x).equals("*") && x + 1 < simple_exp.size()) {
                try {
                    var result = Double.parseDouble(simple_exp.get(x - 1)) * Double.parseDouble(simple_exp.get(x + 1));

                    var temp = new ArrayList<String>();
                    temp.addAll(simple_exp.subList(0, x - 1));
                    temp.addLast(String.valueOf(result));
                    if (x + 2 < simple_exp.size()) {
                        temp.addAll(simple_exp.subList(x + 2, simple_exp.size()));
                    }
                    simple_exp = temp;
                    x = x - 2;
                } catch (Error e){
                    System.out.println("error: invalid expression!");
                    exit(1);
                }
            } else if (x != 0 && simple_exp.get(x).equals("/") && x + 1 < simple_exp.size()) {
                try {
                    var result = Double.parseDouble(simple_exp.get(x - 1)) / Double.parseDouble(simple_exp.get(x + 1));


                    var temp = new ArrayList<String>();
                    temp.addAll(simple_exp.subList(0, x - 1));
                    temp.addLast(String.valueOf(result));
                    if (x + 2 < simple_exp.size()) {
                        temp.addAll(simple_exp.subList(x + 2, simple_exp.size()));
                    }
                    simple_exp = temp;
                    x = x - 2;
                }catch (Error e){
                    System.out.println("error: invalid expression!");
                    exit(1);
                }
            } else if (x != 0 && simple_exp.get(x).equals("%") && x + 1 < simple_exp.size()) {
                try {
                    var result = Double.parseDouble(simple_exp.get(x - 1)) % Double.parseDouble(simple_exp.get(x + 1));
                    var temp = new ArrayList<String>();
                    temp.addAll(simple_exp.subList(0, x - 1));
                    temp.addLast(String.valueOf(result));
                    if (x + 2 < simple_exp.size()) {
                        temp.addAll(simple_exp.subList(x + 2, simple_exp.size()));
                    }
                    simple_exp = temp;
                    x = x - 2;
                } catch (Error e){
                    System.out.println("error: invalid expression!");
                    exit(1);
                }
            }
            x += 1;
        }
        if (simple_exp.contains("*") || simple_exp.contains("/") || simple_exp.contains("%")) {
            //error: invalid expression
            System.out.println("error: invalid expression!");
            exit(1);
        }
        return simple_exp;
    }
    static ArrayList<String> addition_subtraction(ArrayList<String> simple_exp) {
        var x = 0;
        while (x < simple_exp.size()){
            if (x != 0 && simple_exp.get(x).equals("+") && x + 1 < simple_exp.size()) {
                try {
                    var result = Double.parseDouble(simple_exp.get(x - 1)) + Double.parseDouble(simple_exp.get(x + 1));

                    var temp = new ArrayList<String>();
                    temp.addAll(simple_exp.subList(0, x - 1));
                    temp.addLast(String.valueOf(result));
                    if (x + 2 < simple_exp.size()) {
                        temp.addAll(simple_exp.subList(x + 2, simple_exp.size()));
                    }
                    simple_exp = temp;
                    x = x - 2;
                } catch (Error e){
                    System.out.println("error: invalid expression!");
                    exit(1);
                }
            }else if (x != 0 && simple_exp.get(x).equals("-") && x + 1 < simple_exp.size()) {
                try {
                    var result = Double.parseDouble(simple_exp.get(x - 1)) - Double.parseDouble(simple_exp.get(x + 1));

                    var temp = new ArrayList<String>();
                    temp.addAll(simple_exp.subList(0, x - 1));
                    temp.addLast(String.valueOf(result));
                    if (x + 2 < simple_exp.size()) {
                        temp.addAll(simple_exp.subList(x + 2, simple_exp.size()));
                    }
                    simple_exp = temp;
                    x = x - 2;
                } catch (Error e){
                    System.out.println("error: invalid expression!");
                    exit(1);
                }
            }
            x += 1;
        }
        if (simple_exp.contains("+") || simple_exp.contains("-")) {
            //error: invalid expression
            System.out.println("error: invalid expression!");
            exit(1);
        }
        return simple_exp;
    }
    //Parsing: end
}
