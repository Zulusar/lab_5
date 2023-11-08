import java.lang.NumberFormatException
import kotlin.String

fun main() {
    println(
        "Доброго времени суток! Если Вы хотите поиграть в Крестики-Нолики - введите цифру 1, если в Балду - введите 2"
    )
    val xO =
        """Вы выбрали Крестики-Нолики. Правила просты: введите цифры от 0 до 2 через пробел, обозначая координаты, если хотите сделать ход.
        Если хотите откатить игру - введите 3 и количество ходов, на которое желаете вернуться.
        Приятной игры.
    """.trimMargin()
    val balda =
        """Вы выбрали игру Балда. Правила просты: введите 1 слово (не более 5 букв), после чего вводите цифры от 0 до 4, символ, который добавляете
        и слово, которое вы подразумевали. Кто наберт больше очков, тот и выиграл.
        Приятной игры, желаю удачи.
    """.trimMargin()
    val choose: Int = readln().toInt()
    var enterData: String?

    if (choose == 1) {
        println(xO)
        val string1 = "         "
        val cells: Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())
        val field = Board(cells, string1)
        val condition = EStateXO(field)
        var coordinates: Step
        val allGamesXO = MultiGame(condition)
        var enterDataForXO: List<String>?
        var iter = 0

        do {
            println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
            enterData = readln()
            try {
                Input.parse(enterData)
            } catch (e: NumberFormatException) {
                println("Проверте правильность данных!")
                enterData = readln()
            }
            when (Input.parse(enterData)) {
                is Exit -> {
                    println("Вы ввели команду на выход. До свидания!")
                    return
                }

                is TakeBack -> {
                    enterDataForXO = enterData.split(' ')
                    field.cells = allGamesXO.states[enterDataForXO[1].toInt()].board.clone
                    allGamesXO.states.clear()
                    field.printField()
                    when (iter % 2) {
                        0 -> iter = enterDataForXO[1].toInt()
                        1 -> iter = enterDataForXO[1].toInt() + 1
                    }
                    println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
                    enterData = readln()
                    enterDataForXO = enterData.split(' ')
                    coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
                }

                else -> {
                    enterDataForXO = enterData.split(' ')
                    coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
                }
            }
            try {
                condition.checkStep(coordinates)
            } catch (e: WrongCommandException)  {
                println(e.message)
                enterData = readln()
                enterDataForXO = enterData.split(' ')
                coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
            } catch (e: NoCellException) {
                println(e.message)
                enterData = readln()
                enterDataForXO = enterData.split(' ')
                coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
            } catch (e: WrongStepException) {
                condition.step(coordinates)
                field.printField()
                println(e.message)
                return
            }
            allGamesXO.states.add(condition.copy(condition.copyMassive()))
            condition.step(coordinates)
            field.printField()
            condition.checkWin(field)
            if (condition.gameResult != null) {
                println(condition.gameResult)
                return
            }
            iter++
        } while (true)
    }

    if (choose == 2) {
        println(balda)
        val field = Board()
        val newField = field.rerecoding(field)
        var iter = 0
        val condition = EStateBalda(newField, iter)
        var coordinates = Step(0, 0)
        val allGamesBalda = MultiGame(condition)
        var enterDataForBalda: List<String>?
        val enterFirstWord = readln().toCharArray()
        for (i in newField.cells[2].indices) {
            newField.cells[2][i] = enterFirstWord[i]
        }
        newField.printField()

        do {
            println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
            enterData = readln()

            try {
                Input.parse(enterData)
            } catch (e: Exception) {
                println("Проверте правильность данных!")
                enterData = readln()
            }
            when (Input.parse(enterData)) {
                is Exit -> {
                    println("Вы ввели команду на выход. До свидания!")
                    condition.checkWin()
                    println(condition.gameResult)
                    return
                }

                is TakeBack -> {
                    enterDataForBalda = enterData.split(' ')
                    newField.cells = allGamesBalda.states[enterDataForBalda[1].toInt()].board.cells
                    allGamesBalda.states.clear()
                    newField.printField()
                    when (iter % 2) {
                        0 -> iter = enterDataForBalda[1].toInt()
                        1 -> iter = enterDataForBalda[1].toInt() + 1
                    }
                    println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
                    enterData = readln()
                    enterDataForBalda = enterData.split(' ')
                    coordinates = Step(
                        x = enterDataForBalda[0].toInt(),
                        y = enterDataForBalda[1].toInt(),
                        param = listOf(enterDataForBalda[2], enterDataForBalda[3])
                    )
                }

                else -> {
                    enterDataForBalda = enterData.split(' ')
                    try {
                        coordinates.checkData(enterDataForBalda)
                    } catch (e: Exception) {
                        println("Проверьте правильность данных")
                        enterData = readln()
                        enterDataForBalda = enterData.split(' ')
                    }
                    coordinates = Step(
                        x = enterDataForBalda[0].toInt(),
                        y = enterDataForBalda[1].toInt(),
                        param = listOf(enterDataForBalda[2], enterDataForBalda[3])
                    )
                }
            }

            try {
                condition.checkStep(coordinates)
            } catch (e: WrongCommandException) {
                println(e.message)
                enterData = readln()
                enterDataForBalda = enterData.split(' ')
                coordinates = Step(
                    x = enterDataForBalda[0].toInt(),
                    y = enterDataForBalda[1].toInt(),
                    param = listOf(enterDataForBalda[2], enterDataForBalda[3])
                )
            } catch (e: NoCellException) {
                println(e.message)
                enterData = readln()
                enterDataForBalda = enterData.split(' ')
                coordinates = Step(
                    x = enterDataForBalda[0].toInt(),
                    y = enterDataForBalda[1].toInt(),
                    param = listOf(enterDataForBalda[2], enterDataForBalda[3])
                )
            } catch (e: WrongStepException) {
                condition.step(coordinates)
                newField.printField()
                println(e.message)
                return
            }
            allGamesBalda.states.add(condition.copy(condition.copyMassive()))
            condition.step(coordinates)
            newField.printField()
            iter++
        } while (true)
    }
}

