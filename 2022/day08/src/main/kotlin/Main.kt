import kotlin.streams.asStream

enum class Direction {
    TOP,
    LEFT,
    RIGHT,
    BOTTOM
}

class Map(val grid: Array<IntArray>, val height: Int, val width: Int)

class Tree(val visible: Boolean, val nbTree: Int)

fun parseToIntArray(fileName: String): Map {
    val lines = {}.javaClass.getResourceAsStream("$fileName.txt")?.bufferedReader()?.readLines()
    if (lines.isNullOrEmpty()) throw Exception("Invalid input")

    val mapWidth = lines[0].length
    val mapHeight = lines.size
    val map = Array(mapHeight) { IntArray(mapHeight) }

    for (h in 0 until mapHeight) {
        for (w in 0 until mapWidth) {
            map[h][w] = lines[h][w].toString().toInt()
        }
    }

    return Map(map, mapHeight, mapWidth)
}

fun isVisibleDirection(value: Int, map: Map, h: Int, w: Int, direction: Direction): Tree {
    var hCur = h
    var wCur = w

    when (direction) {
        Direction.TOP -> if (--hCur == -1) return Tree(true, 0)
        Direction.LEFT -> if (--wCur == -1) return Tree(true, 0)
        Direction.RIGHT -> if (++wCur >= map.width) return Tree(true, 0)
        Direction.BOTTOM -> if (++hCur >= map.height) return Tree(true, 0)
    }

    val neighborValue = map.grid[hCur][wCur]
    if (neighborValue >= value) return Tree(false, 1)

    val subTree = isVisibleDirection(value, map, hCur, wCur, direction)
    return Tree(subTree.visible, subTree.nbTree + 1)
}

fun getTreeListResult(map: Map, h: Int, w: Int): List<Tree> {
    return Direction.values().asSequence()
        .asStream()
        .map { direction -> isVisibleDirection(map.grid[h][w], map, h, w, direction) }
        .toList()
}

fun compute(map: Map) {
    var totalP1 = 0
    var bestTotalP2 = -1

    for (h in map.grid.indices) {
        for (w in map.grid[h].indices) {
            val treeList = getTreeListResult(map, h, w)
            if (!treeList.stream().allMatch { el -> !el.visible }) {
                totalP1++
            }

            val subTotalP2 = treeList.stream()
                .map { el -> el.nbTree }
                .reduce { a, b -> a * b }
                .get()

            if (bestTotalP2 == -1 || subTotalP2 > bestTotalP2) {
                bestTotalP2 = subTotalP2
            }
        }
    }

    println("Part1: $totalP1")
    println("Part2: $bestTotalP2")
}

fun main() {
    compute(parseToIntArray("input"))
}