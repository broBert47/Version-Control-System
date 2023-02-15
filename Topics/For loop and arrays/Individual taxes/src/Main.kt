fun main() {
    val n = readLine()!!.toInt()
    val incomes = DoubleArray(n)
    val taxes = DoubleArray(n)
    for (i in 0 until n) {
        incomes[i] = readLine()!!.toDouble()
    }
    for (i in 0 until n) {
        taxes[i] = readLine()!!.toDouble()
    }
    var maxTax = 0.0
    var company = -1
    for (i in 0 until n) {
        val tax = incomes[i] * taxes[i] / 100
        if (tax >= maxTax) {
            maxTax = tax
            company = i + 1
        }
    }
    println(company)
}