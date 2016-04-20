package reversi.ui;

import reversi.board.BoardLocation;

import javax.swing.*;

class Button extends JButton implements BoardLocation {

    int row;
    int column;

    public Button(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
