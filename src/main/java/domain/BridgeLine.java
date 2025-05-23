package domain;

import java.util.List;

public class BridgeLine {

    private final List<Boolean> connections;

    public BridgeLine(List<Boolean> connections) {
        this.connections = connections;
    }

    public boolean isConnected(int index) {
        return connections.get(index);
    }

    public int size() {
        return connections.size();
    }

    public List<Boolean> connections() {
        return connections;
    }
}
