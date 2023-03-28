package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Player;
import model.RowGameModel;

public class ComponentC implements View {
    private JTextArea playerturn = new JTextArea();

    public void init(JPanel messages) {
        playerturn.setText("Player 1 to play 'X'");
        playerturn.setEditable(false);
        messages.add(playerturn);
    }
    
    @Override
    public void update(RowGameModel model, int row, int column) {
        if (model.getFinalResult() != null) {
            this.playerturn.setText(model.getFinalResult());
        }
        else if (row < 0 || column < 0) {
            playerturn.setText("Player 1 to play 'X'");
        }
        else if(!model.blocksData[row][column].getContents().isEmpty()) {
            if(model.getCurrentPlayer() == Player.Player_2) {
                this.playerturn.setText("'X': Player 1");
            } else {
                this.playerturn.setText("'O': Player 2");
            }
        }
    }
}
