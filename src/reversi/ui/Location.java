package reversi.ui;

import reversi.board.BoardLocation;

class Location implements BoardLocation {
    int row;
    int column;

    Location(int row, int column) {
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
