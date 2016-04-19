package reversi;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class BoardModelTest {

    private static final Player L = Player.LIGHT;
    private static final Player D = Player.DARK;
    private static final Player U = Player.NEUTRAL;

    private BoardModelImpl boardModel;

    @Before
    public void setup() {
        boardModel = new BoardModelImpl(8);
        boardModel.resetBoard();
    }

    @Test
    public void testOccupy_TakesTile() {

        boardModel.occupy(location(0, 0), L);
        boardModel.occupy(location(0, 1), D);
        boardModel.occupy(location(0, 2), D);
        boardModel.occupy(location(0, 3), L);
        boardModel.occupy(location(7, 7), L);

        assertBoardState(
                board(
                        row(L, D, D, L, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, L, D, U, U, U),
                        row(U, U, U, D, L, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, L)
                )
        );
    }

    @Test
    public void testBoardSetup() {
        assertBoardState(
                board(
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, L, D, U, U, U),
                        row(U, U, U, D, L, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U),
                        row(U, U, U, U, U, U, U, U)
                )
        );
        assertThat(boardModel.getCurrentPlayer(), is(Player.DARK));
    }

    @Test
    public void testMakeTurn_expectsCurrentPlayerChanges() {
        BoardModelImpl boardModel = new BoardModelImpl(8);
        boardModel.resetBoard();

        assertPlayerAfterTurn(Player.LIGHT);
        assertPlayerAfterTurn(Player.DARK);
        assertPlayerAfterTurn(Player.LIGHT);
        assertPlayerAfterTurn(Player.DARK);
    }

    @Test
    public void testOccupy_triggersEvent() {
        SpyBoardChangedObserver spy = new SpyBoardChangedObserver();

        boardModel.addObserver(spy);
        boardModel.occupy(location(0, 0), D);

        assertThat(spy.events.size(), is(1));
        BoardChangedEvents.LocationChanged boardChangedEvent = (BoardChangedEvents.LocationChanged) spy.events.get(0);
        assertThat(boardChangedEvent.getLocation().getRow(), is(0));
        assertThat(boardChangedEvent.getLocation().getColumn(), is(0));
    }

    @Test
    public void testReset_triggersEvent() {
        SpyBoardChangedObserver spy = new SpyBoardChangedObserver();
        boardModel.addObserver(spy);
        boardModel.resetBoard();

        assertThat(spy.events.size(), is(1));
        assertThat(spy.events.get(0), instanceOf(BoardChangedEvents.ResetEvent.class));

    }

    @Test
    public void testMakeTurn_triggersEvent() {
        SpyBoardChangedObserver spy = new SpyBoardChangedObserver();
        boardModel.addObserver(spy);
        boardModel.makeTurn();

        assertThat(spy.events.size(), is(1));
        assertThat(spy.events.get(0), instanceOf(BoardChangedEvents.PlayerChangedEvent.class));

    }

    private void assertPlayerAfterTurn(Player player) {
        boardModel.makeTurn();
        assertThat(boardModel.getCurrentPlayer(), is(player));
    }


    private void assertBoardState(Player[][] expected) {
        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected.length; col++) {
                assertTrue(
                        String.format("Reversi Location (%d,%d) was %s but expected %s", row, col, boardModel.get(location(row, col)), expected[row][col]),
                        boardModel.isOccupied(location(row, col), expected[row][col])
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

