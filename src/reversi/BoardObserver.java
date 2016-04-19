package reversi;

public interface BoardObserver {

    void notify(BoardChangedEvent event);

}
