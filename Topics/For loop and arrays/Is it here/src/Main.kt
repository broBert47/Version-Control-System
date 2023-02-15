fun main() {

    val size = readln().toInt()
    val array = IntArray(size)

    for (i in 0..array.lastIndex) {
        array[i] = readln().toInt()
    }

    val numerbM = readln().toInt()

    var count = 0
    for (i in array) {
        if(numerbM == i){
            count++
        }
    }

    if(count > 0){
        println("YES")
    }else {
        println("NO")
    }



}