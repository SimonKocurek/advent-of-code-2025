import java.util.BitSet

fun main() {
    fun part1(input: List<String>): Long {
        val parsedInput = input.map { machineDiagram ->
            val machinieDiagramParts = machineDiagram
                .split(" ")
                .map { it.trimBrackets() }

            val length = machinieDiagramParts.first().length

            val indicatorLightsTarget = BitSet(length).apply {
                machinieDiagramParts.first().mapIndexed { i, ch -> set(i, ch == '#') }
            }

            val buttons = machinieDiagramParts
                .subList(1, machinieDiagramParts.size - 1)
                .map { button ->
                    BitSet(length).apply {
                        button.split(",").onEach { set(it.toInt()) }
                    }
                }

            val joltageRequirements = machinieDiagramParts.last().split(",").map { it.toInt() }

            ParsedInput(
                indicatorLightsTarget = indicatorLightsTarget,
                buttons = buttons,
                joltageRequirements = joltageRequirements
            )
        }

        return parsedInput.sumOf { data ->
            val seenIndicatorLights = mutableSetOf<BitSet>()

            val startIndicatorLights = BitSet(data.indicatorLightsTarget.length())
            seenIndicatorLights.add(startIndicatorLights)

            var distance = 0L
            var nextIndicatorLights = listOf(startIndicatorLights)

            while (nextIndicatorLights.isNotEmpty()) {
                distance++
                val followupState = mutableListOf<BitSet>()

                for (currentIndicatorLights in nextIndicatorLights) {
                    for (button in data.buttons) {
                        val indicatorLights = currentIndicatorLights.clone() as BitSet
                        indicatorLights.xor(button)

                        if (indicatorLights in seenIndicatorLights) {
                            continue
                        }

                        if (indicatorLights == data.indicatorLightsTarget) {
                            return@sumOf distance
                        }

                        seenIndicatorLights.add(indicatorLights)
                        followupState.add(indicatorLights)
                    }
                }

                nextIndicatorLights = followupState
            }

            error("Could not find a solution")
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 7L)
    // check(part2(testInput) == 24)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

private data class ParsedInput(
    val indicatorLightsTarget: BitSet,
    val buttons: List<BitSet>,
    val joltageRequirements: List<Int>
)

private fun String.trimBrackets() = substring(1, length - 1)
