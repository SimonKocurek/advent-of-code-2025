import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>, connections: Int): Long {
        val positions = input
            .map { it.split(",").map { num -> num.toLong() } }

        val distances = PriorityQueue<Triple<Long, Int, Int>>(connections) { a, b ->
            -a.first.compareTo(b.first)
        }

        for (i in positions.indices) {
            val (x1, y1, z1) = positions[i]

            for (j in (i + 1)..positions.lastIndex) {
                val (x2, y2, z2) = positions[j]
                val xDiff = x1 - x2
                val yDiff = y1 - y2
                val zDiff = z1 - z2
                // We don't need to do sqrt, since the ordering will be the same, and we only care about comparisons
                val distance = (xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff)
                val entry = Triple(distance, i, j)

                if (distances.size < connections) {
                    distances.add(entry)
                    continue
                }

                if (distances.peek().first > distance) {
                    distances.poll()
                    distances.add(entry)
                }
            }
        }

        val parents = IntArray(input.size) { it }
        val sizes = IntArray(input.size) { 1 }
        while (distances.isNotEmpty()) {
            val (_, i, j) = distances.poll()
            union(parents, sizes, i, j)
        }

        return parents
            .map { parent -> find(parents, parent) }
            .distinct() // Get IDs of components
            .map { parent -> sizes[parent] }
            .map { it.toLong() }
            .sortedByDescending { it }
            .take(3)
            .reduce { acc, curr -> acc * curr }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput, connections = 10) == 40L)
//    check(part2(testInput) == BigInteger("40"))

    val input = readInput("Day08")
    part1(input, connections = 1000).println()
    part2(input).println()
}

private fun union(parents: IntArray, sizes: IntArray, i: Int, j: Int) {
    val parentI = find(parents, i)
    val parentJ = find(parents, j)

    if (parentI == parentJ) {
        return
    }

    if (sizes[parentI] >= sizes[parentJ]) {
        parents[parentJ] = parentI
        sizes[parentI] += sizes[parentJ]
    } else {
        parents[parentI] = parentJ
        sizes[parentJ] += sizes[parentI]
    }
}

private fun find(parents: IntArray, i: Int): Int {
    var parent = i

    while (parents[parent] != parent) {
        val nextParent = parents[parent]
        parents[parent] = parents[nextParent] // Path compression
        parent = nextParent
    }

    return parent
}
