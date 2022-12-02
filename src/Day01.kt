import java.util.stream.Collectors
import java.util.stream.Stream

fun main() {
	fun part1(input: List<String>): Int {
		val inputWithDelimiter = Stream.concat(input.stream(), Stream.of("")).collect(Collectors.toList())
		var maxCalories = 0
		var elfCalories = 0
		
		inputWithDelimiter.forEach { foodCalorie ->
			if (foodCalorie.isEmpty()) {
				maxCalories = elfCalories.coerceAtLeast(maxCalories)
				elfCalories = 0
			} else {
				elfCalories += foodCalorie.toInt()
			}
		}
		
		return maxCalories
	}
	
	fun part2(input: List<String>): Int {
		val inputWithDelimiter = Stream.concat(input.stream(), Stream.of("")).collect(Collectors.toList())
		
		// sorted list of top three elves' carried calories
		val topThreeElvesCalories: MutableList<Int> = mutableListOf(0, 0, 0)
		
		var elfCalories = 0
		
		inputWithDelimiter.forEach { foodCalorie ->
			if (foodCalorie.isEmpty()) {
				
				// if this elf's calories are greater than the third-highest elf's
				if (elfCalories > topThreeElvesCalories[0]) {
					// remove minimum element
					topThreeElvesCalories.removeFirstOrNull()
					
					// binary search returns the index the given element would be at and negates it
					val naturalIndex = -topThreeElvesCalories.binarySearch(elfCalories, naturalOrder()) - 1
					
					topThreeElvesCalories.add(naturalIndex, elfCalories)
				}
				
				elfCalories = 0
			} else {
				elfCalories += foodCalorie.toInt()
			}
		}
		
		return topThreeElvesCalories.sum()
	}
}
