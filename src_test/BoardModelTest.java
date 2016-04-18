import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class BoardModelTest {

    private static final Player L = Player.LIGHT;
    private static final Player D = Player.DARK;
    private static final Player U = Player.NEUTRAL;

    @Test
    public void testBoardSetup() {
        BoardModel boardModel = new BoardModel(8);
        boardModel.resetBoard();


        Player[][] expected = board(
                row(U, U, U, U, U, U, U, U),
                row(U, U, U, U, U, U, U, U),
                row(U, U, U, U, U, U, U, U),
                row(U, U, U, L, D, U, U, U),
                row(U, U, U, D, L, U, U, U),
                row(U, U, U, U, U, U, U, U),
                row(U, U, U, U, U, U, U, U),
                row(U, U, U, U, U, U, U, U)
        );

        assertBoardState(expected, boardModel);
        assertThat(boardModel.getCurrentPlayer(), is(Player.DARK));
    }

    @Test
    public void testMakeTurn_expectsCurrentPlayerChanges(){
        BoardModel boardModel = new BoardModel(8);
        boardModel.resetBoard();

        boardModel.makeTurn();
        assertThat(boardModel.getCurrentPlayer(), is(Player.LIGHT));

        boardModel.makeTurn();
        assertThat(boardModel.getCurrentPlayer(), is(Player.DARK));

        boardModel.makeTurn();
        assertThat(boardModel.getCurrentPlayer(), is(Player.LIGHT));

        boardModel.makeTurn();
        assertThat(boardModel.getCurrentPlayer(), is(Player.DARK));
    }



    private void assertBoardState(Player[][] expected, BoardModel actual) {
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected.length; col++) {
                assertTrue(
                        String.format("Location (%d,%d) was %s but expected %s", row, col, actual.get(location(row, col)), expected[row][col]),
                        actual.isOccupied(location(row, col), expected[row][col])
                );
            }
        }
    }


    private Player[][] board(Player[]... rows) {
        return rows;
    }

    private Player[] row(Player... row) {
        return row;
    }

    private Location location(int row, int column) {
        return new Location(row, column);
    }

}

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
