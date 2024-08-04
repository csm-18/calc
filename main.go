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
		fmt.Println("enter expression('q' for quit):")
		fmt.Scanln(&input)
		if input == "q" || input == "Q" {
			os.Exit(0)
		}
	}
}
