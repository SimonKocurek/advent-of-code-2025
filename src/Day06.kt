import java.math.BigInteger

private val WHITESPACE_REGEX = "\\s+".toRegex()

fun main() {
    fun part1(input: List<String>): BigInteger {
        val numbers = input
            .subList(0, input.size - 1)
            .map { it.trim().split(WHITESPACE_REGEX).map { number -> BigInteger(number) } }

        val operands = input
            .last()
            .trim()
            .split(WHITESPACE_REGEX)

        var result = BigInteger.ZERO

        for (x in numbers[0].indices) {
            val operand = operands[x]
            var line = numbers[0][x]
            for (y in 1..numbers.lastIndex) {
                when (operand) {
                    "+" -> line += numbers[y][x]
                    "*" -> line *= numbers[y][x]
                }
            }
            result += line
        }

        return result
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == BigInteger("4277556"))
//    check(part2(testInput) == BigInteger("14"))

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
