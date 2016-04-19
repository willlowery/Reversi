package reversi;

/**
 * Created by tuurk on 4/18/2016.
 */
class Location implements BoardLocation {

    private int row;
    private int column;

    public Location(int row, int column) {
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
