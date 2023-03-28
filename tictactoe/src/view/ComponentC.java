package view;

import javax.swing.JTextArea;

import model.RowGameModel;

public class ComponentC implements View {
    private JTextArea playerturn = new JTextArea();

    public ComponentC() {
        playerturn.setText("Player 1 to play 'X'");
    }

    @Override
    public void update(RowGameModel model, int row, int column) {
        if (model.getFinalResult() != null) {
            this.playerturn.setText(model.getFinalResult());
        }
        else if(model.movesLeft%2 == 1) {
            this.playerturn.setText("'X': Player 1");
        } else{
            this.playerturn.setText("'O': Player 2");
        }
    }
    
    public JTextArea getPlayerTurn() {
        return this.playerturn;
    }
}
