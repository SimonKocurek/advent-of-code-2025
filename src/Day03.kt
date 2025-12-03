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

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
//    check(part2(testInput) == 1_174_379_265)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
