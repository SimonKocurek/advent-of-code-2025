import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val points = input.map { it.split(",").map(String::toLong) }

        return points.maxOf { (y1, x1) ->
            points.maxOf { (y2, x2) ->
                (abs(y2 - y1) + 1) * (abs(x2 - x1) + 1)
            }
        }
    }

    fun part2(input: List<String>): Long {
        val points = input.map { it.split(",").map(String::toLong) }

        val lines = points.zipWithNext() + listOf(points.last() to points.first())
        val verticalLines = lines.filter { (p1, p2) -> p1[0] == p2[0] }
        val horizontalLines = lines.filter { (p1, p2) -> p1[1] == p2[1] }

        var result = 0L
        for (i in points.indices) {
            for (j in (i + 1)..points.lastIndex) {
                val (boxX1, boxY1) = points[i]
                val (boxX2, boxY2) = points[j]

                val boxXStart = min(boxX1, boxX2)
                val boxXEnd = max(boxX1, boxX2)
                val boxYStart = min(boxY1, boxY2)
                val boxYEnd = max(boxY1, boxY2)

                val crossesVerticalLine = verticalLines.any { (p1, p2) ->
                    val lineX = p1[0]
                    val lineYStart = min(p1[1], p2[1])
                    val lineYEnd = max(p1[1], p2[1])

                    if (lineX !in (boxXStart + 1)..(boxXEnd - 1)) {
                        return@any false // Line is on different side of box
                    }

                    if (lineYStart <= boxYStart && lineYEnd >= boxYEnd) {
                        return@any true // Line crosses box completely
                    }

                    if (lineYEnd <= boxYStart || boxYEnd <= lineYStart) {
                        return@any false // Line is completely above or below box
                    }

                    return@any true // Line crosses or inside box
                }
                if (crossesVerticalLine) {
                    continue
                }

                val crossesHorizontalLine = horizontalLines.any { (p1, p2) ->
                    val lineY = p1[1]
                    val lineXStart = min(p1[0], p2[0])
                    val lineXEnd = max(p1[0], p2[0])

                    if (lineY !in (boxYStart + 1)..(boxYEnd - 1)) {
                        return@any false // Line is on different side of box
                    }

                    if (lineXStart <= boxXStart && lineXEnd >= boxXEnd) {
                        return@any true // Line crosses box completely
                    }

                    if (lineXEnd <= boxXStart || lineXStart >= boxXEnd) {
                        return@any false // Line is completely left or right of box
                    }

                    return@any true // Line crosses or inside box
                }
                if (crossesHorizontalLine) {
                    continue
                }

                val area = (abs(boxX2 - boxX1) + 1) * (abs(boxY2 - boxY1) + 1)
                result = max(result, area)
            }
        }

        return result
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
