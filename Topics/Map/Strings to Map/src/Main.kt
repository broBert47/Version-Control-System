fun stringToMap(first: String, second: String, third: String): Map<String, Int> {

    val map = buildMap<String, Int> {
        put(first, first.length)
        put(second, second.length)
        put(third, third.length)
    }
    return map
}