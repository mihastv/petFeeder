# Smart Pet Feeder - Java Simulation

A concurrent simulation of a smart pet feeder system with three independent processes:
- **Cat**: Eats food and gets hungry when the bowl is empty
- **Feeder**: Dispenses food periodically but can randomly jam
- **Repairman**: Detects and fixes jammed feeders

## Features

✨ **Graphical User Interface** - Beautiful animated visualization of the simulation  
📊 **Real-time Event Log** - See all actions as they happen  
📈 **Status Dashboard** - Monitor cat happiness, food levels, and feeder status  
🎨 **Smooth Animations** - Watch the cat eat, feeder dispense, and repairman fix jams  
💻 **Console Mode** - Also available for headless/terminal environments

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

### Verify Your Setup

Before running, you can verify your environment:

```bash
./verify.sh
```

This checks:
- Java version (must be 11+)
- Maven installation
- Display availability for GUI

## Running the Simulation

### With GUI (Recommended)
```bash
./run.sh gui
# or simply
./run.sh
```

### Console Only (No GUI)
```bash
./run.sh console
```

### Using Maven Directly
```bash
# GUI mode
mvn exec:java -Dexec.mainClass="com.petfeeder.SmartPetFeederApp"

# Console mode
mvn exec:java -Dexec.mainClass="com.petfeeder.SmartPetFeederConsole"
```

## GUI Features

The graphical interface includes:

1. **Animated Visualization**
    - 🐱 Happy/hungry cat with tail wagging
    - 🍚 Food bowl showing current contents
    - 🤖 Feeder with dispensing animation
    - 🔧 Repairman fixing jams
    - ⚠️ Visual jam indicators

2. **Real-time Event Log**
    - Color-coded events (green=success, red=error, yellow=warning)
    - Timestamps for all actions
    - Scrollable history

3. **Status Bar**
    - Cat happiness indicator
    - Current food count
    - Feeder status (working/jammed)
    - Simulation uptime

4. **Control Buttons**
    - Stop simulation
    - Help dialog
    - About information

## Project Structure

```
smart-pet-feeder/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/petfeeder/
        │   ├── SmartPetFeederApp.java   # Main application
        │   ├── SharedState.java         # Shared state management
        │   ├── Cat.java                 # Cat thread
        │   ├── Feeder.java              # Feeder thread
        │   └── Repairman.java           # Repairman thread
        └── resources/
            └── logback.xml              # Logging configuration
```

## Building the Project

```bash
# Compile and package the application
mvn clean package

# This creates an executable JAR: target/smart-pet-feeder-1.0-SNAPSHOT.jar
```

## Running the Application

```bash
# Run using Maven
mvn exec:java -Dexec.mainClass="com.petfeeder.SmartPetFeederApp"

# Or run the packaged JAR
java -jar target/smart-pet-feeder-1.0-SNAPSHOT.jar
```

## How It Works

The simulation runs three concurrent threads:

1. **Cat Thread** (checks every 1 second):
    - If food is available: eats one unit and becomes happy
    - If bowl is empty: gets hungry and meows

2. **Feeder Thread** (dispenses every 3 seconds):
    - Adds 2 units of food to the bowl
    - Has a 20% chance of jamming after each dispense
    - Cannot dispense while jammed

3. **Repairman Thread** (checks every 0.5 seconds):
    - Detects when feeder is jammed
    - Takes 2 seconds to fix the jam
    - Restores feeder to working condition

## Configuration

You can modify the simulation parameters in each class:

- `Cat.java`: `CHECK_INTERVAL_MS` - how often the cat checks for food
- `Feeder.java`:
    - `DISPENSE_INTERVAL_MS` - how often food is dispensed
    - `FOOD_PER_DISPENSE` - amount of food per dispense
    - `JAM_PROBABILITY` - chance of jamming (0.0 to 1.0)
- `Repairman.java`:
    - `CHECK_INTERVAL_MS` - how often to check for jams
    - `REPAIR_TIME_MS` - time needed to fix a jam
- `SmartPetFeederApp.java`: `SIMULATION_DURATION_MS` - total simulation time

## Sample Output

```
=================================================
  Smart Pet Feeder Simulation Starting
=================================================

14:23:01.234 [Cat-Thread] INFO  - 🐱 Cat process started
14:23:01.235 [Feeder-Thread] INFO  - 🤖 Feeder process started
14:23:01.235 [Repairman-Thread] INFO  - 🔧 Repairman process started
14:23:01.235 [main] INFO  - Simulation running for 30 seconds...

14:23:04.236 [Feeder-Thread] INFO  - 🤖 FEEDER: Food dispensed! Bowl has 2 units.
14:23:05.237 [Cat-Thread] INFO  - 🐱 CAT: *Nom nom nom* Cat is happy! (Food remaining: 1)
14:23:06.238 [Cat-Thread] INFO  - 🐱 CAT: *Nom nom nom* Cat is happy! (Food remaining: 0)
14:23:07.239 [Cat-Thread] WARN  - 🐱 CAT: *Meow* I am hungry!
14:23:07.240 [Feeder-Thread] INFO  - 🤖 FEEDER: Food dispensed! Bowl has 2 units.
14:23:07.241 [Feeder-Thread] ERROR - 🤖 FEEDER: *CLUNK* Dispenser jammed!
14:23:07.741 [Repairman-Thread] WARN  - 🔧 REPAIR: Jam detected! Fixing...
14:23:08.240 [Cat-Thread] INFO  - 🐱 CAT: *Nom nom nom* Cat is happy! (Food remaining: 1)
14:23:09.742 [Repairman-Thread] INFO  - 🔧 REPAIR: Fixed the feeder! Cat can eat again.
...
```

## Thread Safety

All shared state access is synchronized through the `SharedState` class to prevent race conditions between the three concurrent threads.

## Relation to Promela Model

This Java implementation mirrors the Promela verification model with the same three processes:
- Concurrent execution with shared state
- Non-deterministic jam behavior
- Liveness property: hungry cat eventually gets food (ensured by repairman)

## License

MIT License - Feel free to use and modify!