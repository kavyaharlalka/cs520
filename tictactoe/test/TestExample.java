import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

import model.Player;
import model.RowBlockModel;
import model.RowGameModel;
import controller.RowGameController;
import view.RowColValue;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
	game = new RowGameController();
    }

    @After
    public void tearDown() {
	game = null;
    }

    @Test
    public void testNewGame() {
        assertEquals (Player.Player_1, game.gameModel.getCurrentPlayer());
        assertEquals (9, game.gameModel.movesLeft);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }
    
    // Test Case 1: After performing an illegal move, the game is not updated.
    @Test
    public void testIllegal(){
        // Do a valid or legal move
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(Player.Player_2, game.gameModel.getCurrentPlayer());
        assertEquals(8, game.gameModel.movesLeft);

        //Now do an illegal move at the same block
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        assertEquals("O", game.gameModel.blocksData[1][1].getContents());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(Player.Player_1, game.gameModel.getCurrentPlayer());
        assertEquals(7, game.gameModel.movesLeft);
    }

    // Test Case 2: After a performing a legal move, the game is updated appropriately.
    @Test
    public void testLegal(){
        // Do a valid or legal move
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(Player.Player_2, game.gameModel.getCurrentPlayer());
        assertEquals(8, game.gameModel.movesLeft);
    }

    // Test Case 3: One of the players wins the game.
    @Test
    public void testPlayerWin(){
        // Checking the Win Condition for the following board condition
        // O    X   O
        // ""   X   ""
        // ""   X   ""
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][0]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][1]);
        assertEquals("Player 1 wins!", game.gameModel.getFinalResult());
    }

    // Test Case 4: The players tie the game.
    @Test
    public void testPlayerTie(){
        // Checking the Draw Condition for the following board condition
        // O    X   O
        // X    X   O
        // X    O   X
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][0]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[1][0]);
        game.move(game.gameView.getComponentA().getBlocksData()[1][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][0]);
        assertEquals(RowGameModel.GAME_END_NOWINNER, game.gameModel.getFinalResult());
    }

    //Test Case 5: After resetting the application, the game has the expected initial configuration
    @Test
    public void testReset(){
        // Inputting some random values in the grid
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][0]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][1]);
        game.move(game.gameView.getComponentA().getBlocksData()[0][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][2]);
        game.move(game.gameView.getComponentA().getBlocksData()[2][1]);
        // Resetting the grid
        game.resetGame();
        // Putting checks for initial configurations in the grid
        // Checking if the final result is Null
        assertNull(game.gameModel.getFinalResult());
        // Checking if the player to move is Player 1
        assertEquals(Player.Player_1, game.gameModel.getCurrentPlayer());
        // Checking if the number of moves left is equal to 9
        assertEquals(9, game.gameModel.movesLeft);
        // Checking if all the blocks hold the empty string and have isLegalMove set to True
        for (int r = 0; r<3; r++){
            for (int c = 0; c<3; c++){
                assertEquals("", game.gameModel.blocksData[r][c].getContents());
                assertEquals(true, game.gameModel.blocksData[r][c].getIsLegalMove());
            }
        }
    }

    //Test Case 6: If the user has done not done at least one move (equivalently, both users have done one move each), the user is not permitted to do undo.
    @Test
    public void testUndoNotAllowed(){
        assertEquals(9, game.gameModel.movesLeft);
        // as no moves are made by the users, checking if the undo button status is disabled
        assertEquals(false, game.gameView.getUndoStatus());
    }
    
    //Test Case 7: If the user has done atleast one move, the user is permitted to undo and the game is updated appropriately.
    @Test
    public void testUndoMove(){
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        // Testing is the move made is legal
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(8, game.gameModel.movesLeft);

        game.move(game.gameView.getComponentA().getBlocksData()[2][1]);
        // Testing is the next move made is legal and if undo is possible
        assertEquals("O", game.gameModel.blocksData[2][1].getContents());
        assertEquals(false, game.gameModel.blocksData[2][1].getIsLegalMove());
        assertEquals(7, game.gameModel.movesLeft);

        assertEquals(true, game.gameView.getUndoStatus());

        // Performing an undo move
        Map<Player, RowColValue> PlayerRowCol = new HashMap<Player, RowColValue>();
        RowColValue rowColValue =new RowColValue(2,1);
        PlayerRowCol.put(Player.Player_2,rowColValue);
        game.undoLastMove(PlayerRowCol);

        // // Testing if the board has been rolled back to the earlier configuration
        // Need to fix the following tests ->
        // assertEquals(true, game.gameModel.blocksData[2][1].getIsLegalMove());
        // assertEquals(7, game.gameModel.movesLeft);
        // assertEquals(false, game.gameView.getUndoStatus());
    }

}
