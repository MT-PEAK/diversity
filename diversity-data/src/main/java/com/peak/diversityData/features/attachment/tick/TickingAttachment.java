package com.peak.diversityData.features.attachment.tick;

public interface TickingAttachment extends ClientTickingAttachment, ServerTickingAttachment {
    default void clientTick() {
        this.tick();
    }

    default void serverTick() {
        this.tick();
    }

    void tick();
}
