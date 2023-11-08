abstract class EAbstractState(val board: Board) {

    abstract val gameResult: String?

    abstract fun copy(z: Array<Array<Char>>): EAbstractState

    open fun step(step: Step): EAbstractState {
        this.board.setAndCopy(Point(step.x, step.y), board[Point(step.x, step.y)])
        return nextState(step)
    }

    open fun checkStep(step: Step): Boolean {
        if (board.getOrNull(Point(step.x, step.y)) != null) {
            throw NoCellException
        }
        if (!board.stepOnTheBoard(Point(step.x, step.y))) {
            throw WrongCommandException
        }
        if (board.check() != null) {
            throw WrongStepException("Места закончились! Добро пожаловать отсюда!")
        } else return true
    }

    abstract fun nextState(step: Step): EAbstractState

}