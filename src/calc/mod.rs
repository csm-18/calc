pub fn calc(exp:&str){
    //remove spaces from input expression
    let exp = exp.replace("\n", "");
    let exp = exp.replace(" ", "");
    
    println!("{exp}");

}