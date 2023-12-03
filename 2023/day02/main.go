package main

import (
	"fmt"
	"github.com/samber/lo"
	"os"
	"strconv"
	"strings"
)

type Cube struct {
	Amount   int    `json:"amount"`
	CubeType string `json:"cubeType"`
}

type Bag struct {
	Cubes []Cube `json:"cubes"`
}

type Game struct {
	Id   int   `json:"id"`
	Bags []Bag `json:"bags"`
}

type Limit struct {
	Red   int `json:"red"`
	Green int `json:"green"`
	Blue  int `json:"blue"`
}

func parse(lines []string) []Game {
	var games []Game
	for _, line := range lines {
		parts := strings.Split(line, ":")
		gameId, _ := strconv.Atoi(strings.TrimSpace(strings.ReplaceAll(parts[0], "Game", "")))

		gamesRaw := strings.Split(parts[1], ";")
		var bags []Bag
		for _, gameRaw := range gamesRaw {
			gameCubesRaw := strings.Split(strings.TrimSpace(gameRaw), ",")
			var cubes []Cube
			for _, cubesRaw := range gameCubesRaw {
				cubeSplit := strings.Split(strings.TrimSpace(cubesRaw), " ")
				amount, _ := strconv.Atoi(cubeSplit[0])
				cubes = append(cubes, Cube{Amount: amount, CubeType: cubeSplit[1]})
			}

			bags = append(bags, Bag{Cubes: cubes})
		}

		games = append(games, Game{Id: gameId, Bags: bags})
	}

	return games
}

func part1(games []Game, limit Limit) int {
	total := 0

GAME:
	for _, game := range games {
		for _, bag := range game.Bags {
			for _, cube := range bag.Cubes {
				if cube.CubeType == "red" && cube.Amount > limit.Red {
					continue GAME
				} else if cube.CubeType == "green" && cube.Amount > limit.Green {
					continue GAME
				} else if cube.CubeType == "blue" && cube.Amount > limit.Blue {
					continue GAME
				}
			}
		}
		total += game.Id
	}
	return total
}

func part2(games []Game) int {
	total := 0

	for _, game := range games {
		minLimit := Limit{Red: 0, Green: 0, Blue: 0}

		for _, bag := range game.Bags {
			for _, cube := range bag.Cubes {
				if cube.CubeType == "red" && cube.Amount > minLimit.Red {
					minLimit.Red = cube.Amount
				} else if cube.CubeType == "green" && cube.Amount > minLimit.Green {
					minLimit.Green = cube.Amount
				} else if cube.CubeType == "blue" && cube.Amount > minLimit.Blue {
					minLimit.Blue = cube.Amount
				}
			}
		}
		total += minLimit.Red * minLimit.Green * minLimit.Blue
	}
	return total
}

func main() {
	bytes, err := os.ReadFile("input.txt")
	if err != nil {
		panic(err)
	}
	lines := lo.Filter(strings.Split(string(bytes), "\n"), func(s string, _ int) bool {
		return s != ""
	})

	games := parse(lines)
	fmt.Printf("Part 1: %d\n", part1(games, Limit{Red: 12, Green: 13, Blue: 14}))
	fmt.Printf("Part 2: %d\n", part2(games))
}
