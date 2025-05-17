# StateMachine

A flexible and type-safe state machine implementation in Java.

## Overview

StateMachine is a library that provides a framework for implementing state machines in Java applications. It offers a clean, type-safe API for defining states, transitions, and the rules that govern state changes.

## Features

- Type-safe state machine implementation
- Support for guarded transitions (transitions with conditions)
- Immutable tokens for tracking state
- Clear separation of state machine model and execution
- Deterministic behavior (throws exception if multiple transitions are enabled)

## Installation

### Gradle

```kotlin
dependencies {
    implementation("net.npg:StateMachine:1.0-SNAPSHOT")
}
```

### Maven

```xml
<dependency>
    <groupId>net.npg</groupId>
    <artifactId>StateMachine</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

Here's a simple example of how to use the StateMachine library:

```java
// Create a state model
SimpleIdentifier modelId = new SimpleIdentifier("trafficLight");
ConcreteStateModel<SimpleIdentifier> model = new ConcreteStateModel<>(modelId);

// Create states
SimpleIdentifier redId = new SimpleIdentifier("red");
SimpleIdentifier yellowId = new SimpleIdentifier("yellow");
SimpleIdentifier greenId = new SimpleIdentifier("green");

ConcreteState<SimpleIdentifier> redState = new ConcreteState<>(redId);
ConcreteState<SimpleIdentifier> yellowState = new ConcreteState<>(yellowId);
ConcreteState<SimpleIdentifier> greenState = new ConcreteState<>(greenId);

// Add states to the model
model.addState(redState);
model.addState(yellowState);
model.addState(greenState);

// Define transition guards
BooleanSupplier alwaysTrue = () -> true;

// Add transitions
model.addTransition(redState, greenState, alwaysTrue, new SimpleIdentifier("redToGreen"));
model.addTransition(greenState, yellowState, alwaysTrue, new SimpleIdentifier("greenToYellow"));
model.addTransition(yellowState, redState, alwaysTrue, new SimpleIdentifier("yellowToRed"));

// Create a token starting at the red state
ConcreteToken<SimpleIdentifier> token = new ConcreteToken<>(redState, model);

// Create a state machine
GuardedStateMachine<SimpleIdentifier> stateMachine = new GuardedStateMachine<>();

// Execute the state machine
Token<SimpleIdentifier> finalToken = stateMachine.execute(token);

// The token should now be in the red state after going through the cycle
System.out.println("Final state: " + finalToken.state().id());
```

## API Documentation

### Core Interfaces

- `StateMachine<I>`: The main interface for executing state machines
- `State<I>`: Represents a state in the state machine
- `Transition<I>`: Represents a transition between states
- `Token<I>`: Represents the current state and model
- `StateModel<I>`: Represents the structure of a state machine
- `Identifier`: Marker interface for state and transition identifiers

### Implementation Classes

- `GuardedStateMachine<I>`: Implementation of StateMachine that respects transition guards
- `ConcreteState<I>`: Basic implementation of the State interface
- `ConcreteStateModel<I>`: Basic implementation of the StateModel interface
- `ConcreteToken<I>`: Basic implementation of the Token interface
- `ConcreteTransition<I>`: Basic implementation of the Transition interface
- `SimpleIdentifier`: Basic implementation of the Identifier interface

## License

StateMachine is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0). See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.