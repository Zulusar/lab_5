open class Board(var cells: Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())) : Input {
    var iter = 0

    companion object {
        var i = 0
        var mass = emptyArray<Char>()
        var size = 3
        fun stringToArray(string: String): Array<Char> {
            while (i < size) {
                mass += string.substring(0..size)[i]
                i++
            }
            return mass
        }
    }

    var clone: Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())

    constructor(cells: Array<Array<Char>>, string: String) : this(cells) {
        for (i in cells.indices) {
            cells[i] += stringToArray(string)
        }
    }

    fun printField() {
        for (row in cells) println(row.contentToString())
    }

    open fun getOrNull(point: Point): Unit? {
        return if (point.x > size || point.y > size)
            println("Ошибка!")
        else null
    }

    operator fun get(point: Point): Char {
        return if (iter % 2 == 0) 'X'
        else '0'
    }

    open fun setAndCopy(point: Point, c: Char) {
        cells[point.x][point.y] = c
        iter++
    }

    open fun check(): Unit? {
        var a = 9
        for (row in cells) {
            for (cell in row) {
                if (cell != ' ') a--
            }
        }
        return if (a != 1)
            null
        else {
        }
    }

    fun newCheck(): Unit?{
        var a = 24
        for (row in cells) {
            for (cell in row) {
                if (cell != ' ') a--
            }
        }
        return if (a != 0)
            null
        else {
        }
    }

    open fun stepOnTheBoard(point: Point): Boolean {
        return cells[point.x][point.y] == ' '
    }

    fun rerecoding(board: Board = Board()): Board {
        val string1 = "         "
        board.cells = arrayOf(emptyArray(), emptyArray(), emptyArray(), emptyArray(), emptyArray())
        board.clone = arrayOf(emptyArray(), emptyArray(), emptyArray(), emptyArray(), emptyArray())
        size = 5
        val emptyArrayForField: Array<Array<Char>> =
            arrayOf(emptyArray(), emptyArray(), emptyArray(), emptyArray(), emptyArray())
        for (i in emptyArrayForField.indices) {
            emptyArrayForField[i] = board.clone[i].clone()
        }
        return Board(emptyArrayForField, string1)
    }
}
