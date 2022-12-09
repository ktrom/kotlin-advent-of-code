fun main() {
    fun part1(input: List<String>): Int {
        return getStartWithDistinctMarker(input.get(0), 4)
    }

    fun part2(input: List<String>): Int {
        return getStartWithDistinctMarker(input.get(0), 14)
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day06_test")
    println(part1(testInput) == 5)

    val input = readInput("Day06")
    println(part1(input))

    println(part2(testInput) == 23)
    println(part2(input))
}

fun getStartWithDistinctMarker(datastream: String, markerSize: Int): Int{
    val precedingCharacters: MutableList<Char> = datastream.subSequence(0, markerSize).toMutableList()
    var pos = markerSize

    datastream.substring(markerSize).forEach {
        if(precedingCharacters.distinct().size == markerSize){
            return pos
        }

        precedingCharacters.set(pos % markerSize, it)
        pos++
    }

    return -1
}
