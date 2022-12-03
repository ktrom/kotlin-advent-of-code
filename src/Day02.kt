import java.util.NoSuchElementException

fun main() {
	fun part1(matchups: List<List<Shape>>): Int {
		
		// aggregates points earned from all matchups
		val score: List<Int> = matchups.map { matchup ->
			val opponentShape: Shape = matchup[0]
			val myShape: Shape = matchup[1]
			
			// get score earned for using our shape!
			val shapeScore: Int = ShapeScore.score[myShape]!!
			
			// get score based on outcome of the round
			val outcome: Outcome = RoundOutcomes.roundOutcomes[myShape]!![opponentShape]!!
			val outcomeScore: Int = OutcomeScore.score[outcome]!!
			
			// add our two scores to determine our total score for the round
			shapeScore + outcomeScore
		}
		
		return score.sum()
	}
	
	fun part1(input: List<String>): Int {
		
		// parses string input into a list of competing shapes
		val roundMatchups: List<List<Shape>> =
			input.map { matchup -> matchup.split(" ").map { getShapeAssociatedWithLetter(it) } }
		
		return part1(roundMatchups)
	}
	
	fun part2(input: List<String>): Int {
		val opposingShapesToDesiredOutcomes: List<Pair<Shape, Outcome>> =
			input.map { matchup ->
				val matchupInputs: List<String> = matchup.split(" ")
				Pair(
					getShapeAssociatedWithLetter(matchupInputs.get(0)),
					getOutcomeAssociatedWithLetter(matchupInputs.get(1))
				)
			}
		
		val matchups: List<List<Shape>> = opposingShapesToDesiredOutcomes.map { opposingShapeToDesiredOutcome ->
			val opposingShape: Shape = opposingShapeToDesiredOutcome.first
			val desiredOutcome: Outcome = opposingShapeToDesiredOutcome.second
			
			val desiredOutcomeForOpponent = getDesiredOutcomeForOpponent(desiredOutcome)
			
			val opponentOutcomes = RoundOutcomes.roundOutcomes[opposingShape]!!
			
			// filter to get the Pair<Shape, Outcome> which has our desired Outcome for the opponent's Shape, then use the key for our shape
			val ourShapeToEnemyOutcome =
				opponentOutcomes.entries.stream().filter { entry -> entry.value == desiredOutcomeForOpponent }
					.findFirst().orElseThrow { NoSuchElementException() }
			val shapeToUse = ourShapeToEnemyOutcome.key
			
			listOf(opposingShape, shapeToUse)
		}
		
		// aggregate score for all matchups
		return part1(matchups)
	}
	
	// test if implementation meets criteria from the description
	val testInput = readInput("Day02_test")
	println(part1(testInput) == 15)
	
	println(part2(testInput) == 12)
	
	val input = readInput("Day02")
	println(part1(input))
	println(part2(input))
}

// returns the RPS Shape associated with letters in the Secret Strategy Guide!
private fun getShapeAssociatedWithLetter(letter: String): Shape {
	if (letter == "A" || letter == "X") {
		return Shape.ROCK
	}
	if (letter == "B" || letter == "Y") {
		return Shape.PAPER
	}
	if (letter == "C" || letter == "Z") {
		return Shape.SCISSORS
	}
	
	throw IllegalArgumentException(letter + " is not a valid entry for competition")
}

private fun getOutcomeAssociatedWithLetter(letter: String): Outcome {
	if (letter == "X") {
		return Outcome.LOSE
	}
	if (letter == "Y") {
		return Outcome.TIE
	}
	if (letter == "Z") {
		return Outcome.WIN
	}
	
	throw IllegalArgumentException(letter + " is not a valid entry for competition")
}

// given the desired outcome for the player, returns our desired outcome for the opponent
private fun getDesiredOutcomeForOpponent(desiredOutcome: Outcome): Outcome {
	if (desiredOutcome == Outcome.LOSE) {
		return Outcome.WIN
	}
	if (desiredOutcome == Outcome.WIN) {
		return Outcome.LOSE
	}
	return Outcome.TIE
}

private enum class Shape {
	ROCK,
	PAPER,
	SCISSORS
}

private enum class Outcome {
	LOSE,
	TIE,
	WIN
}

/**
 * This class can be used to determine the outcome of a Rock-Paper-Scissors round given the player's [Shape] and the enemy's [Shape]
 */
private class RoundOutcomes {
	companion object {
		private val rockOutcomes: Map<Shape, Outcome> =
			mapOf(Shape.ROCK to Outcome.TIE, Shape.PAPER to Outcome.LOSE, Shape.SCISSORS to Outcome.WIN)
		private val paperOutcomes: Map<Shape, Outcome> =
			mapOf(Shape.ROCK to Outcome.WIN, Shape.PAPER to Outcome.TIE, Shape.SCISSORS to Outcome.LOSE)
		private val scissorsOutcomes: Map<Shape, Outcome> =
			mapOf(Shape.ROCK to Outcome.LOSE, Shape.PAPER to Outcome.WIN, Shape.SCISSORS to Outcome.TIE)
		
		// To get the outcome of a Rock-Paper-Scissors round, call roundOutcomes.get(<Player's Shape>).get(<Opponent's Shape>)
		val roundOutcomes: Map<Shape, Map<Shape, Outcome>> =
			mapOf(Shape.ROCK to rockOutcomes, Shape.PAPER to paperOutcomes, Shape.SCISSORS to scissorsOutcomes)
	}
}

private class ShapeScore {
	companion object {
		// Maps shapes to the score earned when used
		val score: Map<Shape, Int> = mapOf(Shape.ROCK to 1, Shape.PAPER to 2, Shape.SCISSORS to 3)
	}
}

private class OutcomeScore {
	// Maps outcomes to the resulting score earned
	companion object {
		val score: Map<Outcome, Int> = mapOf(Outcome.LOSE to 0, Outcome.TIE to 3, Outcome.WIN to 6)
	}
}
