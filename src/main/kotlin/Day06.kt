import util.Util
import util.Util.flipAxis

private fun main() {
    val input = Util.loadInputLines(2025, 6)

    // Part One
    var p1 = 0L
    // Input to columns, split around whitespace
    val pt1Problems = input.map { it.split(" +".toRegex()).filter(String::isNotEmpty) }.flipAxis()

    for (p in pt1Problems) {
        val nums = p.dropLast(1)
        val operator = p.last()

        when (operator) {
            "+" -> p1 += nums.sumOf { it.toLong() }
            "*" -> p1 += nums.fold(1L) { acc, num -> acc * num.toLong() }
        }
    }

    // Part Two
    var p2 = 0L
    // Convert inputs into columns
    var pt2Problems = input.map { it.toList() }.flipAxis()

    while (pt2Problems.isNotEmpty()) {
        // Take until a line of full whitespace to separate out each problem
        val lines = pt2Problems.takeWhile { !it.all(Char::isWhitespace) }
        pt2Problems = pt2Problems.drop(lines.size + 1)

        // Get operator and numbers to add
        val operator = lines.first().last()
        val nums = lines.map { it.dropLast(1) }
            .map {
                it.filterNot(Char::isWhitespace)
                    .joinToString("")
                    .toLong()
            }


        when (operator) {
            '+' -> p2 += nums.sum()
            '*' -> p2 += nums.fold(1L) { acc, num -> acc * num }
        }
    }

    Util.printSolution(6, p1, p2)
}