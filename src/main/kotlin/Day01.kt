import util.Util

// Does not work
private fun main() {
    val input = Util.loadInputLines(2025, 1)

    val lines = input.map { it.take(1) to it.drop(1).toInt() }

    var current = 50

    var sum = 0
    var sum2 = 0
    lines.forEach { (d, n) ->
        val initial = current
        var inc2 = n / 100

        when (d) {
            "L" -> {
                if (current < n % 100) inc2++
                current -= n % 100
                if (current < 0) current += 100
                if (current == 0 && inc2 > 0) inc2--
            }

            "R" -> {
                if (current > 100 - (n % 100)) inc2++
                current += n % 100
                if (current >= 100) current -= 100
                if (current == 0 && inc2 > 0) inc2--
            }
        }

        if (initial == 0 && inc2 != 0 && ((d == "R" && n < 100) || (d == "L"))) {
            inc2--
        }

        sum2 += inc2

        println("($initial -> $d$n -> $current) w/ $inc2 + ${current == 0}")

        if (current == 0) sum++
        if (current == 0) sum2++
    }
    println("--------")
    println(sum)
    println(sum2)

//    Util.printSolution(1, partOne, partTwo)
}