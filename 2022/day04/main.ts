import { readLines } from 'https://deno.land/std@0.167.0/io/mod.ts';

async function getGroupped() {
    const file = await Deno.open('./input.txt');
    const groupList: number[][][] = [];

    for await (const line of readLines(file)) {
        const parts = line.split(',');
        const groupIndex = groupList.length;
        groupList[groupIndex] = [];

        for (const part of parts) {
            const split = part.split('-');
            const first = parseInt(split[0], 10);
            const second = parseInt(split[1], 10);

            const covered: number[] = [];
            for (let i = first; i <= second; i++) {
                covered.push(i);
            }

            groupList[groupIndex].push(covered);
        }
    }

    return groupList;
}

function partOne(groupList: number[][][]) {
    let total = 0;
    for (const group of groupList) {
        const part1 = group[0];
        const part2 = group[1];

        if (part1.every((value) => part2.includes(value))) {
            total++;
        } else if (part2.every((value) => part1.includes(value))) {
            total++;
        }
    }
    console.log('Part1:', total);
}

function partTwo(groupList: number[][][]) {
    let total = 0;
    for (const group of groupList) {
        const part1 = group[0];
        const part2 = group[1];

        const part1InPart2 = part1.filter((value) => part2.includes(value));
        const part2InPart1 = part2.filter((value) => part1.includes(value));

        if (part1InPart2.length > 0 || part2InPart1.length > 0) {
            total++;
        }
    }
    console.log('Part2:', total);
}

const groupList = await getGroupped();
partOne(groupList);
partTwo(groupList);
