fun main() {
	fun part1(input: List<String>): Int {
		return input.map { line ->
			val intervals: List<IntRange> = transformIntoIntervals(line)
			
			isSubInterval(intervals[0], intervals[1]) ||
					isSubInterval(intervals[1], intervals[0])
		}.sumOf {
			var hasSubInterval = 0
			if (it) {
				hasSubInterval = 1
			}
			hasSubInterval
		}
	}
	
	fun part2(input: List<String>): Int {
		return input.map { line ->
			val intervals: List<IntRange> = transformIntoIntervals(line)
			
			overlap(intervals[0], intervals[1])
		}.sumOf {
			var hasOverlap = 0
			if (it) {
				hasOverlap = 1
			}
			hasOverlap
		}
	}
	
	// test if implementation meets criteria from the description
	val testInput = readInput("Day04_test")
	println(part1(testInput) == 2)
	
	val input = readInput("Day04")
	println(part1(input))
	
	println(part2(testInput) == 4)
	println(part2(input))
}

// takes a string of the form "x1-y1, x2-y2" and returns list of corresponding
// int ranges, e.g. [[x1, y1 + 1], [x2, y2 + 1]]
fun transformIntoIntervals(line: String): List<IntRange> {
	val ranges: List<String> = line.split(",")
	
	val intervals: List<IntRange> = ranges.map {
		val endpoints: List<Int> = it.split("-").map { it.toInt() }
		
		endpoints[0] until endpoints[1] + 1
	}
	
	return intervals
}

// returns true if candidateSubInterval is a contained in interval
fun isSubInterval(candidateSubInterval: IntRange, interval: IntRange): Boolean {
	return interval.first <= candidateSubInterval.first && candidateSubInterval.last <= interval.last
}

// returns true if the two ranges overlap
fun overlap(range: IntRange, other: IntRange): Boolean {
	if (range.first < other.first) {
		if (range.last >= other.first) {
			return true
		}
	}
	if (other.first < range.first) {
		if (other.last >= range.first) {
			return true
		}
	}
	
	return isSubInterval(range, other) || isSubInterval(other, range)
}
