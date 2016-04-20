package reversi;

import reversi.board.BoardModel;
import reversi.ui.BoardView;

public class Main {
    public static void main(String[] args) {
        BoardModel model = new BoardModel(8);
        BoardView boardView = new BoardView(model, new GameController(model));
        model.addObserver(boardView);

        boardView.render();
        model.resetBoard();
    }
}
