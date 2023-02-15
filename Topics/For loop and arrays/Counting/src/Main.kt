fun main() {
    val n = readLine()!!.toInt()
    val numbers = IntArray(n)
    for (i in 0 until n) {
        numbers[i] = readLine()!!.toInt()
    }
    val m = readLine()!!.toInt()
    var count = 0
    for (i in 0 until n) {
        if (numbers[i] == m) {
            count++
        }
    }
    println(count)
}