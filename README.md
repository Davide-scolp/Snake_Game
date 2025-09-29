# Snake Game - PMO Project

## Description
This is a simple Java application implementing the classic arcade game Snake.  
The player controls a snake moving on a grid to collect apples while avoiding obstacles, traps, and fireballs. The goal is to complete three levels by collecting 5, 10, and 15 apples respectively, while managing collisions and game rules.

## Functional Requirements
- Smooth and responsive snake movement that prohibits instant reversals to avoid self-collision.
- Snake grows when eating apples and shrinks upon collision with specific game objects.
- Support for game sessions with three progressive levels.
- Inclusion and management of game objects such as apples, obstacles, traps, and fireballs.
- Game menu to start new sessions, adjust sound settings, or exit.
- Game over panel offering options to restart or return to the main menu.
- Efficient global management of sounds and textures using the Singleton pattern.

## Architecture
The software follows the Model-View-Controller (MVC) architectural pattern:
- **Model:** Handles game logic and state, mainly through the `Board` class.
- **View:** Handles graphical presentation via `GamePanel`, `GameMenu`, and `GameOverPanel`.
- **Controller:** Manages input and game updates using `GameController` that listens to keyboard events and a Swing timer.

## Design Details
- Centralized resource management for sounds and textures using Singleton with the initialization-on-demand holder idiom for thread safety and lazy initialization.
- The snake's movement is controlled using a Swing timer, direction managed by an Enum, and input buffered to avoid conflicts.

## Testing
JUnit automated tests cover snake movement, collisions, traps, fireballs, and timer functionality. These tests proved invaluable for catching bugs and ensuring stable gameplay.

## Development Approach
Development started with core elements (snake, apple, game board) to build a functional skeleton. Menus and game over screens were implemented next. Finally, additional game objects, level progression, collision handling, sounds, and textures were integrated.  
Advanced Java features such as Optional, Stream API, and lambda expressions enhance code readability and robustness.

## Usage
1. Clone the repository  
2. Compile the source code using `javac` or a build tool  
3. Run the main class to start the game

---

Project developed by Davide Scolpati for the Object Oriented Programming and Modelling (PMO) course.
