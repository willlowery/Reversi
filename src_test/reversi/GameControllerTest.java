package reversi;

import org.junit.Test;
import reversi.board.BoardModel;
import reversi.board.Location;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerTest {

    private static final Player L = Player.LIGHT;
    private static final Player D = Player.DARK;
    private static final Player U = Player.NEUTRAL;

    @Test
    public void testSimple_Moves() {
        MockModel model = new MockModel();
        GameController controller = new GameController(model);

        model.setCurrentPlayer(Player.DARK);
        Player[][] board = board(
                row(U, U, U, U, U),
                row(U, L, L, L, U),
                row(U, L, D, L, U),
                row(U, L, L, L, U),
                row(U, U, U, U, U)
        );
        model.set(board);

        assertTrue(controller.isValidMove(L(0, 2), Player.DARK));
        assertTrue(controller.isValidMove(L(0, 4), Player.DARK));
        assertTrue(controller.isValidMove(L(2, 4), Player.DARK));
        assertTrue(controller.isValidMove(L(4, 4), Player.DARK));
        assertTrue(controller.isValidMove(L(4, 2), Player.DARK));
        assertTrue(controller.isValidMove(L(2, 0), Player.DARK));
        assertTrue(controller.isValidMove(L(0, 0), Player.DARK));
    }


    @Test
    public void testLongMove() {
        MockModel model = new MockModel();
        GameController controller = new GameController(model);

        model.setCurrentPlayer(Player.DARK);
        Player[][] board = board(
                row(U, L, L, L, D),
                row(U, U, U, U, U),
                row(U, U, U, U, U),
                row(U, U, U, U, U),
                row(U, U, U, U, U)
        );
        model.set(board);

        assertTrue(controller.isValidMove(L(0, 0), Player.DARK));
        assertFalse(controller.isValidMove(L(1, 4), Player.DARK));
    }

    private Location L(int row, int column) {
        return new Location(row, column);
    }


    private Player[][] board(Player[]... rows) {
        return rows;
    }

    private Player[] row(Player... row) {
        return row;
    }

}

class MockModel extends BoardModel {

    public MockModel() {
        super(0);

    }

    public void set(Player[][] board) {
        this.board = board;
        this.size = board.length;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    @Override
    public void resetBoard() {
    }
}