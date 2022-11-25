import UIKit
import Foundation


var greeting = "Hello, playground"
/*:
### TASK 1
 Write function for calculation of surface of triangle based on side and the height to this side.
 The inputed parameters must be from type Float.
 Provide example use of your function and print the result of the surface
*/

func calculateTriangleSurfaceArea(baseSide: Float, height: Float ) -> Float {
   return (baseSide*height)/2
}

/*:
 example:
-\
/| -\
/ |   -\
/  |     -\
/   |       -\
/     height:  -\
/      2.10       -\
/      |             -\
/       |               -\
----------------------------
        side: 10
*/
let surfaceTriangle = calculateTriangleSurfaceArea(baseSide: 10, height: 2.10)
print("------TASK 1------")
print(surfaceTriangle)

/*:
### TASK 2
 Write function for calculation of surface and perimeter of a circle based on the radius.
 The inputed parameters must be from type Float. Provide example use of your function and print the result of the surface.
*/

func calculateCircleSurfaceArea(radius: Float ) -> Float {
    return  Float.pi * pow(radius, 2)
}

let surfaceCircle = calculateCircleSurfaceArea(radius: 4)
print("------TASK 2------")
print(surfaceCircle)

/*:
### TASK 3
 Create a structure Car with parameters :
 Make(String), Model(String), Horse Power(Double), Torque(Float), Date Of Manufacturing (String).
 1. Make a function that accepts Car as parameter and print all the information as coma separated value (CSV) String.
 2. Make a function that accepts Car as parameter and prints the Power of the car in Watts (not in hps)
*/

struct Car {
    var Make: String
    var Model: String
    var HorsePower: Double
    var Torque: Float
    var DateOfManufacturing: String
    
    func CalculateWatts() -> Double{
        return 735.49875*self.HorsePower
    }
}


func printStructAsCsv<T>(data: T){
      var result = ""
    
      let mirror = Mirror(reflecting: data)

      guard let style = mirror.displayStyle, style == .struct || style == .class else {
          return
      }
    

      for (_,val) in mirror.children {
           result.append("\(val),")
      }
    
    if (result.isEmpty){
        print("Struct could not be printed as CSV")
    } else {
        print(result.dropLast()) // HACK to remove last comma
    }

}

func printCarPowerInWatts(c: Car) {
    print("\(c.CalculateWatts()) Watts")
}

var c = Car(Make: "Toyota", Model: "Corolla", HorsePower: 169.0, Torque: 151, DateOfManufacturing: "2021")

print("------TASK 3------")
printStructAsCsv(data: c)
printCarPowerInWatts(c: c)
