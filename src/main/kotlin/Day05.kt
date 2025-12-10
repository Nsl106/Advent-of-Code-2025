import util.Util

private fun main() {
    val input = Util.loadInputLines(2025, 5)

    val fresh = input.takeWhile { it.isNotBlank() }.map { it.split("-").let { (a, b) -> LongRange(a.toLong(), b.toLong()) } }
    val available = input.takeLastWhile { it.isNotBlank() }.map { it.toLong() }

    var p1 = 0
    var p2 = 0L

    for (a in available) {
        if (fresh.any { a in it }) p1++
    }

    var currentRanges = fresh.toMutableSet()

    // pt 2 works 500/500 of the time with my input
    var i = 0
    do {
        i++
        val mergedRanges = mutableSetOf<LongRange>()
        for (freshRange in currentRanges) {
            val containers = mergedRanges.filter { it.contains(freshRange.first) || it.contains(freshRange.last) }

            if (containers.isEmpty()) {
                mergedRanges.add(freshRange)
            } else {
                mergedRanges.removeAll(containers.toSet())

                mergedRanges.add(
                    LongRange(
                        (containers.map { it.first } + freshRange.first).min(),
                        (containers.map { it.last } + freshRange.last).max()
                    )
                )
            }
        }
        currentRanges = mergedRanges.shuffled().toMutableSet()
    } while (i < 20)

    p2 = currentRanges.sumOf { (it.last - it.first) + 1 }

    Util.printSolution(5, p1, p2)
}