# elevator.Elevator Control System - Iteration 2


Authors + Student ID:  
Matthew Huybregts 101185221  
Sean Pruss 101189970  
William Kavanagh 101182915  
Joshua Robson 101195802  
Abed Qubbaj  101205030

Date: February 17th, 2024

## Overview

This project implements an elevator control system with three main subsystems: floor.Floor, scheduler.Scheduler, and elevator.Elevator. The communication between these subsystems is achieved using User Datagram Protocol (UDP).

## Components

### elevator.Elevator

- **elevator.Elevator.java:** Represents the elevator.Elevator Subsystem.
- **elevator.ElevatorState.java:** Interface which represents the state of the elevator.Elevator. This is used to follow the State Pattern.
- **elevator.ElevatorEstablishingConnectionState.java:** Represents the initial connection state.
- **elevator.ElevatorIdleState.java:** Represents the Idle State of the elevator.Elevator.
- **elevator.ElevatorTaskReceivedState.java:** Represents the state when the elevator.Elevator receives a task.
- **elevator.ElevatorMotorRunningState.java:** Represents the state where the elevator motor is running.
- **elevator.ElevatorDestinationReachedState.java** Represents the state where the destination is reached.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.
  
### floor.Floor

- **floor.Floor.java:** Represents the floor.Floor subsystem
- **common.FloorData.java:** Represents the information to be shared between subsystems, including timestamp, floor number, direction, and requested floor.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.

### scheduler.Scheduler

- **scheduler.Scheduler.java:** Manages the coordination between the floor.Floor and elevator.Elevator subsystems.
- **common.UDPServer.java:** Handles communication with both the floor.Floor and elevator.Elevator subsystems using UDP.
- **scheduler.SchedulerState.java:** The interface used for the scheduler.Scheduler state to implement the State Pattern.
- **scheduler.SchedulerEstablishConnectionState.java:** The state that represents the initial connection state.
- **scheduler.SchedulerIdleState.java:** The state that represents the scheduler.Scheduler's Idle state.
- **scheduler.SchedulerRequestReceivedState.java:** The state that represents the scheduler.Scheduler receiving a request from the floor.Floor.
- **scheduler.SchedulerWaitState.java:** The state that represents the scheduler.Scheduler waiting for a reply from the elevator.Elevator.
- **scheduler.SchedulerResponseReceivedState.java:** The state that represents the scheduler.Scheduler receiving a response from the elevator.Elevator.

## Test Files
There are three (3) test files that are included to ensure the system states and data is correct.

The test files are test.FloorDataTest.java, test.SchedulerTest.java, and elevator.Elevator.java

### test.ElevatorTest.java
The tests included in this file are used to ensure that each state that is called is the correct state that is required in the elevator subsystem. States tested include: DestinationReached, EstablishingConnection, Idle, MotorRunning, and TaskReceived
### test.SchedulerTest.java
The tests included in this file are used to ensure that each state that is called is the correct state that is required in the scheduler subsystem. States tested include: ResponseReceived, EstablishingConnection, Idle, RequestReceived, and Wait
### test.FloorDataTest.java
The tests included in this file are used to ensure that the data in a common.FloorData object is correct. floor.Floor Number, TimeStamp, Car Button, and Direction are tested in this file.

## Usage

1. Compile all Java files: `javac src/*.java`
2. Run the program: `java src/Main`

## Input File Format

The floor.Floor subsystem reads input from a file, and each line in the file represents an event. The format is as follows:

[timestamp] [floor_number] [direction (up/down)] [requested_floor]

## Notes

- The project uses UDP for communication, and the communication flow is: floor.Floor -> scheduler.Scheduler -> elevator.Elevator -> scheduler.Scheduler -> floor.Floor.
- The code includes error handling for invalid data and communication failures.

Feel free to explore and modify the code to suit your specific requirements. If you encounter any issues or have questions, please contact one of the authors.

## Delegation of Responsibilities

- Liam Kavanagh -> scheduler.Scheduler State Diagram and scheduler.Scheduler Coding
- Matthew Huybregts -> floor.Floor refactor and scheduler.Scheduler Coding
- Sean Pruss -> elevator.Elevator State Diagram and elevator.Elevator Coding
- Joshua Robson -> JUnit tests
- Abed Qubbaj -> Class diagram, sequence diagram and README.md
