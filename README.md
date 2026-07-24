# Algorithms in Java

University exercises on graphs and algorithmic techniques (Java 17).

## Contents

### Part 1 — Graph implementations (`part1/`)
- `IncidListUndir` — undirected graph with adjacency list
- `IncidListUndirWeight` — weighted variant
- Graph traversals, connected components, cycle detection

### Part 2 — Algorithmic techniques (`part2/`)
- **Greedy** — Moore's scheduling algorithm
- **Dynamic Programming**
- **Approximate** — approximation algorithms on graphs
- Graph data structures (`incidList` package)

## Requirements

- Java 17+
- JUnit 5 (for tests)
- `lib/graph2425.jar` — course library providing graph base interfaces (`upo.graph.base.*`)

## Project structure

```
algorithms-java/
├── part1/src/     # Graph implementations
├── part2/src/     # Greedy, DP, approximate algorithms
└── lib/           # graph2425.jar dependency
```

## Running tests

Open `part1/` or `part2/` in Eclipse/IntelliJ, add `lib/graph2425.jar` to the module path, and run the JUnit test classes.
