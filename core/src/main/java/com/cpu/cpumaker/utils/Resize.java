package com.cpu.cpumaker.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Resize {
    public static void applyPixelFilter(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public static Rectangle getScaledBounds(float baseWidth, float baseHeight, float scaleMultiplier, float screenW, float screenH) {
        float width = baseWidth * scaleMultiplier;
        float height = baseHeight * scaleMultiplier;
        float x = (screenW - width) / 2f;
        float y = (screenH - height) / 2f + 40f;
        return new Rectangle(x, y, width, height);
    }
}
