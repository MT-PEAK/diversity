package com.peak.diversityScreen.impl.particle.attachment;

import java.util.function.Supplier;

public class ScreenPointAttachment {

    private final Supplier<float[]> positionSupplier;

    public ScreenPointAttachment(Supplier<float[]> positionSupplier) {
        this.positionSupplier = positionSupplier;
    }

    public static ScreenPointAttachment fixed(float x, float y) {
        return new ScreenPointAttachment(() -> new float[]{x, y});
    }

    public static ScreenPointAttachment dynamic(Supplier<float[]> positionSupplier) {
        return new ScreenPointAttachment(positionSupplier);
    }

    public float getX() {
        return positionSupplier.get()[0];
    }

    public float getY() {
        return positionSupplier.get()[1];
    }

    public float[] getPosition() {
        return positionSupplier.get();
    }
}