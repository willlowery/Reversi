package reversi;

import java.util.ArrayList;
import java.util.List;

public class BoardModelImpl {


    private Player[][] board;
    private final int size;
    private Player currentPlayer;
    private List<BoardObserver> observers;

    public BoardModelImpl(int size) {
        this.size = size;
        this.observers = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public void resetBoard() {
        setupBoard();
        currentPlayer = Player.DARK;

        notifyObservers(BoardChangedEvents.RESET);
    }

    private void setupBoard() {
        board = new Player[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                board[row][column] = Player.NEUTRAL;
            }
        }

        int topLeftRow = (size / 2) - 1;
        int topLeftCol = (size / 2) - 1;

        board[topLeftRow][topLeftCol] = Player.LIGHT;
        board[topLeftRow + 1][topLeftCol] = Player.DARK;
        board[topLeftRow][topLeftCol + 1] = Player.DARK;
        board[topLeftRow + 1][topLeftCol + 1] = Player.LIGHT;
    }

    public boolean isOccupied(BoardLocation location, Player player) {
        return board[location.getRow()][location.getColumn()].equals(player);
    }

    public boolean isUnoccupied(BoardLocation location) {
        return isOccupied(location, Player.NEUTRAL);
    }

    public Player get(BoardLocation location) {
        return board[location.getRow()][location.getColumn()];
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void makeTurn() {
        if (currentPlayer.equals(Player.DARK)) {
            currentPlayer = Player.LIGHT;
        } else {
            currentPlayer = Player.DARK;
        }

        notifyObservers(BoardChangedEvents.PLAYER_CHANGED);
    }

    public void occupy(BoardLocation location, Player player) {
        board[location.getRow()][location.getColumn()] = player;
        notifyObservers(new BoardChangedEvents.LocationChanged(location));
    }

    public void addObserver(BoardObserver observer) {
        this.observers.add(observer);
    }

    private void notifyObservers(BoardChangedEvent event) {
        for (BoardObserver observer : observers) {
            observer.notify(event);
        }

    }
}
