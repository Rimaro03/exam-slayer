# Exam Slayer

--- 
# Documentation [here](https://exam-slayer.it)
---

Exam Slayer is a 2D graphical adventure game set in an imaginary dungeon. The goal is to graduate by defeating bosses that represent exams.

## How to Play

- **Objective:** Pass four exams by finding and defeating the bosses.
- **Controls:**
  - **Move:** `W` (forward), `A` (left), `S` (backward), `D` (right)
  - **Attack:** `Left arrow` (left), `Right arrow` (right), `Up arrow` (up), `Down arrow` (down)
  - **General:** `ESC` (pause), `Backspace` (delete save)

## Installation and Execution

Download the source from [here](https://github.com/UNI-projects-team/exam-slayer).

To compile the game yourself:

```sh
git clone https://github.com/UNI-projects-team/exam-slayer
cd exam-slayer
mvn clean compile assembly:single
java -jar target/exam-slayer-1.0.0-jar-with-dependencies.jar
```

## Technologies Used
- Lombok 1.18.32
- JUnit 4.13.2
- Maven 3.9.6
- Java Swing 1.4.2
- Google Cloud 2.39.0
- Mockito 5.12.2


## Setup Bucket
The game data and save files are stored in a Google Cloud bucket. Access credentials are stored in `./resources/bucket/bucket_key.json`.