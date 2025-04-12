pub fn calc(exp:&str){
    //remove spaces from input expression
    let exp = exp.replace("\n", "");
    let exp = exp.replace(" ", "");
    
    //check for invalid symbols in expression
    if !valid_symbols(&exp) {
        println!("invalid input!");
        return;
    }


    println!("{exp}");

}

fn valid_symbols(exp:&str)-> bool{
    let numeric_symbols = ['0','1','2','3','4','5','6','7','8','9','.'];
    let math_symbols = ['+','-','*','/','%','(',')'];

    for c in exp.chars()  {
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
        }else {
            return false;
        }
    }

    true
}