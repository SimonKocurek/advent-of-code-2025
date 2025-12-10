import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.Status.SATISFIABLE
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

            Part1ParsedInput(
                indicatorLightsTarget = indicatorLightsTarget,
                buttons = buttons,
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

    // Shamelessly implemented using both a library **and** AI. :/
    fun part2(input: List<String>): Long {
        val parsedInput = input.map { machineDiagram ->
            val machineDiagramParts = machineDiagram
                .split(" ")
                .map { it.trimBrackets() }

            val buttons = machineDiagramParts
                .subList(1, machineDiagramParts.size - 1)
                .map { button -> button.split(",").map { it.toInt() } }

            val joltageRequirements = machineDiagramParts.last().split(",").map { it.toInt() }

            Part2ParsedInput(
                buttons = buttons,
                joltageRequirements = joltageRequirements
            )
        }

        return Context().use { context ->
            parsedInput.sumOf { machine ->
                val optimize = context.mkOptimize()

                val buttonsClicks = Array(machine.buttons.size) { i ->
                    context.mkIntConst("button_$i")
                }

                // Buttons can't be clicked a negative number of times
                val zero = context.mkInt(0)
                for (buttonClicks in buttonsClicks) {
                    optimize.Add(context.mkGe(buttonClicks, zero))
                }

                // Joltage requirements must be met
                for (joltageRequirementIdx in machine.joltageRequirements.indices) {
                    val targetJoltage = machine.joltageRequirements[joltageRequirementIdx]

                    val buttonsAffectingJoltage = mutableListOf<IntExpr>()
                    for (buttonIdx in machine.buttons.indices) {
                        val button = machine.buttons[buttonIdx]
                        if (joltageRequirementIdx in button) {
                            buttonsAffectingJoltage.add(buttonsClicks[buttonIdx])
                        }
                    }
                    if (buttonsAffectingJoltage.isEmpty()) {
                        if (targetJoltage != 0) {
                            error("There is no button we could press to reach the target state for this joltage requirement.")
                        }
                        continue
                    }

                    val sumEffects = context.mkAdd(*buttonsAffectingJoltage.toTypedArray())

                    // Button presses need to add up to the target joltage
                    optimize.Add(context.mkEq(
                        sumEffects, context.mkInt(targetJoltage)
                    ))
                }

                val totalClicks = context.mkAdd(*buttonsClicks)
                val minimize = optimize.MkMinimize(totalClicks)

                val status = optimize.Check() // Start the solver
                if (status != SATISFIABLE) {
                    error("Could not find a solution")
                }

                minimize.lower.toString().toLong()
            }
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 7L)
    check(part2(testInput) == 33L)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

data class Part1ParsedInput(
    val indicatorLightsTarget: BitSet,
    val buttons: List<BitSet>,
)

data class Part2ParsedInput(
    val buttons: List<List<Int>>,
    val joltageRequirements: List<Int>
)

private fun String.trimBrackets() = substring(1, length - 1)
