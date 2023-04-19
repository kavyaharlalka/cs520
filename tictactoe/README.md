# TicTacToe

### How to build and test (from Terminal):

1. Make sure that you have Apache Ant installed. Run each ant command in the tictactoe folder, which contains the `build.xml` build file.

2. Run `ant document` to generate the javadoc (a hypertext description) for all of the java classes. Generated hypertext description will be in the `jdoc` folder. Open the `index.html` file. 

3. Run `ant compile` to compile all of the java classes. Compiled classes will be in the `bin` folder.

4. Run `ant test` to run all unit tests.

### How to run (from Terminal):

1. After building the project (i.e., running `ant`), run the following command in the tictactoe folder:
   `java -cp bin RowGameApp`

### How to clean up (from Terminal):

1. Run `ant clean` to clean the project (i.e., delete all generated files).

### New Feature - Undo functionality

* Each player can undo only their last move using the undo button. The undo button remains disabled till each player has made atleast one move.
* If a player use the undo functionality (clicks on the undo button), just that player's previous move is undone and then the undo button is disabled. 
* The player now has to play their turn by placing their icon `x` or `o` anywhere on the board. Thus, undo does not cause the player's turn to be skipped.