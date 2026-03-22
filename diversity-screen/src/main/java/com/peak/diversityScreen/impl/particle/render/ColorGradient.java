package com.peak.diversityScreen.impl.particle.render;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ColorGradient {

    private final List<Stop> stops = new ArrayList<>();

    public ColorGradient addStop(float t, float h, float s, float b, float alpha) {
        stops.add(new Stop(t, h, s, b, alpha));
        stops.sort(Comparator.comparingDouble(stop -> stop.t));
        return this;
    }

    public float[] evaluate(float t) {
        if (stops.isEmpty()) return new float[]{1, 1, 1, 1};
        if (stops.size() == 1) {
            Stop s = stops.get(0);
            return hsbToRgb(s.h, s.s, s.b, s.alpha);
        }

        if (t <= stops.get(0).t) {
            Stop s = stops.get(0);
            return hsbToRgb(s.h, s.s, s.b, s.alpha);
        }
        if (t >= stops.get(stops.size() - 1).t) {
            Stop s = stops.get(stops.size() - 1);
            return hsbToRgb(s.h, s.s, s.b, s.alpha);
        }

        Stop a = null, b = null;
        for (int i = 0; i < stops.size() - 1; i++) {
            if (t >= stops.get(i).t && t <= stops.get(i + 1).t) {
                a = stops.get(i);
                b = stops.get(i + 1);
                break;
            }
        }

        float local = (t - a.t) / (b.t - a.t);
        float h = lerpAngle(a.h, b.h, local);
        float s = lerp(a.s, b.s, local);
        float br = lerp(a.b, b.b, local);
        float alpha = lerp(a.alpha, b.alpha, local);
        return hsbToRgb(h, s, br, alpha);
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private float lerpAngle(float a, float b, float t) {
        float diff = b - a;
        if (diff > 180) diff -= 360;
        if (diff < -180) diff += 360;
        return (a + diff * t + 360) % 360;
    }

    private float[] hsbToRgb(float h, float s, float b, float alpha) {
        int rgb = Color.HSBtoRGB(h / 360f, s, b);
        float r = ((rgb >> 16) & 0xFF) / 255f;
        float g = ((rgb >> 8) & 0xFF) / 255f;
        float bl = (rgb & 0xFF) / 255f;
        return new float[]{r, g, bl, alpha};
    }

    public record Stop(float t, float h, float s, float b, float alpha) {}
}
