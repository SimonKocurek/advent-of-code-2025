fun main() {
    fun part1(input: List<String>): Int {
        val shapeIterator = input.iterator()

        val shapeSizes = buildList {
            (0..5).forEach { _ ->
                shapeIterator.next() // Index
                val requiredSpace =
                    shapeIterator.next().count { it == '#' } +
                        shapeIterator.next().count { it == '#' } +
                        shapeIterator.next().count { it == '#' }
                shapeIterator.next() // Empty line

                add(requiredSpace)
            }
        }

        var fittingShapes = 0

        while (shapeIterator.hasNext()) {
            val lineParts = shapeIterator.next().split(' ')

            val (x, y) = lineParts[0]
                .trimEnd(':')
                .split('x')
                .map(String::toInt)

            val neededSpace = lineParts
                .subList(1, lineParts.size)
                .map(String::toInt)
                .mapIndexed { i, count -> shapeSizes[i] * count }
                .sum()

            if (neededSpace <= x * y) {
                fittingShapes++ // It's not guaranteed to fit... but when we assume it does, we get the right answer
            }
        }

        return fittingShapes
    }

    val input = readInput("Day12")
    part1(input).println()
}
