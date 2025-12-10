import util.Util

private fun main() {
    // Input to mutable char grid
    val input = Util.loadInputLines(2025, 4).map { line -> line.toCharArray().toMutableList() }.toMutableList()

    var p1 = 0
    var p2 = 0


    val rows = 0..input.lastIndex
    val cols = 0..input[0].lastIndex

    // Only add to part one on the initial count before things are removed
    var solvingPartOne = true

    while (true) {
        var removedInLoop = 0

        for (r in rows) for (c in cols) {
            // If we aren't looking at a paper, continue
            if (input[r][c] != '@') continue


            // Check neighbouring positions for papers
            var adjPapers = 0

            for ((rp, cp) in Util.gridDirectionsCardinalAndDiagonal) {
                val rTarget = r + rp
                val cTarget = c + cp

                // If target is in the grid and is a paper, add to the count
                val inGrid = rTarget in rows && cTarget in cols
                if (inGrid && input[rTarget][cTarget] == '@') {
                    adjPapers++
                }
            }

            // Count and/or remove accessible papers
            if (adjPapers < 4) {
                if (solvingPartOne) p1++

                input[r][c] = '.'
                removedInLoop++
            }
        }

        p2 += removedInLoop
        if (removedInLoop == 0) break

        solvingPartOne = false
    }


    Util.printSolution(4, p1, p2)
}