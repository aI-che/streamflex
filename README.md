# StreamFlex

A **Java** command-line simulation of a streaming platform, built to practice **object-oriented design and design patterns**.

## Features

- Accounts, subscriptions and dynamic pricing
- Premium add-ons: 4K, ad-free, offline downloads
- Content search and playback with age and plan checks
- Credit card and crypto payments

## Design patterns

| Pattern | Used for |
| --- | --- |
| **State** | Account status: Active, Suspended, Pending Payment, Cancelled |
| **Decorator** | Stacking premium add-ons on a base plan |
| **Strategy** | Swappable payment methods |

Three-layer architecture: **CLI → Domain → Data** (in-memory).

## Run

```bash
javac -d out $(find src -name "*.java")
java -cp out com.streamflex.App
```

Requires Java 11+. No external dependencies.

## About

Built for the Object-Oriented Analysis and Design (OOAD) course at Dokuz Eylül University.

## License

MIT
