import java.math.BigInteger

private const val BATTERIES = 12

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .sumOf { line ->
                (9 downTo 1).firstNotNullOf { upperDigit ->
                    val upperDigitIndex = line.indexOf(upperDigit.digitToChar())
                    if (upperDigitIndex == -1) {
                        return@firstNotNullOf null
                    }

                    if (upperDigitIndex == line.lastIndex) {
                        return@firstNotNullOf null // To guarantee that there is room for a lower digit
                    }

                    val lowerDigit = (9 downTo 1).first { lowerDigit ->
                        line.indexOf(lowerDigit.digitToChar(), startIndex = upperDigitIndex + 1) != -1
                    }

                    upperDigit * 10 + lowerDigit
                }
            }
    }

    fun part2(input: List<String>): BigInteger {

        fun turnOnBatteries(line: String, offset: Int, batteries: MutableList<Int>): List<Int>? {
            if (batteries.size == BATTERIES) {
                return batteries.toList() // We have turned on all batteries
            }

            return (9 downTo 1).firstNotNullOfOrNull { digit ->
                val foundAt = line.indexOf(digit.digitToChar(), startIndex = offset)
                if (foundAt == -1) {
                    return@firstNotNullOfOrNull null // Digit not found
                }
                val remainingBatteries = BATTERIES - batteries.size - 1 // Including just found battery one
                if (foundAt > line.lastIndex - remainingBatteries) {
                    return@firstNotNullOfOrNull  null // Not enough room for remaining batteries
                }

                batteries.add(digit)
                val result = turnOnBatteries(line, offset = foundAt + 1, batteries = batteries)
                batteries.pop()

                result
            }
        }

        return input.sumOf { line ->
            turnOnBatteries(line, offset = 0, batteries = mutableListOf())
                ?.joinToString("")
                ?.toBigInteger()
                ?: error("Could not turn on all batteries for line: $line")
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == BigInteger("3121910778619"))

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
