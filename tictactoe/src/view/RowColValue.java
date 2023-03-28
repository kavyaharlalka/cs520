package view;

public class RowColValue {
    private int row = -1;
    private int col = -1;

    public RowColValue(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowValue() {
        return row;
    }

    public int getColValue() {
        return col;
    } 
}
