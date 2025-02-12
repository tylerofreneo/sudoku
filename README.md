# My Personal Project
# Sudoku


### *What will the application do?*

Display a **Sudoku** board with the row and column numbers, and allow users to enter numbers into the board. The application should reject all non-valid numbers entered, then display when the board is solved. It should also allow users to choose from a variety of puzzles, then track which puzzle the user has completed. 

### *Who will use it?*

- Sudoku enjoyers
- Puzzle solvers
- Thinkers

### *Why is this project of interest to you?*

I love Sudoku (though I'm not very good)

### User Stories

- As a user, I want to be able to add multiple custom Sudoku boards to the list of puzzles
- As a user, I want to be able to view all of the Sudoku boards in my list of puzzles
- As a user, I want to be able to change the difficulty of a puzzle
- As a user, I want to be able to select a puzzle and see that Sudoku board
- As a user, I want to be able to add numbers to the board, then see the new board
- As a user, I want to be able to see when I completed the board correctly
- As a user, I want to be able to see which puzzles I have completed, and which I have yet to complete
- As a user, I want to be able to see the row and column numbers when I see the board

- As a user, I want to be able to be able to save my Sudoku boards (if I so choose) 
- As a user, I want to be able to load my Sudoku boards from file (if I so choose)

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple boards to a list of puzzles" by running the program, pressing the 'Main' menu, pressing 'Add a puzzle', entering the board numbers (81 digits, with 0 as empty spaces) and the difficulty then pressing done. You can do this many times.
- You can generate the second required action related to the user story "view all boards in list of puzzles" by pressing the 'Main' menu, pressing 'View all puzzles'
- You can locate my visual component by doing the same actions as the previous step. The sudoku boards are displayed in a grid. Also, a winning image is displayed when viewing a board that has been completed.
- You can save the state of my application by pressing 'Main', 'Save boards to file'
- You can reload the state of my application by pressing 'Main', 'Load boards from file'
- You can change the difficulty of any given board by pressing 'Main', 'View all puzzles', choosing the tab of the board you would like to change, 'edit', 'Change current board difficulty', and entering the new board difficulty.
- You can add a number to any given board by pressing 'Main', 'View all puzzles', choosing the tab of the board you would like to change, 'edit', 'Add number', then entering the column, row, and number you would like to add. For example, to enter '9' to the third column fourth row, enter: 349.
- You can view all unfinished puzzles by pressing menu item 'Main', then menu item 'View all unfinished puzzles'.
- You can view all finished puzzles by pressing menu item 'Main', then menu item 'View all finished puzzles'.

### Phase 4: Task 2

Sat Aug 03 19:21:20 PDT 2024
Added board to sudoku book with difficulty Easy
Sat Aug 03 19:21:28 PDT 2024
Set Easy board to difficulty: Medium
Sat Aug 03 19:21:38 PDT 2024
Added board to sudoku book with difficulty Hard
Sat Aug 03 19:21:46 PDT 2024
Added number: 6
        At (col, row): (7, 9) to Hard board.
Sat Aug 03 19:21:53 PDT 2024
Added number: 7
        At (col, row): (8, 9) to Hard board.
Sat Aug 03 19:22:01 PDT 2024
Added number: 8
        At (col, row): (9, 9) to Hard board.
Sat Aug 03 19:22:01 PDT 2024
Completed Hard board.

### Phase 4: Task 3

If I were to refactor my project, the first place I would do it is in the SudokuGUI class. Currently the class is set up such that every time a new window is created, I lose access to the JFrame object that was used to create the old window. This is problematic since I have no way of updating the window after it has been made, the only way to see the new data after it has been updated is by creating a new window. Of course, creating a new window everytime the data changes isn't ideal, so I would refactor this class by creating a new class for each new window. For example, I could make a new class BoardViewGUI that extends JFrame that would be called when the user wants to view all the boards. 