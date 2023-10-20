class TrainRoute(private val startCity: String, private val endCity: String) {
    fun getRoute() = "$startCity - $endCity"
}

class TrainCar(val capacity: Int)

class Train(private var route: TrainRoute) {
    val cars: MutableList<TrainCar> = mutableListOf()

    fun addCar(car: TrainCar) = cars.add(car)

    fun sendTrain(passengerCount: Int) {
        println("Поезд ${route.getRoute()}, состоящий из ${cars.size} вагонов с $passengerCount пассажирами, отправлен.")
        var remainingPassengers = passengerCount
        for ((index, car) in cars.withIndex()) {
            val passengerCountInCar = if (remainingPassengers > 0) {
                val passengersToAdd = minOf(car.capacity, remainingPassengers)
                remainingPassengers -= passengersToAdd
                passengersToAdd
            } else 0
            println("Вагон ${index + 1}: Вместимость - ${car.capacity}, Пассажиры - $passengerCountInCar")
        }
    }
}

fun main() {
    val cities = listOf(
        "Бийск", "Барнаул", "Новосибирск", "Томск", "Санкт-Петербург",
        "Москва", "Красноярск", "Тюмень", "Чита", "Хабаровск",
        "Владивосток", "Казань", "Самара", "Екатеринбург", "Пермь"
    )
    var passengerCount = 0
    var train: Train? = null
    var currentRoute: TrainRoute? = null
    while (true) {
        when (getInput("""
            Выберите действие:
            1 - Создать направление
            2 - Продать билеты
            3 - Сформировать поезд
            4 - Отправить поезд
            EXIT - Завершить работу
            
        """.trimIndent()).uppercase()) {
            "1" -> {
                val startCity = cities.random()
                var endCity = cities.random()
                while (endCity == startCity) {
                    endCity = cities.random()
                }
                currentRoute = TrainRoute(startCity, endCity)
                println("Направление создано: ${currentRoute.getRoute()}")
                train = Train(currentRoute)
            }
            "2" -> {
                passengerCount = (5..201).random()
                println("Продано билетов: $passengerCount")
            }
            "3" -> {
                if (train == null) {
                    println("Направление не создано")
                    continue
                }
                train.cars.clear()
                var passengersLeft = passengerCount
                while (passengersLeft > 0) {
                    val carCapacity = (5..25).random()
                    val car = TrainCar(carCapacity)
                    train.addCar(car)
                    passengersLeft -= carCapacity
                }
                println("Поезд сформирован: ${currentRoute!!.getRoute()}")
                println("Количество вагонов: ${train.cars.size}")
                for (index in train.cars.indices)
                    println("Вместимость вагона ${index + 1}: ${train.cars[index].capacity}")
            }
            "4" -> train?.sendTrain(passengerCount)
            "EXIT" -> {
                println("Работа программы завершена.")
                break
            }
            else -> println("Некорректный ввод. Попробуйте еще раз.")
        }
    }
}

fun getInput(prompt: String): String {
    print(prompt)
    return readln()
}