# Elevator Control System - Iteration 3

Authors + Student ID:  
Matthew Huybregts 101185221
Sean Pruss 101189970  
William Kavanagh 101182915  
Joshua Robson 101195802
Abed Qubbaj  101205030

Date: March 9, 2024

## Overview

This project implements an elevator control system with three main subsystems: floor.Floor, scheduler.Scheduler, and 
elevator.Elevator. The communication between these subsystems is achieved using User Datagram Protocol (UDP).

## Requirements
Java `SDK 21`, and Language Level set to `Default SDK` in Intellij.

## Components

### elevator

- **elevator.Elevator.java:** Represents the elevator.Elevator Subsystem.
- **elevator.ElevatorState.java:** Interface which represents the state of the elevator.Elevator. 
This is used to follow the State Pattern.
- **elevator.ElevatorEstablishingConnectionState.java:** Represents the initial connection state.
- **elevator.ElevatorIdleState.java:** Represents the Idle State of the elevator.Elevator.
- **elevator.ElevatorMotorRunningState.java:** Represents the state where the elevator motor is running.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.
  
### floor

- **floor.Floor.java:** Represents the floor.Floor subsystem
- **common.FloorData.java:** Represents the information to be shared between subsystems, including timestamp, floor 
number, direction, and requested floor.
- **common.UDPClient.java:** Handles communication with the scheduler.Scheduler using UDP.

### scheduler

- **scheduler.Scheduler.java:** Manages the coordination between the floor.Floor and elevator.Elevator subsystems
- **common.UDPServer.java:** Handles communication with both the floor.Floor and elevator.Elevator subsystems using UDP
- **scheduler.SchedulerState.java:** The interface used for the scheduler.Scheduler state to implement the State Pattern
- **scheduler.SchedulerIdleState.java:** The state that represents the scheduler.Scheduler's Idle state
- **scheduler.SchedulerRequestReceivedState.java:** The state that represents the scheduler.Scheduler receiving a 
request from the floor.Floor
- **scheduler.SchedulerResponseReceivedState.java:** The state that represents the scheduler.Scheduler receiving a 
response from the elevator.Elevator

### test

There are five (5) test files that are included to ensure the system states and data is correct.

- test.ElevatorTest.java: The tests included in this file are used to ensure that each state that is called is the 
correct state that is required in the elevator subsystem and to ensure elevator.Elevator data correction. States tested include: EstablishingConnection, Idle and MotorRunning. Data tests include: GetNumRequests, GetCurrentRequest, updateRequestsTest, ShouldStop, and GetStatus.
- test.SchedulerTest.java: The tests included in this file are used to ensure that each state that is called is the 
correct state that is required in the scheduler subsystem and to ensure scheduler.Scheduler data correction. States tested include: ResponseReceived, Idle and RequestReceived. Data tests include: GetClient, ChooseElevator, and CanServiceRequest.
- test.FloorDataTest.java: The tests included in this file are used to ensure that the data in a common.FloorData 
object is correct. Floor Number, Timestamp, Car Button, and Direction fields are included in the tests.
- test.ElevatorStatusTest.java: The tests included in this file are used to ensure that the common.ElevatorStatus data is correct. Tests include GetId, GetFloor, SetFloor, GetDirection, and SetDirection.
- test.ElevatorClientTest.java: The tests included in this file are used to ensure that the scheduler.ElevatorClient data is correct. Tests include GetAddress, GetPort, and GetStatus.

## Usage

1. Run `scheduler.Scheduler.java`
2. Run `elevator.Elevator.java`
3. Run `floor.Floor.java`

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

- Liam Kavanagh -> Scheduler algorithm and coding
- Matthew Huybregts -> Coding, debugging, refactoring
- Sean Pruss -> Debugging, also helped out with JUnit tests
- Joshua Robson -> JUnit tests
- Abed Qubbaj -> Updated diagrams and README.md
