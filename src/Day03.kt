fun main() {
	fun part1(input: List<String>): Int {
		return input.map {
			val charsInFirstHalfOfString: Set<Char> = it.substring(0 until it.length / 2).toSet()
			val charsInSecondHalfOfString: Set<Char> = it.substring(it.length / 2).toSet()
			
			val charsInBothHalvesOfString: Set<Char> =
				charsInFirstHalfOfString intersect charsInSecondHalfOfString
			
			// This function assumes there is exactly one type of char in both halves
			check(charsInBothHalvesOfString.size == 1)
			
			getPriority(charsInBothHalvesOfString.first())
		}.sum();
	}
	
	/**
	 * 	1. takes a list of strings and chunks them by chunkSize.
	 * 	2. each chunk is a List<String>s whose charset's intersection is of size 1.
	 *  3. for each chunk, finds the common char, and calculuates its priority value using [getPriority]
	 *  4. sums priority values of common char for all each chunks
	 *
	 * 	@param chunkSize size of chunks with common char
	 */
	fun part2(input: List<String>, chunkSize: Int): Int {
		return input.chunked(chunkSize).map {
			var charsInAllStrings: Set<Char> = it.first().toSet()
			
			it.forEach { charsInAllStrings = charsInAllStrings intersect it.toSet() }
			
			charsInAllStrings
		}.map {
			check(it.size == 1)
			getPriority(it.first())
		}.sum()
	}
	
	// takes an list of strings and assumes chunk size of 3
	fun part2(input: List<String>): Int {
		return part2(input, 3)
	}
	
	// test if implementation meets criteria from the description
	val testInput = readInput("Day03_test")
	println(part1(testInput) == 157)
	
	val input = readInput("Day03")
	
	println(part1(input))
	
	println(part2(testInput) == 70)
	println(part2(input))
}


// returns the priority value of a food type as outlined in spec https://adventofcode.com/2022/day/3
fun getPriority(character: Char): Int {
	// lower case ASCII values begin at 97, so to map them to 1 - 26, we subtract 96
	if (character.isLowerCase()) {
		return character.code - 96
	}
	
	// uppercase case ASCII values begin at 65, so to map them to 27 - 52, we subtract 38
	return character.code - 38
}
