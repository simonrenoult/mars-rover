
import Instruction.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertContentEquals

class MarsRoverSpec {

    @Nested inner class SettingsInitialPosition {
        @Test fun `GIVEN starting position 0,0 WHEN the rover scans its surroundings THEN the map appears with the rover on it`() {
            // Given
            val rover = Rover(initialMap = emptyMapOfMars(), startingPosition = Position(x = 0, y = 0))

            // When
            val surroundings = rover.scan()

            // Then
            assertContentEquals(
                listOf(
                    listOf("R", "o", "o"),
                    listOf("o", "o", "o"),
                    listOf("o", "o", "o"),
                ),
                surroundings,
            )
        }

        @Test fun `GIVEN starting position 1,2 WHEN the rover scans its surroundings THEN the map appears with the rover on it`() {
            // Given
            val rover = Rover(initialMap = emptyMapOfMars(), startingPosition = Position(x = 1, y = 2))

            // When
            val surroundings = rover.scan()

            // Then
            assertContentEquals(
                listOf(
                    listOf("o", "o", "o"),
                    listOf("o", "o", "o"),
                    listOf("o", "R", "o"),
                ),
                surroundings,
            )
        }
    }

    @Nested inner class WhenSendingInstructions {
        @TestFactory fun testRoverInstructions() = listOf(
            listOf(Down, Move) to listOf(
                listOf("o", "o", "o"),
                listOf("R", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move) to listOf(
                listOf("o", "R", "o"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Right, Move) to listOf(
                listOf("o", "R", "o"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move, Move) to listOf(
                listOf("o", "o", "R"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move, Move, Down, Move) to listOf(
                listOf("o", "o", "o"),
                listOf("o", "o", "R"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move, Move, Down, Move, Left, Move) to listOf(
                listOf("o", "o", "o"),
                listOf("o", "R", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move, Move, Move) to listOf(
                listOf("R", "o", "o"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Down, Move, Move, Move) to listOf(
                listOf("R", "o", "o"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
            listOf(Right, Move, Down, Move, Move, Move) to listOf(
                listOf("o", "R", "o"),
                listOf("o", "o", "o"),
                listOf("o", "o", "o"),
            ),
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("GIVEN a set of instructions WHEN providing them to the rover THEN it executes them as expected") {
                // Given
                val rover = Rover(initialMap = emptyMapOfMars(), startingPosition = Position(x = 0, y = 0))

                // When
                rover.sendInstructions(input)

                // Then
                assertContentEquals(expected, rover.scan())
            }
        }
    }

    @Test fun `When hitting an obstacle`() {
        // Given
        val rover = Rover(
            initialMap = mutableListOf(
                mutableListOf("o", "o", "o"),
                mutableListOf("o", "o", "o"),
                mutableListOf("o", "x", "o"),
            ),
            startingPosition = Position(x = 0, y = 0),
        )

        // When
        rover.sendInstructions(listOf(Right, Move, Down, Move, Move))

        // Then
        assertContentEquals(
            listOf(
                listOf("o", "o", "o"),
                listOf("o", "R", "o"),
                listOf("o", "x", "o"),
            ),
            rover.scan(),
        )
    }
}

private fun emptyMapOfMars() = mutableListOf(
    mutableListOf("o", "o", "o"),
    mutableListOf("o", "o", "o"),
    mutableListOf("o", "o", "o"),
)
