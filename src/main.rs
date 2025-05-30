use std::{
    io::{Write, stdin, stdout},
    process::exit,
};

mod calc;

const VERSION: &str = "0.1.1";
fn main() {
    println!("calc {VERSION}");
    loop {
        print!(">> ");
        stdout().flush().unwrap();

        //read input expression
        let exp = input();

        if &exp == "\n" {
            continue;
        }

        //print result
        println!("{}",calc::calc(&exp));
    }
}

fn input() -> String {
    let mut user_input = String::new();
    match stdin().read_line(&mut user_input) {
        Ok(_) => user_input,
        Err(_) => {
            println!("internal error!");
            exit(1);
        }
    }
}
