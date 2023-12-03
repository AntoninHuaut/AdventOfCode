package main

import (
	"fmt"
	"github.com/samber/lo"
	"os"
	"strconv"
	"strings"
)

type Cell struct {
	Height int `json:"height"`
	Width  int `json:"width"`
}

type NeighboursSymbol struct {
	Cell   Cell   `json:"cell"`
	Symbol string `json:"symbol"`
}

type NumberPart struct {
	Number    int  `json:"number"`
	CellFirst Cell `json:"cell"`
}

type EnginePart struct {
	NumberPart       NumberPart         `json:"numberPart"`
	NeighboursSymbol []NeighboursSymbol `json:"neighboursSymbol"`
}

func isNumber(char string) bool {
	return char >= "0" && char <= "9"
}

func parse(lines []string) []EnginePart {
	var parts []EnginePart

	for h, line := range lines {
		numberBuilder := ""
		part := EnginePart{NumberPart: NumberPart{}, NeighboursSymbol: []NeighboursSymbol{}}
		for w, char := range strings.Split(line, "") {
			if isNumber(char) {
				if numberBuilder == "" {
					part.NumberPart.CellFirst = Cell{
						Height: h,
						Width:  w,
					}
				}
				numberBuilder += char
				for i := -1; i <= 1; i++ {
					for j := -1; j <= 1; j++ {
						if i == 0 && j == 0 {
							continue
						}
						if h+i < 0 || h+i >= len(lines) || w+j < 0 || w+j >= len(lines[h+i]) {
							continue
						}

						neighbourChar := string(lines[h+i][w+j])
						if !isNumber(neighbourChar) && neighbourChar != "." && neighbourChar != "\r" {
							if !lo.Contains(part.NeighboursSymbol, NeighboursSymbol{
								Cell: Cell{
									Height: h + i,
									Width:  w + j,
								},
								Symbol: neighbourChar,
							}) {
								part.NeighboursSymbol = append(part.NeighboursSymbol, NeighboursSymbol{
									Cell: Cell{
										Height: h + i,
										Width:  w + j,
									},
									Symbol: neighbourChar,
								})
							}
						}
					}
				}
			}

			if !isNumber(char) || w == len(line)-1 {
				if numberBuilder != "" {
					nb, _ := strconv.Atoi(numberBuilder)
					part.NumberPart.Number = nb
					parts = append(parts, part)
				}
				part = EnginePart{NumberPart: NumberPart{}, NeighboursSymbol: []NeighboursSymbol{}}
				numberBuilder = ""
			}
		}
	}

	return parts
}

func part1(parts []EnginePart) int {
	total := 0
	for _, part := range parts {
		if len(part.NeighboursSymbol) > 0 {
			total += part.NumberPart.Number
		}
	}
	return total
}

func part2(parts []EnginePart) int {
	total := 0
	for _, part := range parts {
		for _, otherPart := range parts {
			if part.NumberPart.CellFirst.Height == otherPart.NumberPart.CellFirst.Height &&
				part.NumberPart.CellFirst.Width == otherPart.NumberPart.CellFirst.Width {
				continue
			}

			for _, partNeighbour := range part.NeighboursSymbol {
				for _, otherPartNeighbour := range otherPart.NeighboursSymbol {
					if partNeighbour.Cell.Height == otherPartNeighbour.Cell.Height &&
						partNeighbour.Cell.Width == otherPartNeighbour.Cell.Width &&
						partNeighbour.Symbol == "*" {
						total += part.NumberPart.Number * otherPart.NumberPart.Number
					}
				}
			}
		}
	}
	return total / 2
}

func main() {
	bytes, err := os.ReadFile("input.txt")
	if err != nil {
		panic(err)
	}
	lines := lo.Filter(strings.Split(string(bytes), "\n"), func(s string, _ int) bool {
		return s != ""
	})

	parts := parse(lines)
	fmt.Printf("Part 1: %d\n", part1(parts))
	fmt.Printf("Part 2: %d\n", part2(parts))
}
