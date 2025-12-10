import util.Util

private fun main() {
    // Input to char grid
    val input = Util.loadInputLines(2025, 7).map { it.toList() }

    var p1 = 0L

    data class Point(val r: Int, val c: Int)

    // Track active beams and a map of timelines that reach each position
    val initialBeam = Point(0, input[0].indexOf('S'))
    val timelineMap = mutableMapOf<Point, Long>(initialBeam to 1)
    var activeBeams = setOf(initialBeam)

    a@ while (true) {
        val newActiveBeams = mutableSetOf<Point>()

        for (beam in activeBeams) {
            // Break when beams reach the end
            if (beam.r == input.lastIndex) break@a

            // Read cell below the beam, and choose columns for continuing beam segments
            val directions = when (input[beam.r + 1][beam.c]) {
                '.' -> {
                    listOf(0)
                }

                '^' -> {
                    p1++ // Count splits
                    listOf(-1, +1)
                }

                else -> throw Exception()
            }

            // For each continuation
            for (direction in directions) {
                val point = Point(beam.r + 1, beam.c + direction)
                newActiveBeams.add(point) // Add a new beam to continue next loop
                timelineMap[point] = (timelineMap[point] ?: 0) + timelineMap[beam]!! // Update timeline map for the current cell to include any parent timelines for this beam
            }
        }
        activeBeams = newActiveBeams
    }

    // Count timelines at the final position of each beam
    val p2 = activeBeams.sumOf { timelineMap[it]!! }

    Util.printSolution(7, p1, p2)
}