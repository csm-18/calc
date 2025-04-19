
mod parser;

pub fn calc(exp: &str) {
    //remove spaces from input expression
    let exp = exp.replace("\n", "");
    let exp = exp.replace(" ", "");

    //check for invalid symbols in expression
    if !valid_symbols(&exp) {
        println!("invalid input!");
        return;
    }

    //get tokens from expression
    let tokens = match lexer(&exp) {
        Ok(tokens) => tokens,
        Err(error) => {
            println!("{error}");
            return;
        }
    };
    parser::parser(tokens);
}

fn valid_symbols(exp: &str) -> bool {
    let numeric_symbols = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'];
    let math_symbols = ['+', '-', '*', '/', '%', '(', ')'];

    for c in exp.chars() {
        let mut valid = false;

        for ns in numeric_symbols {
            if c == ns {
                valid = true;
                break;
            }
        }
        if valid {
            continue;
        }

        for ms in math_symbols {
            if c == ms {
                valid = true;
                break;
            }
        }
        if valid {
            continue;
        } else {
            return false;
        }
    }

    true
}

#[derive(Debug, PartialEq, Clone)]
pub enum TokenType {
    Num,
    LeftParen,
    RightParen,
    Plus,
    Minus,
    Multiply,
    Divide,
    Remainder,
}

#[derive(Debug, Clone)]
pub struct Token {
    token_type: TokenType,
    value: String,
}
fn lexer(exp: &str) -> Result<Vec<Token>, String> {
    let mut tokens: Vec<Token> = Vec::new();

    let mut x = 0;
    while x < exp.len() {
        //check if it is a number
        if is_numeric(&exp[x..x + 1]) {
            let mut num = String::new();
            let mut y = x;
            while y < exp.len() && is_numeric(&exp[y..y + 1]) {
                num.push_str(&exp[y..y + 1]);
                y += 1;
            }

            //check if num is a valid number
            if &num[..1] == "." || &num[num.len() - 1..] == "." {
                return Err("invalid number!".to_owned());
            }
            if num.contains(".") {
                let mut dots = 0;
                for c in num.chars() {
                    if c == '.' {
                        dots += 1;
                    }
                }
                if dots > 1 {
                    return Err("invalid number!".to_owned());
                }
            }

            tokens.push(Token {
                token_type: TokenType::Num,
                value: num,
            });
            x = y;
            continue;
        }

        match &exp[x..x + 1] {
            "+" => {
                tokens.push(Token {
                    token_type: TokenType::Plus,
                    value: "+".to_owned(),
                });
            }
            "-" => {
                tokens.push(Token {
                    token_type: TokenType::Minus,
                    value: "-".to_owned(),
                });
            }
            "*" => {
                tokens.push(Token {
                    token_type: TokenType::Multiply,
                    value: "*".to_owned(),
                });
            }
            "/" => {
                tokens.push(Token {
                    token_type: TokenType::Divide,
                    value: "/".to_owned(),
                });
            }
            "%" => {
                tokens.push(Token {
                    token_type: TokenType::Remainder,
                    value: "%".to_owned(),
                });
            }
            "(" => {
                tokens.push(Token {
                    token_type: TokenType::LeftParen,
                    value: "(".to_owned(),
                });
            }
            ")" => {
                tokens.push(Token {
                    token_type: TokenType::RightParen,
                    value: ")".to_owned(),
                });
            }
            _ => {}
        }
        x += 1;
    }

    Ok(tokens)
}

fn is_numeric(c: &str) -> bool {
    let numeric_symbols = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."];

    for ns in numeric_symbols {
        if c == ns {
            return true;
        }
    }
    false
}
