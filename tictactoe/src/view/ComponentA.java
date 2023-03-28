package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameController;
import model.RowGameModel;

public class ComponentA implements View {
    public JButton[][] blocks = new JButton[3][3];

    public void init(RowGameController controller, JPanel game) {
        // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<3; row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			            controller.move((JButton)e.getSource());
                    }
                });
            }
        }
    }

    public boolean checkIfBlockAtRowCol(JButton block, int row, int column) {
        return blocks[row][column] == block;
    }

    public void endGame() {
	for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                blocks[row][column].setEnabled(false);
            }
        }
    }

    @Override
    public void update(RowGameModel model, int row, int column) {
        if (row >= 0) {
            blocks[row][column].setText(model.blocksData[row][column].getContents());
            blocks[row][column].setEnabled(model.blocksData[row][column].getIsLegalMove());
        }
    }
    
}
