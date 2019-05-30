# Contribution Guidelines

## Table of Contents

- 1 - [Commit Message Guidelines](CONTRIBUTING.md/#commit-message-guidelines)
- 2 - [Merge Request Guidelines](CONTRIBUTING.md/#merge-request-guidelines)
- 3 - [Code Style Guidelines](CONTRIBUTING.md/#code-style-guidelines)

## Commit Message Guidelines

- Commit messages must begin with the issue number (if applicable) in square brackets.

- The issue number can be ommited, when the commit does not relate to a specific issue.

- Commit messages must be written in present-tense.

- If more detail is required, add a paragraph after the message seperated by a new line character.

- There must not be puncuation in the commit message.

- Both the title and the body must use proper grammar.

  Example:

  ```
  [#71] Add basic set of Contribution Guidelines
  
  Create the basic set of coding style guidelines for the project.
  ```

## Merge Request Guidelines

- The title of a merge request must not contain an issue number in square brackets.

- The title must describe the goal of the merge request and summarize the changes made.

- The body of the merge request must describe the changes in further detail if needed.

- The body of the merge request must list the issues that the merge reqest will close.

  Example:

  ```
  Title: Start Android Studio Project
  
  Body:
  
  Creates a basic "EmptyActivity" Android Application. This will be the starting point for the Wave Music Player.
  
  Closes
  - #67
  ```

## Code Style Guidelines

### Indentation

- Use the default `TAB` character in Android Studio.

### Braces

- Place opening braces on a new line.
- Place closing braces on a new line.
  - The exception is empty code blocks, which can have their opening and closing on the same line.
- Braces must always be used, even around loops or if statements that only have a 1 line body.

### Variables

- Variables and functions must use `camelCase`.
- Constants must use `UPPERCASE_SEPERATED_WITH_UNDERSCORES`.
- Boolean variables must use a verb like `is` or `has`, which describes the boolean.

### Functions

- Functions must use `camelCase`.
- Function names must begin with a verb.
- Functions that are being overridden must use the `@Override` notation before the function signature, but after the function block comment.
- Functions that are being overloaded must place the new parameters after the set of existing parameters.

### Classes

- Classes must use `UpperCase`.

### Comments

- In-line comments must be a full sentence, with full punctuation and proper grammar.

- Block comment must be placed before the method signature.

  - This comment must describe the functionality of the method briefly.
  - This comment must describe the input parameters if any.
  - This comment must describe the return values if any.

  Example:

  ```java
  /**
   * Purpose: Checks if the given object is equal to this one.
   *
   * @param variable1 The desired object to compare.
   * @return A boolean that is true, if the two objects are equal.
   */
  public boolean equals(Object variable1)
  {
    //Do something
    return true;
  }
  ```