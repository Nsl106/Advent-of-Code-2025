import util.Util
import kotlin.math.abs
import kotlin.math.hypot

private fun main() {
    val demoInput = """
    162,817,812
    57,618,57
    906,360,560
    592,479,940
    352,342,300
    466,668,158
    542,29,236
    431,825,988
    739,650,466
    52,470,668
    216,146,977
    819,987,18
    117,168,530
    805,96,715
    346,949,466
    970,615,88
    941,993,340
    862,61,35
    984,92,344
    425,690,689
    """.trimIndent().lines()

    val input =
        Util.loadInputLines(2025, 8)
//        demoInput
            .map { it.split(",").map { it.toInt() } }

    var p1 = 0
    var p2 = 0

    // Build connections map
    val connections = mutableSetOf<Pair<Set<List<Int>>, Double>>()
    for (box in input) {
        val boxDistances = input
            .filter { it != box } // Remove duplicate
            .associateWith { // Build a map of each other cell to its distance from the current box
                // 3d distance
                hypot(hypot(abs(box[0] - it[0]).toDouble(), abs(box[1] - it[1]).toDouble()), abs(box[2] - it[2]).toDouble())
            }
            .toList() // To list of Pair<boxes, distance> (from .associateWith's map)
            .sortedBy { it.second } // Sort by distance
            .map { setOf(box, it.first) to it.second } // Build final list of connections to distance (including initial cell)


        // Add all distances to connection list
        connections.addAll(boxDistances)
    }

    // Build list of single-box circuits to start
    val circuits: MutableSet<Set<List<Int>>> = connections.map { it.first }.flatten().map { setOf(it) }.toMutableSet()

    // Iterate through best connections in order of closest to furthest
    for ((i, connection) in connections.sortedBy { (_, d) -> d }.withIndex()) {
        // Connected boxes (always 2) and the distance between them
        val (boxes, dist) = connection

        // Find existing circuits that contain one of the boxes
        val existingCircuits = circuits.filter { circuit -> boxes.any { circuit.contains(it) } }

        // Remove the existing circuits (if any) from the list and replace them with a combined version
        circuits.removeAll(existingCircuits)
        circuits.add((existingCircuits.flatten() + boxes).toSet())

        // On the 1000th best connection, calculate pt 1 result
        if (i == 1000) {
            p1 = circuits
                .map { it.size } // Only the circuit size matters
                .sortedDescending() // Largest circuits first
                .take(3) // Take three
                .fold(1) { acc, num -> acc * num } // Multiply
        }

        // When there is only one remaining circuit, calculate pt 2 result and end
        if (circuits.size == 1) {
            p2 = connection.first // Only the boxes matter, not the distance
                .map { it[0] } // Only X
                .let { it[0] * it[1] } // Multiply!
            break
        }
    }


    Util.printSolution(8, p1, p2)
}