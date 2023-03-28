package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import model.RowGameModel;
import controller.RowGameController;

public class RowGameGUI implements View {
    public JFrame gui = new JFrame("Tic Tac Toe");
    public RowGameModel gameModel = new RowGameModel();
    public JButton reset = new JButton("Reset");

    private ArrayList<View> componentList = new ArrayList<>();
    private ComponentA componentA = new ComponentA();

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(3,3));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        componentA.init(controller, game);
        componentList.add(componentA);

        ComponentC componentC = new ComponentC();
        componentC.init(messages);
        componentList.add(componentC);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param gameModel The RowGameModel containing the block
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    public void updateBlock(RowGameModel gameModel, int row, int column) {
        // if (row >= 0) {
        //     blocks[row][column].setText(gameModel.blocksData[row][column].getContents());
        //     blocks[row][column].setEnabled(gameModel.blocksData[row][column].getIsLegalMove());
        // }

        for (View component : componentList) {
            component.update(gameModel, row, column);
        }
    }

    @Override
    public void update(RowGameModel model, int row, int column) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public boolean checkIfBlockAtRowCol(JButton block, int row, int column) {
        return componentA.checkIfBlockAtRowCol(block, row, column);
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
        componentA.endGame();
    }
}
