# MiniBank — Integrated OOP Semester Project

A single Java 21 + Maven project integrating the 14 MiniBank practical milestones: menu shell, model/encapsulation, equality and cloning, regex validation/command parsing/statements, abstract account types and polymorphism, interfaces/lambdas/packages, runtime annotations/reflection, checked banking exceptions, synchronized operations, executor-based transaction processing, serialization/logging/NIO reports, collections and concurrent maps, generic annotation-driven repository, and service/JUnit integration.

## Requirements
- JDK 21 (JDK 17+ is fine if you change `maven.compiler.release` to 17; the project uses records and modern `instanceof` syntax).
- Maven 3.8+ installed and available in PowerShell as `mvn`.

## Run from PowerShell
```powershell
cd "G:\OOP\Practical 14\Part A"
# Extract this project into that folder, or cd to the extracted minibank-complete folder.
mvn test
mvn clean package
java -jar target/oop-minibank-1.0.0.jar
```

If your extracted folder is named `minibank-complete`, run the commands from inside that folder. The app stores data in `data/accounts.ser`, appends transaction entries to `logs/transactions.log`, and writes `reports/end-of-day.txt`.

## Menu
1. Open account
2. Deposit
3. Withdraw
4. Transfer
5. Account statement
6. List accounts (sorted by balance)
7. End-of-day report
8. Run sample concurrent deposits
9. Exit (saves state)

## Notes
- This is an educational console simulation, not production banking software. It has no authentication, authorization, encryption, audit guarantees, database transaction isolation, or regulatory controls.
- Java native deserialization must only read trusted local files. Do not load attacker-controlled `.ser` files.
- Amounts are whole rupees (`long`); interest is shown as a simple yearly estimate, not credited to balances.
- Transaction transfer uses a consistent account-number lock order to avoid lock-order deadlocks. Service-level synchronized methods provide simple in-process coordination for this lab.
