private const val START_ROTATION = 50
private const val MAX_ROTATION = 100
private const val DEBUG = false

fun main() {
    fun part1(input: List<String>): Int {
        var rotation = START_ROTATION
        var zeroRotations = 0

        input
            .map { line -> line.first() to line.substring(1).toInt() }
            .onEach { (direction, shift) ->
                rotation = when (direction) {
                    'L' -> (rotation - (shift % MAX_ROTATION)).let { if (it < 0) it + MAX_ROTATION else it }
                    'R' -> (rotation + shift) % MAX_ROTATION
                    else -> error("Unknown direction: $direction")
                }

                if (rotation == 0) {
                    zeroRotations++
                }
            }

        return zeroRotations
    }

    fun part2(input: List<String>): Int {
        var rotation = START_ROTATION
        var zeroRotations = 0

        input
            .map { line -> line.first() to line.substring(1).toInt() }
            .onEach { (direction, shift) ->
                if (DEBUG) {
                    println("Rotation $rotation")
                }

                when (direction) {
                    'L' -> {
                        zeroRotations += shift / MAX_ROTATION
                        var newRotation = rotation - (shift % MAX_ROTATION)
                        if (newRotation < 0) {
                            newRotation += MAX_ROTATION

                            if (rotation != 0) {
                                zeroRotations++
                            }
                        }
                        if (newRotation == 0) {
                            zeroRotations++
                        }

                        rotation = newRotation
                    }

                    'R' -> {
                        rotation += shift
                        zeroRotations += rotation / MAX_ROTATION
                        rotation %= MAX_ROTATION
                    }

                    else -> error("Unknown direction: $direction")
                }

                if (DEBUG) {
                    println("Direction $direction")
                    println("Shift $shift")
                    println("Updated Rotation $rotation")
                    println("Zero Rotations $zeroRotations")
                    println()
                }
            }

        return zeroRotations
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
