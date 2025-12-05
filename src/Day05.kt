import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        val ranges = input
            .takeWhile { it.isNotEmpty() }
            .map { line -> line.split("-").map { BigInteger(it) } }

        val ids = input
            .subList(ranges.size + 1, input.size)
            .map { BigInteger(it) }

        return ids
            .count { id -> ranges.any { (start, end) -> id in start..end } }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
//    check(part2(testInput) == 43)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
