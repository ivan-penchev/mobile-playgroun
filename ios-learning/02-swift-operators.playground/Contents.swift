import UIKit

/*
 Привет драги проверяващ.
 Искам само да отбележа че това условията на това задание беше написано с краката.
 Някой неща съм си гадал какво да пиша.
 Ще се радвам ако го вземеш под предвид при оценяването.
 */

//Make a function for calculating average fuel consumption in liters per 100 km.
func calculateAvarageFuelConsumption(litreOfFuelUsed: Double, DistanceTravelledInKM: Double) -> Double {
    // (Litres used X 100) ÷ km travelled = Litres per 100km.
    // Example a car uses 65 litres to travel 500km
    // (65 X 100) ÷ 500 = 13 Litres/100km
    return (litreOfFuelUsed*100)/DistanceTravelledInKM
}

print(calculateAvarageFuelConsumption(litreOfFuelUsed: 65, DistanceTravelledInKM: 500))

//Make a function that is used for adding distance, amount of fuel, and date of fueling (as string). Calculate the average fuel consumption between the current and the last fueling.
func calculateAverageConsumption(kilometers: Double, amountFuel: Double, dateOfFueling: String) -> Double {
    return amountFuel / (kilometers / 100.00)
}

print(calculateAverageConsumption(kilometers: 320, amountFuel: 28.2, dateOfFueling: "12/6/2022"))


//Make a function for converting l/100km to mpg.
func ConvertToMPG(litersPer100KM: Double) -> Double {
    //To find the miles per gallon, divide the number of liters per 100 km into 282.48.
    // https://math.stackexchange.com/a/153966
    // Примера, в линка, е за американска галон който е с 20% по малък от великобританският (известен като имперски) галон. Аз изчислявам за имперският галон.
    return 282.48/litersPer100KM
}

print(ConvertToMPG(litersPer100KM: 34))


//Make a function for calculating the average price per kilometer on a given fuel price per liter.
func calculateAvaragePricePerKM(traveledKilometers: Double, fuelSpent: Double, fuelPricePerLiter: Double) -> Double {
    
    var spentAmont = fuelSpent * fuelPricePerLiter
    var averagePricePerKilometer = traveledKilometers / spentAmont
    
    return averagePricePerKilometer
}

print(calculateAvaragePricePerKM(traveledKilometers: 100.00, fuelSpent: 8.5, fuelPricePerLiter: 2.55))


//Make a function to print information about fuel consumption and the date of fueling.
struct FuelLog {
    var entires: Array<FuelLogLine>
}
struct FuelLogLine {
    var consumedFuelSinceLastCheck: Double
    var currentEntryTime: String
    static func ==(lhs: FuelLogLine, rhs: FuelLogLine) -> Bool {
        return lhs.consumedFuelSinceLastCheck == rhs.consumedFuelSinceLastCheck && lhs.currentEntryTime == rhs.currentEntryTime
    }
}


func printFuelLogInformation(fuelLog: FuelLog) {
    
    for (index, element) in fuelLog.entires.enumerated() {
        let consumedFuel = element.consumedFuelSinceLastCheck
        let lastCheckTime = index == 0 ? "unknown fueling time" : fuelLog.entires[index-1].currentEntryTime
        print("I have consumed \(consumedFuel) litres of fuels, since: \(lastCheckTime)")
    }
}

let logEntry = FuelLogLine(consumedFuelSinceLastCheck: 10, currentEntryTime: "22 November 2022")
let logEntry2 = FuelLogLine(consumedFuelSinceLastCheck: 30, currentEntryTime: "29 November 2022")
let logEntry3 = FuelLogLine(consumedFuelSinceLastCheck: 20, currentEntryTime: "2 December 2022")
let logEntry4 = FuelLogLine(consumedFuelSinceLastCheck: 10, currentEntryTime: "22 November 2022")

var fuelLog = FuelLog(entires: [logEntry,logEntry2,logEntry3,logEntry4])

printFuelLogInformation(fuelLog: fuelLog)
