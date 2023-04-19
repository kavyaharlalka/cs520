import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

import model.Player;
import model.RowBlockModel;
import controller.RowGameController;

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
    public void TestLegal(){
        // Do a valid or legal move
        game.move(game.gameView.getComponentA().getBlocksData()[1][1]);
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(Player.Player_2, game.gameModel.getCurrentPlayer());
        assertEquals(8, game.gameModel.movesLeft);
    }

}
