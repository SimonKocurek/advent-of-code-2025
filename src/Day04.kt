private val DIRECTIONS = (-1..1).flatMap { dX ->
    (-1..1).map { dY -> dY to dX }
}.filter { it != (0 to 0) }

fun main() {
    fun part1(input: List<String>): Int {
        return input.indices.sumOf { y ->
            input[y].indices.count { x ->
                if (input[y][x] != '@') {
                    return@count false // Not a roll of paper
                }

                val neighbours = DIRECTIONS.count { (dY, dX) ->
                    val newY = y + dY
                    val newX = x + dX

                    newY in input.indices && newX in input[newY].indices &&
                            input[newY][newX] == '@'
                }

                neighbours < 4
            }
        }
    }

    fun part2(input: List<String>): Int {
        val warehouse = input
            .map { it.toCharArray() }
            .toTypedArray()

        val candidatesForRemoval = warehouse.indices.flatMap { y ->
            warehouse[y].indices
                .filter { x -> warehouse[y][x] == '@' }
                .map { x -> y to x }
        }.toMutableList()

        var removals = 0
        while (candidatesForRemoval.isNotEmpty()) {
            val (y, x) = candidatesForRemoval.pop()
            if (warehouse[y][x] != '@') {
                continue // Might have been removed already
            }

            val neighbours = DIRECTIONS
                .map { (dY, dX) -> (y + dY) to (x + dX) }
                .filter { (newY, newX) ->
                    newY in warehouse.indices && newX in warehouse[newY].indices &&
                            warehouse[newY][newX] == '@'
                }

            if (neighbours.size >= 4) {
                continue
            }

            removals++
            warehouse[y][x] = '.'
            candidatesForRemoval.addAll(neighbours)
        }

        return removals
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
