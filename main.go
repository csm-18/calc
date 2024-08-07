package main

import (
	"fmt"
	"os"
)

const VERSION = "v1.0.0"

func main() {
	fmt.Println("calc", VERSION)
	for {
		var input string
		var result string
		fmt.Println("Enter expression('q' for quit):")
		fmt.Scanln(&input)
		if len(input) == 0 {
			continue
		} else if input == "q" || input == "Q" {
			os.Exit(0)
		}

		result = calc(input)
		fmt.Println("Result:", result)
	}
}
