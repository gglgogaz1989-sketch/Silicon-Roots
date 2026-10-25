
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

    private float elapsed;
    private boolean ready;

    @Override
    public void create() {
        Gdx.app.log("SiliconRoots", "Creating game...");

        try {
            batch = new SpriteBatch();
            font = new BitmapFont();

            font.getData().setScale(1.2f);
            ready = true;

            Gdx.app.log("SiliconRoots", "Game started.");
        } catch (RuntimeException e) {
            Gdx.app.error("SiliconRoots", "Startup failed.", e);
            dispose();
            throw e;
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.035f, 0.055f, 0.10f, 1f);

        if (!ready || batch == null || font == null) {
            return;
        }

        elapsed += Gdx.graphics.getDeltaTime();

        batch.begin();

        float x = 28f;
        float y = Gdx.graphics.getHeight() - 36f;

        font.setColor(Color.CYAN);
        font.draw(batch, "SILICON ROOTS", x, y);

        y -= 42f;
        font.setColor(Color.WHITE);
        font.draw(batch, "Engine: LibGDX", x, y);

        y -= 30f;
        font.draw(
            batch,
            "Status: Running",
            x,
            y
        );

        y -= 30f;
        font.draw(
            batch,
            "FPS: " + Gdx.graphics.getFramesPerSecond(),
            x,
            y
        );

        y -= 30f;
        font.draw(
            batch,
            "Screen: " + Gdx.graphics.getWidth()
                + " x " + Gdx.graphics.getHeight(),
            x,
            y
        );

        y -= 30f;
        font.draw(
            batch,
            "Uptime: " + (int) elapsed + " sec",
            x,
            y
        );

        y -= 48f;
        font.setColor(Color.GREEN);
        font.draw(
            batch,
            "If you see this, rendering works!",
            x,
            y
        );

        batch.end();
    }

    @Override
    public void pause() {
        Gdx.app.log("SiliconRoots", "Paused.");
    }

    @Override
    public void resume() {
        Gdx.app.log("SiliconRoots", "Resumed.");
    }

    @Override
    public void dispose() {
        ready = false;

        if (batch != null) {
            batch.dispose();
            batch = null;
        }

        if (font != null) {
            font.dispose();
            font = null;
        }

        Gdx.app.log("SiliconRoots", "Resources released.");
    }
}
