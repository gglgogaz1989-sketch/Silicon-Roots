
package com.cpu.cpumaker.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public final class Resize {

    private static final float DEFAULT_OFFSET = 40f;

    private Resize() {
    }

    public static void applyPixelFilter(Texture texture) {
        if (texture == null) {
            throw new IllegalArgumentException(
                "Texture cannot be null."
            );
        }

        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );
    }

    public static Rectangle getScaledBounds(
        float baseWidth,
        float baseHeight,
        float scaleMultiplier,
        float screenW,
        float screenH
    ) {
        return getScaledBounds(
            baseWidth,
            baseHeight,
            scaleMultiplier,
            screenW,
            screenH,
            DEFAULT_OFFSET
        );
    }

    public static Rectangle getScaledBounds(
        float baseWidth,
        float baseHeight,
        float scaleMultiplier,
        float screenW,
        float screenH,
        float verticalOffset
    ) {
        if (!isFinite(baseWidth)
            || !isFinite(baseHeight)
            || !isFinite(scaleMultiplier)
            || !isFinite(screenW)
            || !isFinite(screenH)
            || !isFinite(verticalOffset)) {
            throw new IllegalArgumentException(
                "Resize values must be finite."
            );
        }

        if (baseWidth < 0 || baseHeight < 0
            || scaleMultiplier < 0
            || screenW < 0 || screenH < 0) {
            throw new IllegalArgumentException(
                "Dimensions cannot be negative."
            );
        }

        float width = baseWidth * scaleMultiplier;
        float height = baseHeight * scaleMultiplier;
        float x = (screenW - width) / 2f;
        float y = (screenH - height) / 2f
            + verticalOffset;

        return new Rectangle(x, y, width, height);
    }

    private static boolean isFinite(float value) {
        return !Float.isNaN(value)
            && !Float.isInfinite(value);
    }
}
