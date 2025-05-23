package domain;

import java.util.List;

public class BridgeLine {

    private final List<Boolean> horizontalConnections;

    public BridgeLine(List<Boolean> horizontalConnections) {
        this.horizontalConnections = horizontalConnections;
    }

    public boolean isConnectedAt(int index) {
        return horizontalConnections.get(index);
    }

    public int width() {
        return horizontalConnections.size();
    }

    public List<Boolean> getConnections() {
        return horizontalConnections;
    }
}
