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

    public void endGame() {
        undo.setEnabled(false);
        playerToRowColValueMap.put(Player.Player_1, null);
        playerToRowColValueMap.put(Player.Player_2, null);
    }

    @Override
    public void update(RowGameModel model, int row, int column) {
        if (row >= 0) {
            String blockContent = model.blocksData[row][column].getContents();
            if (blockContent.isEmpty()) {
                resetRowColValForPlayerForGivenRowColValue(Player.Player_1, row, column);
                resetRowColValForPlayerForGivenRowColValue(Player.Player_2, row, column);
                undo.setEnabled(false);
            }
            else {
                playerToRowColValueMap.put(model.getCurrentPlayer(), new RowColValue(row, column));
                undo.setEnabled(model.movesLeft <= 7);
            }
        }
        else {           
            undo.setEnabled(false);
        }
    }

    private void resetRowColValForPlayerForGivenRowColValue(Player player, int row, int column) {
        RowColValue playerPrevMoveRowColValue = playerToRowColValueMap.get(player);
        if (playerPrevMoveRowColValue != null && playerPrevMoveRowColValue.getRowValue() == row && playerPrevMoveRowColValue.getColValue() == column) {
            playerToRowColValueMap.put(player, null);
        }
    }
}
