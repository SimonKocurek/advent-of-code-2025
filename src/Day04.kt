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
        return input.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == BigInteger("3121910778619"))

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
