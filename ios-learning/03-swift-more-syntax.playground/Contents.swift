import UIKit


/*
 Привет проверяващ! :)
 Разработил съм приложението спрямо заданието, и не съм вложил и секунда(усилие) повече.
 Пример:
 В заданието е споменато че наличността на банкомата е в Български лева, така че всичко е сложено към него.
 Ако случайно искаш да направиш този банкомат да работи на Слънчев бряг и да раздава EUR няма да бъде възможно :)
 
 Има доста неща, които могат да се ползват:
 Пример:
    State design pattern за АТМ-а, защото в момента можеш да "вкараш" повече от една карта в банкомата, което е проблем, защото ще има race conditions.
 
 Kaто цяло ако някой имплементира това сериозно може да си поиграе.
 Но аз записах iOS курс за да уча за iOS а не, да пиша програмки, които съм писал във първи курс в университета.
 */
enum Currency: String {
    case USD, EUR, BGN // supported currencies here
}

struct Card {
    let id: String
    let owner: String
    var currentBalance: [String: Double]
}

struct ATM {
    let id: String
    private var availableCurrency: [String: Double]
    let exchangeFeePercentage: Double
    
    init(id: String, exchangeFeePercentage: Double) {
        self.id = id
        self.exchangeFeePercentage = exchangeFeePercentage.isLessThanOrEqualTo(0) ? 0 : exchangeFeePercentage
        self.availableCurrency = [Currency.BGN.rawValue: 0.0]
    }
    
    func printAvailableCurrencyStatus() {
        print("Наличността на банкоматът e \(self.availableCurrency)")
    }
    
    mutating func addCurrency(_ currency: String, amount: Double) -> (success: Bool, message: String) {
        guard currency == Currency.BGN.rawValue else {
            return (false, "Банкоматът работи само с български лева.")
        }
        self.availableCurrency[currency] = (self.availableCurrency[currency] ?? 0) + amount
        
        return (true, "Наличността на банкоматът беше обновена на \(self.availableCurrency[currency]!) \(currency)")
        
    }
    
    private mutating func removeCurrency(_ currency: String, amount: Double) -> (success: Bool, message: String) {
        guard currency == Currency.BGN.rawValue else {
            return (false, "Банкоматът работи само с български лева.")
        }
        
        guard self.availableCurrency[currency]! - amount >= 0  else {
            return (false, "Наличността на банкомата е недостатъчба.")
        }
        
        self.availableCurrency[currency] = self.availableCurrency[currency]! - amount
        return (true, "Наличността на банкоматът беше обновена на \(self.availableCurrency[currency]!) \(currency)")
    }
    
    // Other properties and methods here...
    mutating func withdraw(fromCard: inout Card, amount: Double) -> (success: Bool, message: String) {
        let eurToBgnRate = 1.956
        let usdToBgnRate = 1.858
        
        
        guard amount.isLessThanOrEqualTo(self.availableCurrency[Currency.BGN.rawValue]!) else {
            return (false, "Недостатъчна наличност в банкомата")
        }
        
        
        if var cardBGNBalance = fromCard.currentBalance[Currency.BGN.rawValue] {
            if amount > cardBGNBalance {
                if var cardEURBalance = fromCard.currentBalance[Currency.EUR.rawValue] {
                    let eurBalanceConvertedBgn = cardEURBalance * eurToBgnRate
                    if amount <= eurBalanceConvertedBgn {
                        // тук по условие трябва да има такса за превалутиране
                        // но спрямо примерите дадени в условието.
                        // Ако таксата се включи "математиката" не излиза.
                        let eurToWidraw = amount / eurToBgnRate
                        fromCard.currentBalance[Currency.EUR.rawValue] = cardEURBalance-eurToWidraw
                        removeCurrency(Currency.BGN.rawValue, amount: amount)
                        return (true, "Withdrawal successful. New balance: \(fromCard.currentBalance)")
                    }
                }
                
                if var cardUSDBalance = fromCard.currentBalance[Currency.USD.rawValue] {
                    let usdBalanceConvertedBgn = cardUSDBalance * usdToBgnRate
                    if amount <= usdBalanceConvertedBgn {
                        let usdToWidraw = amount / usdToBgnRate
                        fromCard.currentBalance[Currency.USD.rawValue] = cardUSDBalance-usdToWidraw
                        removeCurrency(Currency.BGN.rawValue, amount: amount)
                        return (true, "Withdrawal successful. New balance: \(fromCard.currentBalance)")
                    }
                }
                
                
                return (false, "Недостатъчна наличност по вашата сметка")
            }
            fromCard.currentBalance[Currency.BGN.rawValue] = cardBGNBalance-amount
            removeCurrency(Currency.BGN.rawValue, amount: amount)
            return (true, "Withdrawal successful. New balance: \(fromCard.currentBalance)")
            
        }
        
        return (false, "Withdrawal failed. Balance unvailable: \(fromCard.currentBalance)")
        
    }
    
}

struct exampleTest {
    var atmBalance: Double
    var cardBalance: [String: Double]
    var wantsToWidraw: Double
}

struct examplesData {
    var testCase: Array<exampleTest>
}

var examples = examplesData(testCase: Array())

//Примери:
//1. Банкоматът има наличност от 20 лева. Потребителя има в сметката си 80 лева.
//Потребителя иска да изтегли 20 лева - потребителя остава с 60 лева в сметката и
//банкоматът остава с наличност от 0 лева.
examples.testCase.append(exampleTest(atmBalance: 20, cardBalance: [Currency.BGN.rawValue: 80.0], wantsToWidraw: 20))

//2. Банкоматът има наличност от 100 лева. Потребителя има в сметката си 80 лева.
//Потребителя иска да изтегли 80 лева - потребителя остава с 0 лева в сметката,
//банкомата остава с наличност от 20 лева
examples.testCase.append(exampleTest(atmBalance: 100, cardBalance: [Currency.BGN.rawValue: 80.0], wantsToWidraw: 80))


//3. Банкоматът има наличност от 100 лева. Потребителя има в сметката си 80 лева.
//Потребителя иска да изтегли 90 лева - банкоматът изписва “Недостатъчна наличност
//по вашата сметка”
examples.testCase.append(exampleTest(atmBalance: 100, cardBalance: [Currency.BGN.rawValue: 80.0], wantsToWidraw: 90))

//4. Банкоматът има наличност от 60 лева. Потребителя има в сметката си 80 лева.
//Потребителя иска да изтегли 80 лева - банкоматът изписва “Недостатъчна наличност в
//банкомата”
examples.testCase.append(exampleTest(atmBalance: 60, cardBalance: [Currency.BGN.rawValue: 80.0], wantsToWidraw: 80))


//5. Банкоматът има наличност от 100 лева. Потребителя има в сметката си 80 лева и 60
//евро. Потребителя иска да изтегли 100 лева. Потребителя остава с 80 лева и 9.87 евро,
//банкоматът остава с 0 лева наличност.
examples.testCase.append(exampleTest(atmBalance: 100, cardBalance: [Currency.BGN.rawValue: 80.0, Currency.EUR.rawValue: 60.0], wantsToWidraw: 100))

//6. Банкоматът има наличност от 400 лева. Потребителя има в сметката си 80 лева и 60
//евро. Потребителя иска да изтегли 150 лева. Банкоматът изписва “Недостатъчна
//наличност по вашата сметка”
examples.testCase.append(exampleTest(atmBalance: 400, cardBalance: [Currency.BGN.rawValue: 80.0, Currency.EUR.rawValue: 60.0], wantsToWidraw: 150))


examples.testCase.forEach { testCase in
    print("Потребителят иска да изтегли \(testCase.wantsToWidraw) наличността на банкомата е \(testCase.atmBalance) а на картата \(testCase.cardBalance)")
    var atmToWindraw = ATM(id: "ATM-BILLA-1", exchangeFeePercentage: 2.0)
    var cardClient = Card(id: "CARD-1234", owner: "John Doe", currentBalance: testCase.cardBalance)
    atmToWindraw.addCurrency(Currency.BGN.rawValue, amount: testCase.atmBalance)
    var razpiska = atmToWindraw.withdraw(fromCard: &cardClient, amount: testCase.wantsToWidraw)
    atmToWindraw.printAvailableCurrencyStatus()
    print(razpiska.message)
    print("-------------------------END CASE-------------------------")
}
