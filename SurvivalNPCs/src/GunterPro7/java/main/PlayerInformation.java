package main;

public class PlayerInformation {
    private long listening;
    private NPC lastNPC;

    public boolean isListening() {
        if (listening == -1L) {
            return false;
        }

        return System.currentTimeMillis() - listening > 60_000;
    }

    public void setListening(long listening) {
        this.listening = listening;
    }

    public NPC getLastNPC() {
        return lastNPC;
    }

    public void setLastNPC(NPC lastNPC) {
        this.lastNPC = lastNPC;
    }
}
