import java.util.Scanner

fun main() {
    // get directories smaller than given size
    fun getDirectories(input: List<String>, size: Int): Int {
        val root: Node = getSystemTree(input)

       return getDirectories(root, size, Condition.LESS_THAN).sumOf { it.size }
    }

    fun part1(input: List<String>): Int {
        return getDirectories(input, 100000)
    }

    fun part2(input: List<String>): Int {
        val root: Node = getSystemTree(input)
        val totalSpace = 70000000

        // sure, this will have literally every node, but i've got to get to sleep :p
        val allNodes: List<Node> = getDirectories(root, 1, Condition.GREATER_THAN)

        val availableSpace = totalSpace - root.size
        val necessarySpace = 30000000 - availableSpace

        return allNodes.filter { it.size > necessarySpace }.minBy { it.size }.size
    }


    // test if implementation meets criteria from the description
    val testInput = readInput("Day07_test")
    println(part1(testInput) == 95437)

    val input = readInput("Day07")
    println(part1(input))

    println(part2(testInput) == 24933642)
    println(part2(input))
}

class Node(val name: String){
    var parent: Node? = null
    val children: MutableMap<String, Node> = mutableMapOf()
    var size = -1
}

fun getSystemTree(input: List<String>): Node{
    val fileSystemRoot = Node("fileSystem")
    val root = Node("/")

    fileSystemRoot.children[root.name] = root
    root.parent = fileSystemRoot

    var currentNode: Node? = fileSystemRoot

    // set up node tree
    input.forEach {
        val reader = Scanner(it.reader())
        val firstValue: String = reader.next()

        if(firstValue == "$"){
            val cmd: String = reader.next()

            if(cmd == "cd"){
                val nextDirName: String = reader.next()

                currentNode = if(nextDirName == ".."){
                    currentNode!!.parent
                } else{
                    currentNode!!.children[nextDirName]
                }
            }
        }
        else if(firstValue == "dir"){
            val dirName: String = reader.next()
            val childNode =  Node(dirName)
            currentNode!!.children[dirName] = childNode
            childNode.parent = currentNode
        }

        else{
            val fileSize = firstValue.toInt()
            val fileName = reader.next()

            val fileNode = Node(fileName)
            fileNode.size = fileSize

            currentNode!!.children[fileName] = fileNode
            fileNode.parent = currentNode
        }
    }

    return root
}

// If this is called on a node, and that node has -1 size, this will calculate size
// returns all nodes greater than given size beneath node
fun getDirectories(node: Node, size: Int, condition: Condition): List<Node> {
    val nodesMeetingCondition = mutableListOf<Node>()

    if(node.size == -1){
        node.size = 0
       node.children.map {
           val childNode = it.value
           nodesMeetingCondition.addAll(getDirectories(childNode, size, condition))
           node.size += childNode.size
       }
    }

    val nodeSizeMeetsCondition =
        condition == Condition.GREATER_THAN && node.size > size
            || condition == Condition.LESS_THAN && node.size < size

    if(nodeSizeMeetsCondition && node.children.isNotEmpty()){
        nodesMeetingCondition.add(node)
    }

    return nodesMeetingCondition
}

enum class Condition{
    GREATER_THAN,
    LESS_THAN
}


