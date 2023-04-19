package view;

import java.awt.*;
import java.awt.event.*;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameController;
import model.Player;
import model.RowGameModel;

public class ComponentUndo implements View {
    private JButton undo = new JButton("Undo");
    private Map<Player, RowColValue> playerToRowColValueMap = new EnumMap<Player, RowColValue>(Player.class);

    /**
     * Initialize the undo component
     */
    public void init(JPanel options, RowGameController controller) {
        playerToRowColValueMap.put(Player.Player_1, null);
        playerToRowColValueMap.put(Player.Player_2, null);
        undo.setEnabled(false);

        options.add(undo);
        
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.undoLastMove(playerToRowColValueMap);
            }
        });
    }

    /**
     * When game has ended, disable the undo button and reset player last move map
     */
    public void endGame() {
        undo.setEnabled(false);
        playerToRowColValueMap.put(Player.Player_1, null);
        playerToRowColValueMap.put(Player.Player_2, null);
    }

    /**
     * This update function is used to store the last move made my each player to allow undoing it.
     */
    @Override
    public void update(RowGameModel model, int row, int column) {
        // Ensure final result is not being set (game is not over), in which case row and column is -1
        if (row >= 0) {
            String blockContent = model.blocksData[row][column].getContents();
            
            // In case content is being emptied, it is undo being called
            if (blockContent.isEmpty()) {
                resetRowColValForPlayerForGivenRowColValue(Player.Player_1, row, column);
                resetRowColValForPlayerForGivenRowColValue(Player.Player_2, row, column);
                undo.setEnabled(false);
            }
            else {
                // Store the last move of the current player
                playerToRowColValueMap.put(model.getCurrentPlayer(), new RowColValue(row, column));

                // Only enable undo when each player has made atleast 1 move
                undo.setEnabled(model.movesLeft <= 7);
            }
        }
        else {       
            // Game over hence disable undo    
            undo.setEnabled(false);
        }
    }

    /**
     * Removes last move data of the player if it matches the given row and column
     */
    private void resetRowColValForPlayerForGivenRowColValue(Player player, int row, int column) {
        RowColValue playerPrevMoveRowColValue = playerToRowColValueMap.get(player);
        if (playerPrevMoveRowColValue != null && playerPrevMoveRowColValue.getRowValue() == row && playerPrevMoveRowColValue.getColValue() == column) {
            playerToRowColValueMap.put(player, null);
        }
    }

    /**
     * Gives current status of the undo button 
     */
    public boolean getButtonUndoStatus(){
        return undo.isEnabled();
    }
}
