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
        val verticalLines = lines.filter { (p1, p2) -> p1[1] == p2[1] }
        val horizontalLines = lines.filter { (p1, p2) -> p1[0] == p2[0] }

        var result = 0L
        for (i in points.indices) {
            for (j in (i + 1)..points.lastIndex) {
                val (boxY1, boxX1) = points[i]
                val (boxY2, boxX2) = points[j]

                val boxBottomEnd = min(boxY1, boxY2)
                val boxTopEnd = max(boxY1, boxY2)
                val boxLeftEnd = min(boxX1, boxX2)
                val boxRightEnd = max(boxX1, boxX2)

                val crossesVerticalLine = verticalLines.any { (p1, p2) ->
                    // p1X == p2X
                    val (p1Y, p1X) = p1
                    val (p2Y, _) = p2

                    if (p1X <= boxLeftEnd || p1X >= boxRightEnd) {
                        return@any false // Line is on different side of box
                    }

                    if (max(p1Y, p2Y) > boxTopEnd || min(p1Y, p2Y) < boxBottomEnd) {
                        return@any false // Line is completely above or below box
                    }
                    
                    if () {
                        
                    }
                    
                    return@any true // Line crosses or inside box
                }
                if (crossesVerticalLine) {
                    continue
                }

                val crossesHorizontalLine = horizontalLines.any { (p1, p2) ->
                    // p1Y == p2Y
                    val (p1Y, p1X) = p1
                    val (_, p2X) = p2

                    if (p1Y >= boxTopEnd || p1Y <= boxBottomEnd) {
                        return@any false // Line is on different side of box
                    }

                    if (max(p1X, p2X) > boxRightEnd || min(p1X, p2X) < boxLeftEnd) {
                        return@any false // Line is completely left or right of box
                    }
                    
                    // TODO

                    return@any true // Line crosses or inside box
                }
                if (crossesHorizontalLine) {
                    continue
                }

                val area = (abs(boxY2 - boxY1) + 1) * (abs(boxX2 - boxX1) + 1)
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
