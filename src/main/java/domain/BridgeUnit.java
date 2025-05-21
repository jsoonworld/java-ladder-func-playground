package domain;

public class BridgeUnit {

    private static final String CONNECTED = "-----";
    private static final String NOT_CONNECTED = "     ";

    private final boolean connected;

    public BridgeUnit(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public String draw() {
        if (connected) {
            return CONNECTED;
        }
        return NOT_CONNECTED;
    }
}
