open class GameExceptions(message: String) : Exception(message)
object WrongCommandException: GameExceptions("Место занято! Выберите другие координаты.")
object NoCellException: GameExceptions("Неверные координаты! Внимательно посмотрите условия")