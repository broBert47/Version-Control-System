import java.io.File

fun main(){
    val word = "/Users/robertbosnjak/Downloads/text.txt"
    val reading = File(word).readText().split(" ").size
    println(reading)
}
