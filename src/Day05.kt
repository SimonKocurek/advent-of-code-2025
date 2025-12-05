import java.math.BigInteger
import java.math.BigInteger.ONE

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

    fun part2(input: List<String>): BigInteger {
        val ranges = input
            .takeWhile { it.isNotEmpty() }
            .map { line -> line.split("-").map { BigInteger(it) } }
            .sortedBy { (start, _) -> start }

        val mergedRanges = buildList {
            ranges.forEach { (start, end) ->
                if (isEmpty()) {
                    // Should happen only in the first iteration
                    add(start to end)
                    return@forEach
                }

                if (start > last().second) {
                    // No overlap
                    add(start to end)
                    return@forEach
                }

                if (end <= last().second) {
                    // Fully contained
                    return@forEach
                }

                val lastRange = pop()
                add(lastRange.first to end)
            }
        }

        return mergedRanges.sumOf { (start, end) -> end - start + ONE }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == BigInteger("14"))

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
