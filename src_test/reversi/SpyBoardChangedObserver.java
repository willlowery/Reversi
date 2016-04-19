package reversi;

import java.util.ArrayList;
import java.util.List;

class SpyBoardChangedObserver implements BoardObserver {

    public List<BoardChangedEvent> events = new ArrayList<>();

    @Override
    public void notify(BoardChangedEvent event) {
        events.add(event);
    }
}
