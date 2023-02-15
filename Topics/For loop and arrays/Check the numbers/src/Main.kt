import java.lang.Exception

fun main() {
    val n = readLine()!!.toInt()
    val arr = IntArray(n)
    for (i in 0 until n) {
        arr[i] = readLine()!!.toInt()
    }
    val (p, m) = readLine()!!.split(" ").map { it.toInt() }
    var result = "YES"
    for (i in 0 until n - 1) {
        if (arr[i] == p && arr[i + 1] == m || arr[i] == m && arr[i + 1] == p) {
            result = "NO"
            break
        }
    }
    println(result)
}

/*

  val size = readln().toInt()
    val array = IntArray(size)

    for (i in array) {
        array[i] = readln().toInt()
    }

    val input = readln().split(" ")

    val first = input[0].toInt()
    val second = input[1].toInt()

    var result = "YES"
    for (i in array.indices) {
            if (array[i] == first && array[i + 1] == second) {
                result = "NO"
                break
            }
            if (array[i] == second && array[i + 1] == first) {
                result = "NO"
                break
            }
    }

    println(result)
 */