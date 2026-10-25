package com.cpu.cpumaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SiliconRoots extends ApplicationAdapter {

private SpriteBatch batch;
private BitmapFont font;

private String errorMessage = null;
private boolean initialized = false;

@Override
public void create() {
    Gdx.app.log("SiliconRoots", "Starting game...");

    try {
        batch = new SpriteBatch();
        font = new BitmapFont();

        font.setColor(Color.WHITE);
        initialized = true;

        Gdx.app.log("SiliconRoots", "Game initialized successfully!");

    } catch (Exception e) {
        errorMessage = "Initialization error:\n"
                + e.getClass().getSimpleName()
                + "\n"
                + String.valueOf(e.getMessage());

        Gdx.app.error("SiliconRoots", errorMessage, e);

        dispose();
    }
}

@Override
public void render() {
    try {
        ScreenUtils.clear(0.10f, 0.12f, 0.17f, 1f);

        if (batch == null || font == null) {
            return;
        }

        batch.begin();

        if (errorMessage != null) {
            font.setColor(Color.RED);
            font.draw(
                    batch,
                    errorMessage,
                    20,
                    Gdx.graphics.getHeight() - 30,
                    Gdx.graphics.getWidth() - 40,
                    8,
                    true
            );

        } else if (initialized) {
            font.setColor(Color.WHITE);

            font.draw(
                    batch,
                    "Silicon Roots",
                    30,
                    Gdx.graphics.getHeight() - 50
            );

            font.setColor(Color.GREEN);

            font.draw(
                    batch,
                    "Game started successfully!",
                    30,
                    Gdx.graphics.getHeight() - 90
            );

            font.setColor(Color.LIGHT_GRAY);

            font.draw(
                    batch,
                    "LibGDX Android test",
                    30,
                    Gdx.graphics.getHeight() - 130
            );
        }

        batch.end();

    } catch (Exception e) {
        Gdx.app.error("SiliconRoots", "Rendering error", e);

        if (batch != null && batch.isDrawing()) {
            batch.end();
        }

        errorMessage = "Rendering error:\n"
                + e.getClass().getSimpleName()
                + "\n"
                + String.valueOf(e.getMessage());

        initialized = false;
    }
}

@Override
public void resize(int width, int height) {
    Gdx.app.log(
            "SiliconRoots",
            "Screen size: " + width + "x" + height
    );
}

@Override
public void pause() {
    Gdx.app.log("SiliconRoots", "Game paused");
}

@Override
public void resume() {
    Gdx.app.log("SiliconRoots", "Game resumed");
}

@Override
public void dispose() {
    if (batch != null) {
        batch.dispose();
        batch = null;
    }

    if (font != null) {
        font.dispose();
        font = null;
    }

    initialized = false;

    Gdx.app.log("SiliconRoots", "Resources released");
}

}
