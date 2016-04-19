package reversi;

public class BoardChangedEvents {

    public static BoardChangedEvent RESET = new ResetEvent();
    public static BoardChangedEvent PLAYER_CHANGED = new PlayerChangedEvent();

    public static class ResetEvent implements BoardChangedEvent {
    }

    public static class PlayerChangedEvent implements BoardChangedEvent {
    }

    public static class LocationChanged implements BoardChangedEvent {
        BoardLocation location;

        public LocationChanged(BoardLocation location) {
            this.location = location;
        }

        public BoardLocation getLocation() {
            return location;
        }
    }
}
