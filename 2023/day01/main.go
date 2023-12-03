package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"

	"github.com/samber/lo"
)

func part1(lines []string) int {
	total := 0
	for _, line := range lines {
		characters := strings.Split(line, "")
		numbersStr := lo.Map(characters, func(char string, _ int) *int {
			if nb, err := strconv.Atoi(char); err == nil {
				return &nb
			}
			return nil
		})
		numbers := lo.Filter(numbersStr, func(nb *int, _ int) bool {
			return nb != nil
		})

		first := numbers[0]
		last := numbers[len(numbers)-1]
		total += (*first * 10) + *last
	}

	return total
}

func part2(lines []string) int {
	return part1(lo.Map(lines, func(line string, _ int) string {
		replacers := [][]string{
			{"one", "one1one"}, {"two", "two2two"}, {"three", "three3three"}, {"four", "four4four"}, {"five", "five5five"},
			{"six", "six6six"}, {"seven", "seven7seven"}, {"eight", "eight8eight"}, {"nine", "nine9nine"},
		}
		for _, replacer := range replacers {
			line = strings.ReplaceAll(line, replacer[0], replacer[1])
		}
		return line
	}))
}

func main() {
	bytes, err := os.ReadFile("input.txt")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(string(bytes), "\n")

	fmt.Printf("Part 1: %d\n", part1(lines))
	fmt.Printf("Part 2: %d\n", part2(lines))
}
