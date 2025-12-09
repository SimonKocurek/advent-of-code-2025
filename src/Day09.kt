import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val points = input.map { it.split(",").map(String::toLong) }

        return points.maxOf { (y1, x1) ->
            points.maxOf { (y2, x2) ->
                (abs(y2 - y1) + 1) * (abs(x2 - x1) + 1)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    // check(part2(testInput) == 25272L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
