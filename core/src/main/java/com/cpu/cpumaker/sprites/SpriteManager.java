package com.cpu.cpumaker.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.cpu.cpumaker.utils.Resize;

public class SpriteManager {
    public Texture workbench;
    public Texture transistor;
    public Texture coin;
    public Texture uiPixel;

    public void load() {
        workbench = new Texture(Gdx.files.internal("workbench.png"));
        transistor = new Texture(Gdx.files.internal("black_transistor.png"));
        coin = new Texture(Gdx.files.internal("coin.png"));

        Resize.applyPixelFilter(workbench);
        Resize.applyPixelFilter(transistor);
        Resize.applyPixelFilter(coin);

        // Программная текстура для кнопок UI
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        uiPixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public void dispose() {
        workbench.dispose();
        transistor.dispose();
        coin.dispose();
        uiPixel.dispose();
    }
}
