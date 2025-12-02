fun main() {
    fun part1(input: List<String>): Long {
        return input
            .first() // Input is a single line
            .split(",")
            .map { range -> range.split("-").map { it.toLong() } }
            .map { (start, end) -> start..end }
            .sumOf { range ->
                range
                    .map { it.toString() }
                    .filter { it.length % 2 == 0 } // Micro-optimization - only even-length numbers can have repeated sequence
                    .filter {
                        val halfLength = it.length / 2
                        it.substring(0, halfLength) == it.substring(halfLength)
                    }
                    .sumOf { it.toLong() }
            }
    }

    fun part2(input: List<String>): Long {
        return input
            .first() // Input is a single line
            .split(",")
            .map { range -> range.split("-").map { it.toLong() } }
            .map { (start, end) -> start..end }
            .sumOf { range ->
                range
                    .map { it.toString() }
                    .filter {
                        (1..it.length / 2).any { substringLength ->
                            if (it.length % substringLength != 0) {
                                return@any false // Micro-optimization - substring can't repeat until the end, if the length is not multiple of substring length
                            }

                            val baseSubstring = it.substring(0, substringLength)

                            var offset = substringLength
                            while (offset < it.length) {
                                if (baseSubstring != it.substring(offset, offset + substringLength)) {
                                    return@any false
                                }
                                offset += substringLength
                            }

                            return@any true
                        }
                    }
                    .sumOf { it.toLong() }
            }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1_227_775_554L)
    check(part2(testInput) == 4_174_379_265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
