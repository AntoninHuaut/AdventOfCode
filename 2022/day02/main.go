package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func getPoint(fCol string, sCol string) int {
	point := 0

	switch sCol {
	case "X":
		point += 1
	case "Y":
		point += 2
	case "Z":
		point += 3
	}

	if (fCol == "A" && sCol == "X") || (fCol == "B" && sCol == "Y") || (fCol == "C" && sCol == "Z") { // Draw
		point += 3
	} else if (fCol == "A" && sCol == "Y") || (fCol == "B" && sCol == "Z") || (fCol == "C" && sCol == "X") { // Win
		point += 6
	}

	return point
}

func partOne(lines [][]string) {
	score := 0
	for _, line := range lines {
		score += getPoint(line[0], line[1])
	}

	fmt.Println("Part1:", score)
}

func partTwo(lines [][]string) {
	score := 0
	for _, line := range lines {
		fCol := line[0]
		sCol := line[1]
		oCol := ""

		if sCol == "Y" { // Draw
			if fCol == "A" {
				oCol = "X"
			} else if fCol == "B" {
				oCol = "Y"
			} else if fCol == "C" {
				oCol = "Z"
			}
		} else if sCol == "Z" { // Win
			if fCol == "A" {
				oCol = "Y"
			} else if fCol == "B" {
				oCol = "Z"
			} else if fCol == "C" {
				oCol = "X"
			}
		} else if sCol == "X" { // Lose
			if fCol == "A" {
				oCol = "Z"
			} else if fCol == "B" {
				oCol = "X"
			} else if fCol == "C" {
				oCol = "Y"
			}
		}

		score += getPoint(fCol, oCol)
	}

	fmt.Println("Part2:", score)
}

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	var lines [][]string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		parts := strings.Split(scanner.Text(), " ")
		if len(parts) < 2 {
			panic("Invalid length")
		}

		lines = append(lines, parts)
	}

	partOne(lines)
	partTwo(lines)
}
