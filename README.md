# JavaUtils

## Releases

[latest release](/releases/latest)

## JavaDoc

https://luka5w.github.io/JavaUtils/javadoc

## Content

- **CLI**
  - **CLIUtils**
    - Logging a message or throwable and exiting the program with a specific status code.
  - **Logger**: A class for logging (to CLI only) with child process/ child logger support.
  - **Prompt**: Prompting the user (query Y/N or prompt anything).
- **Data**
  - **FileUtils**
    - Creating a file and all parent directories.
    - Erasing the content of a file.
    - Reading data from a file.
    - Reading data after a specific char from a file.
    - Reading data before a sepcific char from a file.
    - Writing data to a file.
  - **Utils**
    - Checking whether a String can be interpretedas true or false.
- **Encryption**
  - **HashedPassword**
    - Hashing passwords with a salt
    - Comparing hashed passwords with raw passwords
    - Export a hashed password and salt to a String.
    - Import a hashed password and salt from a String.
- **Exception**
  - **IncomparableException**: When two instances can't be compared with each other.
  - **PreconditionNotMetException**: When a required precondition is not met.
  - **TooMuchIterationException**: When a loop iterates too much.

## License

Do what ever you want, this project is licensed under the [Unlicense](https://unlicense.org).