fun main() {
    fun part1(input: List<String>): Int {
        val trees: MutableList<MutableList<Tree>> = mutableListOf()

        input.forEachIndexed { y, it ->
            trees.add(it.toCharArray().mapIndexed { x, char -> Tree(char.code, x, y) }.toMutableList())
        }

        return trees.map { row ->
            row.map { tree ->
                isVisible(tree, trees)
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description
    val testInput = readInput("Day08_test")
    println(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))

    println(part2(testInput) == 24933642)
    println(part2(input))
}
fun isVisible(tree: Tree, trees: List<List<Tree>>): Int {
    Direction.values().forEach { direction ->
            if (tree.height > tree.getTallestInDirection(trees, direction)) {
                return 1
            }
    }
    return 0
}
class Tree(val height: Int, val xPos: Int, val yPos: Int){
    var tallestTree: MutableMap<Direction, Int> = mutableMapOf(
        Direction.NORTH to -1,
        Direction.EAST to -1,
        Direction.SOUTH to -1,
        Direction.WEST to -1
    )

    fun getTallestInDirection(trees: List<List<Tree>>, direction: Direction):Int{
            val adjacentTreeY = yPos + yTranslation[direction]!!
            val adjacentTreeX = xPos + xTranslation[direction]!!

            if (isOutOfBounds(adjacentTreeY, adjacentTreeX, trees)) {
                return -1
            }

            val treeInDirection = trees[adjacentTreeY][adjacentTreeX]

            if(treeInDirection.tallestTree[direction] == -1){
                treeInDirection.getTallestInDirection(trees, direction)
            }

            tallestTree[direction] =
                treeInDirection.height.coerceAtLeast(treeInDirection.tallestTree[direction]!!)
            return tallestTree[direction]!!
    }

    override fun toString(): String {
        return height.toString()
    }

}

fun isOutOfBounds(adjacentTreeY: Int, adjacentTreeX: Int, trees: List<List<Tree>>): Boolean{
    return adjacentTreeY < 0 || adjacentTreeX < 0 || adjacentTreeY == trees.size || adjacentTreeX == trees[0].size
}

enum class Direction{
    NORTH,
    EAST,
    SOUTH,
    WEST
}

val yTranslation: Map<Direction, Int> = mapOf(
    Direction.NORTH to -1,
    Direction.EAST to 0,
    Direction.SOUTH to 1,
    Direction.WEST to 0
)

val xTranslation: Map<Direction, Int> = mapOf(
    Direction.NORTH to 0,
    Direction.EAST to 1,
    Direction.SOUTH to 0,
    Direction.WEST to -1
)

