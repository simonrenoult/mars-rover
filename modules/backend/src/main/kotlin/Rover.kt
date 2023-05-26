import Instruction.Down
import Instruction.Left
import Instruction.Move
import Instruction.Right
import Instruction.Up

enum class Instruction { Up, Down, Left, Right, Move }

class Rover(
    private val initialMap: RoverMap,
    private val startingPosition: Position,
    private var instructions: MutableList<Instruction> = ArrayList(),
) {
    fun scan(): RoverMap = computePosition()
    fun sendInstructions(instructions: List<Instruction>) = this.instructions.addAll(instructions)

    private fun computePosition(): RoverMap = landOnInitialPosition().also { moveAccordingToInstructions(it) }

    private fun moveAccordingToInstructions(roverMap: RoverMap) {
        var currentDirection: Instruction? = null
        this.instructions.forEach { instruction ->
            when (instruction) {
                Move -> when (currentDirection) {
                    Up -> roverMap.moveRover { y, x -> y - 1 to x }
                    Down -> roverMap.moveRover { y, x -> y + 1 to x }
                    Left -> roverMap.moveRover { y, x -> y to x - 1 }
                    Right -> roverMap.moveRover { y, x -> y to x + 1 }
                    else -> Unit
                }
                else -> currentDirection = instruction
            }
        }
    }

    private fun landOnInitialPosition(): RoverMap {
        return initialMap.mapIndexed { i: Int, row: MutableList<String> ->
            row.mapIndexed { j: Int, cell: String -> if (j == startingPosition.x && i == startingPosition.y) MapIndicator.Rover.text else cell }
        } as RoverMap
    }
}
