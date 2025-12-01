fun main() {
    fun part1(input: List<String>): Int {
        var rotation = 50
        var zeroRotations = 0

        input
            .map { line -> line.first() to line.substring(1).toInt() }
            .onEach { (direction, shift) ->
                rotation = when (direction) {
                    'L' -> (rotation - (shift % 100)).let { if (it < 0) it + 100 else it }
                    'R' -> (rotation + shift) % 100
                    else -> error("Unknown direction: $direction")
                }

                if (rotation == 0) {
                    zeroRotations++
                }
            }

        return zeroRotations
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
