# StreamFlex

**StreamFlex** is a Java-based command-line simulation of a modern streaming platform. The project focuses on object-oriented design, subscription management, dynamic pricing, payment processing, and state-based content access control.

## Features

* User and account management
* Subscription plan customization
* Dynamic premium add-ons
* Dynamic subscription pricing
* Content browsing and search
* Content playback authorization
* Multiple payment methods
* Account state management

Users can customize their subscription with features such as **4K Resolution, Ad-Free Viewing, and Offline Downloads**. Playback access is controlled according to account state, age restrictions, and subscription privileges.

## Design Patterns

### State Pattern

Manages the different states of a user account, such as:

* Active
* Suspended
* Pending Payment
* Cancelled

The account state determines how operations such as playback and subscription actions are handled.

### Decorator Pattern

Allows premium subscription features to be added dynamically without modifying the base subscription plan.

Examples:

* 4K Resolution
* Ad-Free Viewing
* Offline Downloads

### Strategy Pattern

Provides interchangeable payment algorithms such as:

* Credit Card
* Cryptocurrency

This allows new payment methods to be added without changing the existing business logic.

## Architecture

The system follows a three-layer architecture:

```text
Presentation / CLI
        ↓
Domain / Business Logic
        ↓
Data Access
```

The presentation layer handles user interaction, the domain layer contains the core business logic and design patterns, and the data access layer manages the in-memory content inventory.

## Technologies

* Java 11+
* Object-Oriented Programming
* Design Patterns
* Java Collections
* Command-Line Interface

The application uses standard Java libraries and does not require an external database or heavy framework.

## Project Structure

```text
src/
└── com/
    └── streamflex/
        ├── data/
        ├── domain/
        │   ├── decorator/
        │   ├── model/
        │   ├── state/
        │   └── strategy/
        ├── exception/
        ├── presentation/
        └── App.java
```

## Academic Project

Developed as part of the **Object-Oriented Analysis and Design (OOAD)** course at Dokuz Eylül University.

## License

MIT License

