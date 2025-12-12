import java.util.Stack

private const val START_PART1 = "you"
private const val START_PART2 = "svr"
private const val MIDDLE_POINT1 = "dac"
private const val MIDDLE_POINT2 = "fft"
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
        toVisit.push(START_PART1)

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

    fun part2(input: List<String>): Long {
        val paths = input.map { line ->
            val parts = line.split(" ")

            val from = parts[0].substring(0, parts[0].length - 1) // Remove ':'
            val to = parts.subList(1, parts.size)

            from to to
        }.associateBy({ it.first }, { it.second })

        fun waysToReach(from: String, to: String): Long {
            val waysToReachEndMemo = mutableMapOf<String, Long>()

            fun dfs(from: String): Long {
                waysToReachEndMemo[from]?.let { return it }

                var result = 0L
                for (neighbor in paths[from].orEmpty()) {
                    if (neighbor == to) {
                        result++
                        continue
                    }

                    result += dfs(neighbor)
                }

                waysToReachEndMemo[from] = result
                return result
            }

            return dfs(from)
        }

        return (
            waysToReach(START_PART2, MIDDLE_POINT1) *
                waysToReach(MIDDLE_POINT1, MIDDLE_POINT2) *
                waysToReach(MIDDLE_POINT2, END)
            ) + (
            waysToReach(START_PART2, MIDDLE_POINT2) *
                waysToReach(MIDDLE_POINT2, MIDDLE_POINT1) *
                waysToReach(MIDDLE_POINT1, END)
            )
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 5)
    val testInput2 = readInput("Day11_test2")
    check(part2(testInput2) == 2L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
