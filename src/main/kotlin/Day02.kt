import util.Util

private fun main() {
    val input = Util.loadInputLines(2025, 2)[0].split(",").map { it.split("-")[0] to it.split("-")[1] }

    var p1 = 0L
    var p2 = 0L
    input.forEach { (f, l) ->
        val fi = f.toLong()
        val li = l.toLong()
        var id = fi
        while (id <= li) {
            val idStr = id.toString()

            // Part one
            if (idStr.length % 2 == 0) {
                if (idStr.take(idStr.length / 2) == idStr.takeLast(idStr.length / 2)) {
                    p1 += id
                }
            }

            // Part two
            val idChars = idStr.toList()

            // For each digit in the first half of the id
            for (i in 1..(idStr.length / 2)) {
                // Make sure it's a valid split
                if (idStr.length % i != 0) continue

                // Take the characters up to there
                val idSegment = idStr.take(i).toList()

                // Check each chunk against the initial segment
                if (idChars.chunked(i).all { it == idSegment }) {
                    p2 += id
                    break
                }
            }

            id++
        }
    }


    Util.printSolution(2, p1, p2)
}

// This was almost cool, but the decimal point 0s got left out of the string

//  if (cs.length % 2 == 0) {
//      // Split to a decimal number with the . in the center of the number
//      val x = c / (10.0.pow(cs.length / 2.0))
//
//      // Split around the . and compare halves
//      var halves = x.toString().split(".")
//      if (halves[0] == halves[1]) {
//          count += c
//      }
//  }