class EStateXO(board: Board = Board()) : EAbstractState(board), Input {

    override val gameResult: String?
        get() = if (checkWin(board) == 'N') null
        else "Win ${checkWin(board)}"

    fun copyMassive(): Array<Array<Char>> {
        val copyMassiv: Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())
        for (i in this.board.cells.indices) {
            copyMassiv[i] = this.board.cells[i].clone()
        }
        return copyMassiv
    }

    override fun copy(z: Array<Array<Char>>): EStateXO {
        val state = EStateXO()
        for (i in state.board.clone.indices) {
            state.board.clone[i] = z[i].clone()
        }
        return state
    }

    override fun step(step: Step): EAbstractState {
        this.board.setAndCopy(Point(step.x, step.y), board[Point(step.x, step.y)])
        return nextState(step)
    }

    override fun checkStep(step: Step): Boolean {
        if (board.getOrNull(Point(step.x, step.y)) != null) {
            throw NoCellException
        }
        if (!board.stepOnTheBoard(Point(step.x, step.y))) {
            throw WrongCommandException
        }
        if (board.check() != null) {
            throw WrongStepException("Места закончились! Добро пожаловать отсюда!")
        }
        else return true
    }

    override fun nextState(step: Step): EStateXO {
        return copy(board.cells)
    }


    fun checkWin(board: Board): Char {//проверка на выигрыш
        var a = 0//счетчик
        val winLines = arrayOf(//выигрышные комбинации
            arrayOf(arrayOf(0, 0), arrayOf(0, 1), arrayOf(0, 2)),
            arrayOf(arrayOf(1, 0), arrayOf(1, 1), arrayOf(1, 2)),
            arrayOf(arrayOf(2, 0), arrayOf(2, 1), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(2, 0)),
            arrayOf(arrayOf(0, 1), arrayOf(1, 1), arrayOf(2, 1)),
            arrayOf(arrayOf(0, 2), arrayOf(1, 2), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 0), arrayOf(1, 1), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 2), arrayOf(1, 1), arrayOf(2, 0))
        )

        for (lines in winLines) {
            val coord1 = lines[0]
            val coord2 = lines[1]
            val coord3 = lines[2]
            if (board.cells[coord1[0]][coord1[1]] == board.cells[coord2[0]][coord2[1]] && board.cells[coord1[0]][coord1[1]] == board.cells[coord3[0]][coord3[1]] && board.cells[coord1[0]][coord1[1]] == '0') {
                a += 3//увеличение счетчика для корректного определния победителя
            }
            if (board.cells[coord1[0]][coord1[1]] == board.cells[coord2[0]][coord2[1]] && board.cells[coord1[0]][coord1[1]] == board.cells[coord3[0]][coord3[1]] && board.cells[coord1[0]][coord1[1]] == 'X') {
                a += 2
            }
        }
        if (a == 3) return '0'
        return if (a == 2) 'X'
        else 'N'
    }
}

