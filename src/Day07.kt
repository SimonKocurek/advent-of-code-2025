import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        val xSize = input[0].length

        var enabled = BooleanArray(xSize) { false }
        var enabledNext = BooleanArray(xSize) { false }

        val startX = input[0].indexOf('S')
        enabled[startX] = true

        var splits = 0

        for (y in 1..input.lastIndex) {
            for (x in 0 until xSize) {

                when (input[y][x]) {
                    '^' -> if (enabled[x]) {
                        if (x - 1 >= 0) {
                            enabledNext[x - 1] = true
                        }
                        if (x + 1 < xSize) {
                            enabledNext[x + 1] = true
                        }
                        splits++
                    }
                    '.' -> if (enabled[x]) {
                        enabledNext[x] = true
                    }
                }
            }

            val temp = enabled
            enabled = enabledNext
            enabledNext = temp
            enabledNext.fill(false)
        }

        return splits
    }

    fun part2(input: List<String>): BigInteger {
        val xSize = input[0].length

        var enabled = Array<BigInteger>(xSize) { BigInteger.ZERO }
        var enabledNext = Array<BigInteger>(xSize) { BigInteger.ZERO }

        val startX = input[0].indexOf('S')
        enabled[startX] = BigInteger.ONE

        for (y in 1..input.lastIndex) {
            for (x in 0 until xSize) {

                when (input[y][x]) {
                    '^' -> {
                        if (x - 1 >= 0) {
                            enabledNext[x - 1] += enabled[x]
                        }
                        if (x + 1 < xSize) {
                            enabledNext[x + 1] += enabled[x]
                        }
                    }
                    '.' -> enabledNext[x] += enabled[x]
                }
            }

            val temp = enabled
            enabled = enabledNext
            enabledNext = temp
            enabledNext.fill(BigInteger.ZERO)
        }

        return enabled.sumOf { it }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == BigInteger("40"))

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
