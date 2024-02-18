# Elevator Control System - Iteration 2


Authors + Student ID:  
Matthew Huybregts 101185221  
Sean Pruss 101189970  
William Kavanagh 101182915  
Joshua Robson 101195802  
Abed Qubbaj  101205030

Date: February 17th, 2024

## Overview

This project implements an elevator control system with three main subsystems: Floor, Scheduler, and Elevator. The communication between these subsystems is achieved using User Datagram Protocol (UDP).

## Components

### Elevator

- **Elevator.java:** Represents the Elevator Subsystem.
- **ElevatorState.java:** Interface which represents the state of the Elevator. This is used to follow the State Pattern.
- **ElevatorEstablishingConnectionState.java:** Represents the initial connection state.
- **ElevatorIdleState.java:** Represents the Idle State of the Elevator.
- **ElevatorTaskReceivedState.java:** Represents the state when the Elevator receives a task.
- **ElevatorMotorRunningState.java:** Represents the state where the elevator motor is running.
- **ElevatorDestinationReachedState.java** Represents the state where the destination is reached.
- **UDPClient.java:** Handles communication with the Scheduler using UDP.
  
### Floor

- **Floor.java:** Represents the Floor subsystem
- **FloorData.java:** Represents the information to be shared between subsystems, including timestamp, floor number, direction, and requested floor.
- **UDPClient.java:** Handles communication with the Scheduler using UDP.

### Scheduler

- **Scheduler.java:** Manages the coordination between the Floor and Elevator subsystems.
- **UDPServer.java:** Handles communication with both the Floor and Elevator subsystems using UDP.
- **SchedulerState.java:** The interface used for the Scheduler state to implement the State Pattern.
- **SchedulerEstablishConnectionState.java:** The state that represents the initial connection state.
- **SchedulerIdleState.java:** The state that represents the Scheduler's Idle state.
- **SchedulerRequestReceivedState.java:** The state that represents the Scheduler receiving a request from the Floor.
- **SchedulerWaitState.java:** The state that represents the Scheduler waiting for a reply from the Elevator.
- **SchedulerResponseReceivedState.java:** The state that represents the Scheduler receiving a response from the Elevator.

## Test Files
There are three (3) test files that are included to ensure the system states and data is correct.

The test files are FloorDataTest.java, SchedulerTest.java, and Elevator.java

### ElevatorTest.java
The tests included in this file are used to ensure that each state that is called is the correct state that is required in the elevator subsystem. States tested include: DestinationReached, EstablishingConnection, Idle, MotorRunning, and TaskReceived
### SchedulerTest.java
The tests included in this file are used to ensure that each state that is called is the correct state that is required in the scheduler subsystem. States tested include: ResponseReceived, EstablishingConnection, Idle, RequestReceived, and Wait
### FloorDataTest.java
The tests included in this file are used to ensure that the data in a FloorData object is correct. Floor Number, TimeStamp, Car Button, and Direction are tested in this file.

## Usage

1. Compile all Java files: `javac src/*.java`
2. Run the program: `java src/Main`

## Input File Format

The Floor subsystem reads input from a file, and each line in the file represents an event. The format is as follows:

[timestamp] [floor_number] [direction (up/down)] [requested_floor]

## Notes

- The project uses UDP for communication, and the communication flow is: Floor -> Scheduler -> Elevator -> Scheduler -> Floor.
- The code includes error handling for invalid data and communication failures.

Feel free to explore and modify the code to suit your specific requirements. If you encounter any issues or have questions, please contact one of the authors.

## Delegation of Responsibilities

- Liam Kavanagh -> Scheduler State Diagram and Scheduler Coding
- Matthew Huybregts -> Floor refactor and Scheduler Coding
- Sean Pruss -> Elevator State Diagram and Elevator Coding
- Joshua Robson -> JUnit tests
- Abed Qubbaj -> Class diagram, sequence diagram and README.md
