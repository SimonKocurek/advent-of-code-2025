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

    fun part2(input: List<String>): BigInteger {
        val numbers = input
            .subList(0, input.size - 1)

        val operands = input
            .last()
            .trim()
            .split(WHITESPACE_REGEX)

        var result = BigInteger.ZERO

        var operandIdx = 0
        var operation: BigInteger? = null
        for (x in numbers[0].indices) {
            var number = BigInteger.ZERO
            for (y in numbers.indices) {
                val char = numbers[y][x]
                if (char.isDigit()) {
                    number = (number * BigInteger.TEN) + char.digitToInt().toBigInteger()
                }
            }

            // Empty column
            if (number == BigInteger.ZERO) {
                operandIdx++
                result += operation ?: error("No operation")
                operation = null
                continue
            }

            if (operation == null) {
                // first number
                operation = number
                continue
            }

            val operand = operands[operandIdx]
            when (operand) {
                "+" -> operation += number
                "*" -> operation *= number
            }
        }

        result += operation ?: error("No operation")
        return result
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == BigInteger("4277556"))
    check(part2(testInput) == BigInteger("3263827"))

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
