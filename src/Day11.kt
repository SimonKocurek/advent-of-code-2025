import java.util.Stack

private const val START = "you"
private const val END = "out"

fun main() {
    fun part1(input: List<String>): Int {
        val paths = input.map { line ->
            val parts = line.split(" ")

            val from = parts[0].substring(0, parts[0].length - 1) // Remove ':'
            val to = parts.subList(1, parts.size)

            from to to
        }.associateBy({ it.first }, { it.second })

        var waysToReachEnd = 0
        val toVisit = Stack<String>()
        toVisit.push(START)

        while (toVisit.isNotEmpty()) {
            val from = toVisit.pop()

            val neighbors = paths[from] ?: continue
            for (neighbor in neighbors) {
                if (neighbor == END) {
                    waysToReachEnd++
                    continue
                }

                toVisit.push(neighbor)
            }
        }

        return waysToReachEnd
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 5)
    // check(part2(testInput) == 24)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
