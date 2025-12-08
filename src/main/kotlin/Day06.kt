import util.Util
import util.Util.flipAxis

private fun main() {
    val demoInput = """
    123 328  51 64 
     45 64  387 23 
      6 98  215 314
    *   +   *   +  
    """.trimIndent().lines()

    val input =
        Util.loadInputLines(2025, 6)
//        demoInput



    // run {} blocks to allow variables with the same name in each part

    // Part One
    var p1 = 0L
    run {
        // Input to columns, split around whitespace
        val problems = input.map { it.split(" +".toRegex()).filter(String::isNotEmpty) }.flipAxis()

        for (p in problems) {
            val nums = p.dropLast(1)
            val operator = p.last()

            when (operator) {
                "+" -> p1 += nums.sumOf { it.toLong() }
                "*" -> p1 += nums.fold(1L) { acc, num -> acc * num.toLong() }
            }
        }
    }

    // Part Two
    var p2 = 0L
    run {
        // Convert inputs into columns
        var problems = input.map { it.toList() }.flipAxis()


        while (problems.isNotEmpty()) {
            // Take until a line of full whitespace to separate out each problem
            val lines = problems.takeWhile { !it.all(Char::isWhitespace) }
            problems = problems.drop(lines.size + 1)

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
    }



    Util.printSolution(6, p1, p2)
}