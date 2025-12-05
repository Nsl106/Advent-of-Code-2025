import util.Util
import kotlin.math.pow

private fun main() {
    val demoInput = """
    987654321111111
    811111111111119
    234234234234278
    818181911112111
    """.trimIndent().lines()

    val input =
        Util.loadInputLines(2025, 3)
//        demoInput
            .map { it -> it.map { it.digitToInt() } }

    var p1 = 0
    var p2 = 0L


    for (ln in input) {
        val first = ln.dropLast(1).max()
        val second = ln.dropWhile { it != first }.drop(1).max()
        p1 += first * 10 + second
    }

    for (ln in input) {
        var remainder = ln

        for (i in 12 downTo 1) {
            val num = remainder.dropLast(i - 1).max()
            remainder = remainder.dropWhile { it != num }.drop(1)
            p2 += num * 10.0.pow(i - 1).toLong()
        }
    }

    Util.printSolution(3, p1, p2)
}