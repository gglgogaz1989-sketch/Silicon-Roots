package com.cpu.cpumaker.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.cpu.cpumaker.utils.Resize;

public class SpriteManager {
    // Точные пути к файлам из твоего GitHub
    private static final String DESK_FILE = "other/desk.png";
    private static final String TRANSISTORS_FILE = "other/transistors.png";
    private static final String COIN_FILE = "values/coin.png";

    public Texture desk;
    public Texture transistors;
    public Texture coin;
    public Texture uiPixel;

    public void load() {
        desk = new Texture(Gdx.files.internal(DESK_FILE));
        transistors = new Texture(Gdx.files.internal(TRANSISTORS_FILE));
        coin = new Texture(Gdx.files.internal(COIN_FILE));

        Resize.applyPixelFilter(desk);
        Resize.applyPixelFilter(transistors);
        Resize.applyPixelFilter(coin);

        // Программная текстура для кнопок UI
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        uiPixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public void dispose() {
        desk.dispose();
        transistors.dispose();
        coin.dispose();
        uiPixel.dispose();
    }
}
