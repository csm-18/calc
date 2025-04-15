use std::result;

use crate::calc::{Token, TokenType};



pub fn parser(tokens: Vec<Token>) {
    
    println!("testing add_and_subtract() calculation!");
    match add_and_subtract(tokens){
        Ok(tokens) => {
            let final_result = tokens[0].value.parse::<f64>().unwrap();
            println!("{}", final_result);
        },
        Err(err) => {
            println!("{}", err);
        }
    };
}

//under construction!
// fn remove_parentheses(tokens:Vec<Token>)->Result<Vec<Token>,String>{
//     if tokens.iter().any(|token| matches!(token.token_type, TokenType::LeftParen | TokenType::RightParen)) {
//         let mut x = 0;
//         while x < tokens.len() {
//             if tokens[x].token_type == TokenType::LeftParen {
//                 let mut no_right_paren = true;
//                 let mut y = x + 1;
//                 while y < tokens.len() {
//                     if tokens[y].token_type == TokenType::RightParen {
//                         no_right_paren = false;
//                         break;
//                     }
//                     y += 1;
//                 }

//                 if no_right_paren {
//                     return Err("invalid expression!".to_string());
//                 }else {
//                     if tokens[x+1..y].iter().any(|token| token.token_type == TokenType::LeftParen) {
//                         x = y;
//                         continue;
//                     }else {
//                         if y - x == 1 {
//                             return Err("invalid expression!".to_string());
                            
//                         }else if tokens[x+1..y].iter().any(|token| token.token_type == TokenType::RightParen) {
//                             return Err("invalid expression!".to_string());
                            
//                         }
//                     }
//                 }
//             }
//             x += 1;
//         }
        
//     }
//     Ok(tokens)
    
    
// }


//evaluates the expression that can contain addition, subtraction, multiplication, division and remainder
// fn eval(tokens:Vec<Token>)->Result<Token,String>{

//     if tokens.len() == 1 {
//         match tokens[0].value.parse::<f64>(){
//             Ok(num) => Ok(Token{
//                 token_type: TokenType::Num,
//                 value: num.to_string(),
//             }),
//             Err(_) => Err("invalid number!".to_string()),
//         }
//     }else if !tokens.iter().any(|token| matches!(token.token_type, TokenType::Plus | TokenType::Minus | TokenType::Multiply | TokenType::Divide | TokenType::Remainder)) {
//         return Err("invalid expression!".to_string());
//     }else {
//         let mut result = 0.0;
//         result = match add_and_subtract(tokens) {
//             Ok(num) => num,
//             Err(_) => return Err("invalid expression!".to_string()),
            
//         };
//         return Ok(Token{
//             token_type: TokenType::Num,
//             value: result.to_string(),
//         });
//     }


    
// }
    
fn add_and_subtract(tokens:Vec<Token>)->Result<Vec<Token>,String>{
    let mut tokens = tokens;
    let mut x = 0;
    while x < tokens.len() {
        if tokens.len() == 1 && tokens[x].token_type == TokenType::Num {
            return Ok(tokens);    
        }else if x != 0 && x+1 < tokens.len() && tokens[x].token_type == TokenType::Plus{
            let left_operand = match tokens[x-1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let right_operand = match tokens[x+1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let result = left_operand + right_operand;
            let result_token = Token{
                token_type: TokenType::Num,
                value: result.to_string(),
            };
            tokens.drain(x-1..=x+1);
            tokens.insert(x-1, result_token);
            x -= 1;
            continue;
        }else if x != 0 && x+1 < tokens.len() && tokens[x].token_type == TokenType::Minus{
            let left_operand = match tokens[x-1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let right_operand = match tokens[x+1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let result = left_operand - right_operand;
            let result_token = Token{
                token_type: TokenType::Num,
                value: result.to_string(),
            };
            tokens.drain(x-1..=x+1);
            tokens.insert(x-1, result_token);
            x -= 1;
            continue;
        }else if tokens[x].token_type == TokenType::Num {
            x += 1;
            continue;
        }else {
                return Err("invalid expression!".to_string());
        }
        
    }
    
    if tokens.len() == 1 && tokens[x].token_type == TokenType::Num {
        Ok(tokens)
    }else {
        Err("invalid expression!".to_string())
    }    

}

fn multiply_and_divide_and_remainder(tokens:Vec<Token>)->Result<Vec<Token>,String>{
    let mut tokens = tokens;
    let mut x = 0;
    while x < tokens.len() {
        if tokens.len() == 1 && tokens[x].token_type == TokenType::Num {
            return Ok(tokens);    
        }else if x != 0 && x+1 < tokens.len() && tokens[x].token_type == TokenType::Multiply{
            let left_operand = match tokens[x-1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let right_operand = match tokens[x+1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let result = left_operand * right_operand;
            let result_token = Token{
                token_type: TokenType::Num,
                value: result.to_string(),
            };
            tokens.drain(x-1..=x+1);
            tokens.insert(x-1, result_token);
            x -= 1;
            continue;
        }else if x != 0 && x+1 < tokens.len() && tokens[x].token_type == TokenType::Divide{
            let left_operand = match tokens[x-1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let right_operand = match tokens[x+1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let result = left_operand / right_operand;
            let result_token = Token{
                token_type: TokenType::Num,
                value: result.to_string(),
            };
            tokens.drain(x-1..=x+1);
            tokens.insert(x-1, result_token);
            x -= 1;
            continue;
        }else if x != 0 && x+1 < tokens.len() && tokens[x].token_type == TokenType::Remainder{
            let left_operand = match tokens[x-1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let right_operand = match tokens[x+1].value.parse::<f64>(){
                Ok(num) => num,
                Err(_) => return Err("invalid expression!".to_string()),
            };
            let result = left_operand % right_operand;
            let result_token = Token{
                token_type: TokenType::Num,
                value: result.to_string(),
            };
            tokens.drain(x-1..=x+1);
            tokens.insert(x-1, result_token);
            x -= 1;
            continue;
        }else if tokens[x].token_type == TokenType::Num {
            x += 1;
            continue;
        }else {
                return Err("invalid expression!".to_string());
        }
        
    }
    
    if tokens.len() == 1 && tokens[x].token_type == TokenType::Num {
        Ok(tokens)
    }else {
        Err("invalid expression!".to_string())
    }     
}