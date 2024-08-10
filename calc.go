package main

func calc(exp string) string {
	var result string = "result!!!"

	if symbolError(exp) {
		result = "invalid symbol error!"
		return result
	}

	return result
}

func symbolError(exp string) bool {
	var error = false
find:
	for _, e := range exp {
		switch e {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '.':
		case '+':
		case '-':
		case '*':
		case '/':
		case '%':
		case '(':
		case ')':
		case ' ':
			continue
		default:
			error = true
			break find
		}
	}
	return error
}

func lexer(exp string) (bool, []string) {
	var tokens []string
	var error bool = false
	return error, tokens
}
