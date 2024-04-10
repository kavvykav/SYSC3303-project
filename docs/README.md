# Elevator Control System - Iteration 5

Authors + Student ID:  
Matthew Huybregts 101185221  
Sean Pruss 101189970  
William Kavanagh 101182915  
Joshua Robson 101195802  
Abed Qubbaj 101205030

Date: April 10th, 2024

## Overview

This project implements an elevator control system with three main subsystems: floor.Floor, scheduler.Scheduler, and
elevator.Elevator. The communication between these subsystems is achieved using User Datagram Protocol (UDP).

## Requirements

Java `SDK 21`, and Language Level set to `Default SDK` in IntelliJ.

## Components

### elevator

- **elevator.Elevator.java:** Represents the elevator.Elevator Subsystem.
- **elevator.ElevatorState.java:** Interface which represents the state of the elevator.Elevator.
  This is used to follow the State Pattern.
- **elevator.ElevatorEstablishingConnectionState.java:** Represents the initial connection state.
- **elevator.ElevatorIdleState.java:** Represents the Idle State of the elevator.Elevator.
- **elevator.ElevatorMotorRunningState.java:** Represents the state where the elevator motor is running.
- **elevator.Timer.java:** Sets the time that the elevator has to reach its destination.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.

### floor

- **floor.Floor.java:** Represents the floor.Floor subsystem
- **floor.FloorData.java:** Represents the information to be shared between subsystems, including timestamp, floor
  number, direction, and requested floor.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.

### scheduler

- **scheduler.Scheduler.java:** Manages the coordination between the floor.Floor and elevator.Elevator subsystems
- **common.UDPServer.java:** Handles communication with both the floor.Floor and elevator.Elevator subsystems using UDP
- **scheduler.SchedulerState.java:** The interface used for the scheduler.Scheduler state to implement the State Pattern
- **scheduler.SchedulerIdleState.java:** The state that represents the scheduler.Scheduler's Idle state
- **scheduler.SchedulerRequestReceivedState.java:** The state that represents the scheduler.Scheduler receiving a
  request from the floor.Floor
- **scheduler.SchedulerResponseReceivedState.java:** The state that represents the scheduler.Scheduler receivinag a
  response from the elevator.Elevator

### gui

- **gui.Console.java:** Includes the code that initializes and updates the visual aspects of the GUI
- **gui.GUI.java:** Receives updates from the Scheduler and updates the Console accordingly. This is the runnable component of the GUI subsystem.

### test

- **test.ElevatorTest.java:** The unit test file for the Elevator subsystem
- **test.SchedulerTest.java**: The unit test file for the Scheduler subsystem
- **test.FloorDataTest.java**: The unit test file for the FloorData class
- **test.FloorRequestTest.java:** The unit test file for the FloorRequest class
- **test.ElevatorStatusTest.java**: The unit test file for the ElevatorStatus class
- **test.ElevatorClientTest.java**: The unit test file for the ElevatorClient class
- **test.ElevatorStatisticsTest.java:** The unit test file for the ElevatorStatistics class
- **test.TimerTest.java**: The unit test file for the Timer class
- **test.MotorTest.java**: The unit test file for the Motor class
- **test.PassengerRequestTest.java:** The unit test file for the PassengerRequestTest class
- **test.ConsoleTest.java:** The unit test file for the Console class
- **floor.IntegrationTest.java:** The file for integration testing the system
- **floor.AcceptanceTest.java:** The file for acceptance testing the system

### Directory Structure

```bash
SYSC3303-project
├── docs
│  ├── CommonClasses.png
│  ├── DoorFaultSequenceDiagram.png
│  ├── ElevatorStateDiagram.png
│  ├── ElevatorStuckFaultSequenceDiagram.png
│  ├── ElevatorStuckTimingDiagram.png
│  ├── ElevatorSubsystemClassDiagram.png
│  ├── ElevatorTooSlowTimingDiagram.png
│  ├── FloorSubsystemClassDiagram.png
│  ├── GUIClassDiagram.png
│  ├── README.md
│  ├── SchedulerStateDiagram.png
│  ├── SchedulerSubsystemClassDiagram.png
│  ├── SequenceDiagram.png
│  └── SYSC 3303 B1G1 FINAL PROJECT REPORT.pdf
├── lib
│  ├── apiguardian-api-1.1.2.jar
│  ├── assertj-core-3.17.2.jar
│  ├── assertj-swing-3.17.1.jar
│  ├── assertj-swing-junit-3.17.1.jar
│  ├── byte-buddy-1.14.11.jar
│  ├── byte-buddy-agent-1.14.11.jar
│  ├── fest-reflect-1.4.1.jar
│  ├── fest-util-1.2.5.jar
│  ├── hamcrest-core-1.3.jar
│  ├── junit-4.12.jar
│  ├── junit-jupiter-5.8.1.jar
│  ├── junit-jupiter-api-5.8.1.jar
│  ├── junit-jupiter-api-5.10.1.jar
│  ├── junit-jupiter-engine-5.8.1.jar
│  ├── junit-jupiter-params-5.8.1.jar
│  ├── junit-platform-commons-1.8.1.jar
│  ├── junit-platform-commons-1.10.1.jar
│  ├── junit-platform-engine-1.8.1.jar
│  ├── mockito-core-5.10.0.jar
│  ├── mockito-junit-jupiter-5.10.0.jar
│  ├── objenesis-3.3.jar
│  ├── opentest4j-1.2.0.jar
│  └── opentest4j-1.3.0.jar
├── src
│  ├── common
│  │  ├── Direction.java
│  │  ├── ElevatorStatistics.java
│  │  ├── ElevatorStatus.java
│  │  ├── FloorRequest.java
│  │  ├── NetworkConstants.java
│  │  ├── PassengerRequest.java
│  │  ├── UDPClient.java
│  │  └── UDPServer.java
│  ├── elevator
│  │  ├── Elevator.java
│  │  ├── ElevatorEstablishingConnectionState.java
│  │  ├── ElevatorIdleState.java
│  │  ├── ElevatorRequestReceivedState.java
│  │  ├── ElevatorState.java
│  │  ├── Motor.java
│  │  └── Timer.java
│  ├── floor
│  │  ├── AcceptanceTest.java
│  │  ├── Floor.java
│  │  ├── FloorData.java
│  │  └── IntegrationTest.java
│  ├── gui
│  │  ├── Console.java
│  │  └── GUI.java
│  ├── scheduler
│  │  ├── ElevatorClient.java
│  │  ├── Scheduler.java
│  │  ├── SchedulerIdleState.java
│  │  ├── SchedulerRequestReceivedState.java
│  │  └── SchedulerState.java
│  └── test
│     ├── ConsoleTest.java
│     ├── ElevatorClientTest.java
│     ├── ElevatorStatisticsTest.java
│     ├── ElevatorStatusTest.java
│     ├── ElevatorTest.java
│     ├── FloorDataTest.java
│     ├── FloorRequestTest.java
│     ├── MotorTest.java
│     ├── PassengerRequestTest.java
│     ├── SchedulerTest.java
│     └── TimerTest.java
├── SYSC3303-project.iml
└── test_input.txt
```

> [!IMPORTANT]
> Make sure all `.jar` files in the `lib` directory are added to the project  
> in IntelliJ

## Usage

> [!IMPORTANT]
> Run the files in this order for the system to initialize properly

1. Run `scheduler.Scheduler.java`
2. Run `gui.GUI.java`
3. Run `elevator.Elevator.java`
4. Run `floor.Floor.java`

## Input File Format

The floor.Floor subsystem reads input from a file, and each line in the file represents an event.  
The format is as follows:

[timestamp] [floor_number] [direction (up/down)] [requested_floor]

## Notes

- The project uses UDP for communication, and the communication flow is: floor.Floor -> scheduler.Scheduler ->
  elevator.Elevator -> scheduler.Scheduler -> floor.Floor
- The code includes error handling for invalid data and communication failures

Feel free to explore and modify the code to suit your specific requirements. If you encounter any issues or have  
questions, please contact one of the authors.

## Delegation of Responsibilities

- Liam Kavanagh -> Implementing and integrating GUI
- Matthew Huybregts -> Implementing capacity limits and back end
- Sean Pruss -> Update README.md
- Joshua Robson -> JUnit tests, Debugging, Helped with GUI
- Abed Qubbaj -> Update diagrams
