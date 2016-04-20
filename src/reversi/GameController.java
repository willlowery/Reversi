package reversi;

import reversi.board.BoardLocation;
import reversi.board.BoardModel;

public class GameController {

    private BoardModel model;

    public GameController(BoardModel model) {
        this.model = model;
    }

    public void occupy(BoardLocation location) {
        Player currentPlayer = model.getCurrentPlayer();
        if (isValidMove(location, currentPlayer)) {
            occupy(location, currentPlayer);
            model.makeTurn();
        }
    }

    public boolean isValidMove(BoardLocation location, Player player) {
        if (isOutOfBounds(location) || model.isOccupied(location)) {
            return false;
        }

        for (Cardinality direction : Cardinality.values()) {
            if (checkDirection(location, player, direction)) {
                return true;
            }
        }

        return false;
    }

    public void occupy(BoardLocation location, Player player) {
        model.occupy(location, player);
        for (Cardinality direction : Cardinality.values()) {
            if (checkDirection(location, player, direction)) {
                BoardLocation toTake = direction.apply(location);
                while (!model.isOccupied(toTake, player)) {
                    model.occupy(toTake, player);
                    toTake = direction.apply(toTake);
                }
            }
        }
    }

    private boolean checkDirection(BoardLocation location, Player player, Cardinality direction) {
        Player opponent = getOpponent(player);
        BoardLocation toCheck = direction.apply(location);

        if (isOutOfBounds(toCheck) || !model.isOccupied(toCheck, opponent)) {
            return false;
        }

        toCheck = direction.apply(toCheck);
        while (inBounds(toCheck) && model.isOccupied(toCheck, opponent)) {
            toCheck = direction.apply(toCheck);
        }

        return inBounds(toCheck) && model.isOccupied(toCheck, player);

    }

    private Player getOpponent(Player player) {
        return player.equals(Player.DARK) ? Player.LIGHT : Player.DARK;
    }

    private boolean inBounds(BoardLocation toCheck) {
        return !isOutOfBounds(toCheck);
    }


    private boolean isOutOfBounds(BoardLocation location) {
        return !(location.getRow() >= 0 &&
                location.getRow() < model.getSize() &&
                location.getColumn() >= 0 &&
                location.getColumn() < model.getSize());
    }

    private enum Cardinality {

        EAST(0, 1),
        NORTH(-1, 0),
        NORTH_EAST(-1, 1),
        NORTH_WEST(-1, -1),
        SOUTH(1, 0),
        SOUTH_WEST(1, -1),
        SOUTH_EAST(1, 1),
        WEST(0, -1);

        private int row;
        private int column;

        Cardinality(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public BoardLocation apply(BoardLocation location) {
            return new BoardLocation() {
                @Override
                public int getRow() {
                    return location.getRow() + row;
                }

                @Override
                public int getColumn() {
                    return location.getColumn() + column;
                }
            };
        }
    }
}