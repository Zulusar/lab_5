class EStateBalda(
    board: Board = Board(),
    var turn: Int = 1,
    val words1: ArrayList<String> = arrayListOf(),
    val words2: ArrayList<String> = arrayListOf()
) : EAbstractState(board) {

    fun copyMassive(): Array<Array<Char>> {
        val copyMassiv: Array<Array<Char>> = arrayOf(
            emptyArray(), emptyArray(), emptyArray(), emptyArray(),
            emptyArray()
        )
        for (i in this.board.cells.indices) {
            copyMassiv[i] = this.board.cells[i].clone()
        }
        return copyMassiv
    }

    override fun copy(z: Array<Array<Char>>): EStateBalda {
        val state = EStateBalda(board.rerecoding())
        for (i in state.board.cells.indices) {
            state.board.cells[i] = z[i].clone()
        }
        return state
    }

    override fun nextState(step: Step): EStateBalda {
        return copy(board.cells)
    }

    override fun checkStep(step: Step): Boolean {
        if (board.getOrNull(Point(step.x, step.y)) != null) {
            throw NoCellException
        }
        if (!board.stepOnTheBoard(Point(step.x, step.y))) {
            throw WrongCommandException
        }
        if (board.newCheck() != null) {
            throw WrongStepException("Места закончились! Добро пожаловать отсюда!")
        } else return true
    }

    override fun step(step: Step): EStateBalda {
        turn++
        when (turn % 2) {
            0 -> words1.add(step.param[1])
            1 -> words2.add(step.param[1])
        }
        val symbol: CharArray = step.param[0].toCharArray()
        this.board.setAndCopy(Point(step.x, step.y), symbol[0])
        return nextState(step)
    }


    fun checkWin(): String {
        return if (words1.size > words2.size) {
            return ("Win 1 player!")
        }
         else {
            ("Win 2 player!")
        }
    }

    override val gameResult: String
        get() = checkWin()
}