# PathBlocker

A Java playground for experimenting with classic **path‑finding algorithms** on grid‑based “maze” levels.
The project comes with a tiny interactive game/visualiser where you control (or let the algorithm control) a yellow agent that tries to reach a black‑and‑white goal flag while obstacles constantly conspire to block the way.

---

## ✨ Key features

| Feature                      | Description                                                                                                                                                       |
| ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Two grid modes               | **Flat Grid** – every cell has the same traversal cost.<br>**Altitude Grid** – pyramidal “hills” give each cell a movement cost proportional to height.           |
| Multiple search algorithms   | *Flat Grid*: **Depth‑First Search (DFS)** and **Breadth‑First Search (BFS)**.<br>*Altitude Grid*: **A\*** (with Manhattan or Euclidean heuristic – configurable). |
| Live visualisation           | Watch the open / closed node sets grow cell by cell and see the final path traced back.                                                                           |
| Level files                  | Levels are plain text, so you can design your own mazes with any editor.                                                                                          |
| Pluggable heuristics & costs | Just implement the `Heuristic` and `CostModel` interfaces and drop them in.                                                                                       |
| Unit‑test friendly           | Core logic is algorithm‑only – no UI dependencies. JUnit tests included.                                                                                          |
| Lightweight                  | Pure Java 17, no external dependencies; runs on Windows, macOS, Linux.                                                                                            |

---

## 📸 Screenshots

| Flat grid (BFS)                                    | Altitude grid (A\*)                                        |
| -------------------------------------------------- | ---------------------------------------------------------- |
| ![Flat grid screenshot](https://github.com/user-attachments/assets/6a8b9b2f-720f-4313-9910-9e285cd2e571) | ![Altitude grid screenshot](https://github.com/user-attachments/assets/5298c711-9862-4b8c-9704-71ddb964e458)|![0001]


---

## ⏱️ Algorithms in a nutshell

| Algorithm | Grid type | Notes                                                  |
| --------- | --------- | ------------------------------------------------------ |
| BFS       | Flat      | Uses a FIFO queue; explores layers outward.            |
| DFS       | Flat      | Simple stack‑based exploration; mainly for comparison. |
| A\*       | Altitude  | Priority queue ordered by *f(n) = g(n)+h(n)*.          |

### Heuristic functions for A\*

| Name      | Formula        | When to use                       |   |    |    |                                     |
| --------- | -------------- | --------------------------------- | - | -- | -- | ----------------------------------- |
| Manhattan | \`             | dx                                | + | dy | \` | 4‑way movement, cheap & admissible. |


---

 🚀 Getting started

### 1. Clone & build

<details>
<summary>Gradle (recommended)</summary>

```bash
git clone https://github.com/your‑username/pathblocker.git
cd pathblocker
./gradlew run            # downloads JDK 17 toolchain automatically
```

</details>

<details>
<summary>Maven</summary>

```bash
git clone https://github.com/your‑username/pathblocker.git
cd pathblocker
mvn javafx:run
```

</details>

### 2. Play

1. The start menu lets you pick **Flat** or **Altitude** grid.
2. Choose an algorithm:
   *Flat grid ➜* BFS or DFS
   *Altitude grid ➜* A\* (pick heuristic)
3. Click **Start** – the solver will animate its search.
   • **Space** pauses/resumes
   • **N** advances one step (great for teaching)
   • **R** reloads the same level
   • **L** opens the level picker

---

## 🛠️ Extending the project

* **Add a new algorithm**:

  1. Create a package under `search/youralgo`.
  2. Implement the `SearchAlgorithm` interface.
  3. Register it in `SearchRegistry`.

* **Custom cost models or heuristics**:
  Implement `CostModel` or `Heuristic`, drop the class on the classpath, and select it from the UI.

* **Make bigger levels**:
  Level files are simple ASCII art:

  ```text
  ##########
  #S     ###   S = start
  #   ##   #   G = goal
  #  ^^^   #   # = wall
  #   G    #   ^ = altitude (height increases with count)
  ##########
  ```


![0001](https://github.com/user-attachments/assets/552cb565-a287-465d-823d-99aeedd0f459)
![0002](https://github.com/user-attachments/assets/61fd5a69-0a16-4fad-b5fc-c96dfed28dce)
![0025](https://github.com/user-attachments/assets/67a8ed67-8cd6-468f-a3b7-1379881a7015)
![0026](https://github.com/user-attachments/assets/eb64da32-8849-438a-840e-b6d981148f6c)
![0018](https://github.com/user-attachments/assets/08247e6f-539a-4b33-841f-dfcc2408674d)

![0001](https://github.com/user-attachments/assets/2e77373c-434c-48bd-a699-d27113a28082)
![0002](https://github.com/user-attachments/assets/87a6015a-1e1f-44a0-b741-d8c352d34e31)
![0017](https://github.com/user-attachments/assets/e5b01d52-fff8-4dd3-a98a-6a8c2ef60ce1)
