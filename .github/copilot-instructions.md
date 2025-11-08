## Quick orientation

This is a small single-module Java (Maven) console app for tracking income and expenses.

- Entrypoint: `org.example.Main` (see `src/main/java/org/example/Main.java`).
- Core domain: `ExpenseTracker` stores an in-memory `List<Transaction>` and exposes UI-driven operations (add, view, load, save).
- Persistence: plain CSV files (default `transactions.csv`) using header `DATE,TYPE,CATEGORY,AMOUNT,DESCRIPTION`.

## Build / run / debug (what actually works here)

- Java version used in the POM: 21. The project is a standard Maven layout (`pom.xml`).
- Compile: `mvn compile` (run from repo root).
- Run (after compile):

  PowerShell

  ```powershell
  mvn compile
  java -cp target/classes org.example.Main
  ```

- You can also run in an IDE (IntelliJ/VS Code): set main class to `org.example.Main` and use Java 21 SDK.

## Important code patterns and conventions (project-specific)

- Date format: `yyyy-MM-dd` (see `ExpenseTracker.formatter`). Parse/print exactly this format.
- Transaction type: normalized to upper-case strings `INCOME` or `EXPENSE`. Code expects these exact values.
- Persistence: CSV is implemented with `String.split(",")` (see `ExpenseTracker.loadFromFile`). This is a simple parser — descriptions containing commas will be split incorrectly. If you modify CSV logic, prefer a proper CSV library (e.g., OpenCSV) or ensure quoting/escaping.
- Autosave: `ExpenseTracker.addTransaction` calls `saveToFile(DEFAULT_FILE)` automatically after adding a transaction — adding a transaction immediately writes `transactions.csv` in the project root.
- Visibility: `ExpenseTracker` and `Transaction` are package-private (no `public` modifier). Tests or new classes outside `org.example` must be in the same package or change visibility.

## Key files to inspect for changes

- `src/main/java/org/example/Main.java` — CLI menu, user input flow.
- `src/main/java/org/example/ExpenseTracker.java` — all domain logic: add, load, save, summary.
- `src/main/java/org/example/Transaction.java` — data model and `toString()` output format.
- `transactions.csv` — example data and canonical CSV header.

## What an AI coding agent should do first when changing code

1. Read `Main.java` and `ExpenseTracker.java` together to understand the synchronous console flow (Main drives user I/O; ExpenseTracker does logic + I/O).
2. Preserve CLI prompts and CSV header format unless intentionally changing the public file format.
3. When editing CSV read/write, add a small unit or integration check (manual run) demonstrating round-trip read->write on the sample `transactions.csv`.
4. If you change visibility of classes, update any code that relies on package-private access (there are no other packages).

## Data & edge-cases to be careful about (from reading code)

- CSV parsing uses `split(",")` and assumes exactly 5 columns after the header. Guard against malformed rows.
- `LocalDate.parse(...)` will throw on invalid dates; user input in `Main` is not validated beyond parse exceptions. Add try/catch and re-prompt where appropriate.
- Number parsing uses `Double.parseDouble(...)` and `Integer.parseInt(...)` without validation. Consider safe parsing or user feedback.

## No external dependencies

- `pom.xml` currently declares no external libraries. Any new dependency must be added to `pom.xml` and kept minimal.

## Example snippets the agent can reuse

- Add transaction (pattern): create `Transaction t = new Transaction(date, type, category, amount, description); transactions.add(t); saveToFile(DEFAULT_FILE);`
- CSV header used when writing: `DATE,TYPE,CATEGORY,AMOUNT,DESCRIPTION` (keep exact for compatibility).

## Quick tests and checks an agent should run locally

- After code changes, run `mvn compile` and then `java -cp target/classes org.example.Main` and exercise menu options 1–4 manually.
- Verify `transactions.csv` is readable by `ExpenseTracker.loadFromFile` and that `saveToFile` preserves the header.

## When to ask the human

- If you plan to change the CSV format, ask whether backward compatibility with existing `transactions.csv` is required.
- If adding new public APIs or modules, ask whether package visibility should be changed for `Transaction`/`ExpenseTracker`.

---

If any part of the CSV format, date handling, or autosave behavior is unclear, tell me which area you want clarified (e.g., "I plan to switch to quoted CSVs" or "I want transactions stored in JSON instead").
