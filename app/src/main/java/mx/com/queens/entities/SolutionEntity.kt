package mx.com.queens.entities

class SolutionEntity {
    var numSolution:Int = 0
    var solution:Int = 0
    var x:Int = 0
    var y:Int = 0

    constructor(numSolution: Int, solution: Int, x: Int, y: Int) {
        this.numSolution = numSolution
        this.solution = solution
        this.x = x
        this.y = y
    }

    override fun toString(): String {
        return "SolutionEntity(numSolution='$numSolution', " +
                "solution='$solution', " +
                "x='$x', " +
                "y=$y)"
    }

}