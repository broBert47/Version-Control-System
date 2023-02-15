fun main() {
    //Do not touch code below
    var inputArray: Array<Array<String>> = arrayOf()
    val n = readLine()!!.toInt()
    for (i in 0 until n) {
        val strings = readLine()!!.split(' ').toTypedArray()
        inputArray += strings
    }
    //write your code here

    println("${inputArray.first().first()} ${inputArray.first().last()}")
    println("${inputArray.last().first()} ${inputArray.last().last()}")

    /*
    val rows = inputArray.size
    val columns = inputArray[0].size

    // Check if there's only one column
    if (columns == 1) {
        print("${inputArray[0][0]} ${inputArray[rows - 1][0]}")
        println()
    } else if (rows == 1) {
        print("${inputArray[0][0]} ${inputArray[0][columns - 1]}")
        println()
    }else{
        // Print the first and last elements of the first and last row
        for (i in 0 until rows) {
            if (i == 0 || i == rows - 1) {
                print("${inputArray[i][0]} ${inputArray[i][columns - 1]}")
                println()
            }
        }
    }

     */
}