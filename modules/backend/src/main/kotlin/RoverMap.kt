typealias RoverMap = List<MutableList<String>>
fun RoverMap.moveRover(computeNewRoverPosition: (Int, Int) -> Pair<Int, Int>) {
    var roverHasNotMovedYet = true
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (cell == MapIndicator.Rover.text && roverHasNotMovedYet) {
                val (newY, newX) = computeNewRoverPosition(y, x)
                val adjustedNewX = if (newX >= row.size) 0 else newX
                val adjustedNewY = if (newY >= this.size) 0 else newY
                if (this[adjustedNewY][adjustedNewX] == MapIndicator.OpenField.text) {
                    this[adjustedNewY][adjustedNewX] = MapIndicator.Rover.text
                    roverHasNotMovedYet = false
                    this[y][x] = MapIndicator.OpenField.text
                }
            }
        }
    }
}

data class Position(val x: Number, val y: Number)
enum class MapIndicator(val text: String) { Rover("R"), OpenField("o") }
