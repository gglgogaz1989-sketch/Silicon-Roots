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

    @Override
    public void create() {
        try {
            batch = new SpriteBatch();
            font = new BitmapFont();

            // ==========================================
            // ВСЮ СВОЮ ЛОГИКУ И ЗАГРУЗКУ ТЕКСТУР ПИШИ ЗДЕСЬ:
            // Пример:
            // Texture desk = new Texture(Gdx.files.internal("other/desk.png"));
            // ==========================================

        } catch (Exception e) {
            // Если произошел вылет, игра перехватит его и сохранит текст ошибки
            errorMessage = "CRASH DETECTED:\n" + e.getClass().getName() + "\n" + 
                           (e.getMessage() != null ? e.getMessage() : "No message");
                           
            Gdx.app.log("SiliconRoots", "Crash", e);
        }
    }

    @Override
    public void render() {
        // Очищаем экран (темно-серый фон)
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        if (errorMessage != null) {
            // Если случился вылет — выводим ошибку красным текстом на экран телефона
            font.setColor(Color.RED);
            font.draw(batch, errorMessage, 30, Gdx.graphics.getHeight() - 50);
        } else {
            // Если всё ок — игра работает
            font.setColor(Color.WHITE);
            font.draw(batch, "Silicon Roots - Running OK!", 100, 100);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
