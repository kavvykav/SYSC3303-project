# Elevator Control System
Iteration 2

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

- **Motor:** Controls the movement of the elevator.
- **Door:** Manages the opening and closing of the elevator door.
- **ElevatorButton:** Represents the buttons inside the elevator for selecting floors.
- **ElevatorLamp:** Represents the lamps inside the elevator indicating the selected floors.

### Floor

- **FloorData:** Represents the information to be shared between subsystems, including timestamp, floor number, direction, and requested floor.
- **UDPClient:** Handles communication with the Scheduler using UDP.

### Scheduler

- **Scheduler:** Manages the coordination between the Floor and Elevator subsystems.
- **UDPServer:** Handles communication with both the Floor and Elevator subsystems using UDP.

## Usage

1. Compile all Java files: `javac *.java`
2. Run the program: `java Main`

## Input File Format

The Floor subsystem reads input from a file, and each line in the file represents an event. The format is as follows:

[timestamp] [floor_number] [direction (up/down)] [requested_floor]

## Notes

- The project uses UDP for communication, and the communication flow is: Floor -> Scheduler -> Elevator -> Scheduler -> Floor.
- The code includes error handling for invalid data and communication failures.

Feel free to explore and modify the code to suit your specific requirements. If you encounter any issues or have questions, please contact one of the authors.

## Delegation of Responsibilities

- Liam Kavanagh -> Scheduler State Diagram and Coding
- Matthew Huybregts -> Floor refactor and Scheduler Coding
- Sean Pruss -> Elevator State Diagram and Coding
- Joshua Robson -> JUnit tests
- Abed Qubbaj -> UML diagrams and README
