package main

import (
	"bufio"
	"fmt"
	"golang.org/x/exp/slices"
	"os"
)

func removeDuplicate(elements []string) []string {
	var filteredList []string
	encountered := make(map[string]bool)
	for _, element := range elements {
		if !encountered[element] {
			filteredList = append(filteredList, element)
			encountered[element] = true
		}
	}
	return filteredList
}

func intersection(s1, s2 []string) []string {
	var inter []string
	hash := map[string]bool{}
	for _, e := range s1 {
		hash[e] = true
	}
	for _, e := range s2 {
		if hash[e] {
			inter = append(inter, e)
		}
	}
	return removeDuplicate(inter)
}

func getItemAppearBoth(lines []string) []string {
	var itemAppearBoth []string
	for _, line := range lines {
		var first []string
		var second []string
		for index, char := range line {
			isFirst := index < len(line)/2
			charStr := string(char)

			if isFirst {
				if !slices.Contains(first, charStr) {
					first = append(first, charStr)
				}
			} else {
				if !slices.Contains(second, charStr) {
					second = append(second, charStr)
				}
			}
		}

		itemAppearBoth = append(itemAppearBoth, intersection(first, second)...)
	}

	return itemAppearBoth
}

func groupThreeByThree(lines []string) [][]string {
	var groupedLines [][]string
	for index, line := range lines {
		if index%3 == 0 {
			groupedLines = append(groupedLines, []string{})
		}
		arrIndex := (index - index%3) / 3
		groupedLines[arrIndex] = append(groupedLines[arrIndex], line)
	}

	return groupedLines
}

func stringToCharArray(str string) []string {
	chars := []rune(str)
	var output []string
	for i := 0; i < len(chars); i++ {
		output = append(output, string(chars[i]))
	}
	return output
}

func getBadge(groupedLines [][]string) []string {
	var badges []string
	for _, grouped := range groupedLines {
		intersect := intersection(stringToCharArray(grouped[0]), stringToCharArray(grouped[1]))
		intersect = intersection(intersect, stringToCharArray(grouped[2]))

		if len(intersect) > 0 {
			badges = append(badges, intersect[0])
		}
	}

	return badges
}

func calcScore(charList []string) int {
	score := 0
	for _, charStr := range charList {
		char := []rune(charStr)[0]
		if char >= 'A' && char <= 'Z' {
			score += int(char) - 65 + 27
		} else if char >= 'a' && char <= 'z' {
			score += int(char) - 97 + 1
		}
	}
	return score
}

func partOne(lines []string) {
	fmt.Println("Part1:", calcScore(getItemAppearBoth(lines)))
}

func partTwo(lines []string) {
	groupedLines := groupThreeByThree(lines)
	badges := getBadge(groupedLines)
	fmt.Println("Part2:", calcScore(badges))
}

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	partOne(lines)
	partTwo(lines)
}
