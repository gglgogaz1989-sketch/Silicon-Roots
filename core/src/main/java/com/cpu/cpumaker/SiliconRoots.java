package com.cpu.cpumaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.cpu.cpumaker.values.Coins;
import com.cpu.cpumaker.values.Values;
import com.cpu.cpumaker.utils.Resize;
import com.cpu.cpumaker.sprites.SpriteManager;
import com.cpu.cpumaker.musical.SoundManager;

public class SiliconRoots extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    
    private SpriteManager spriteManager;
    private SoundManager soundManager;
    
    private Rectangle deskBounds;
    private Rectangle objectBounds;
    
    private Rectangle shopButtonBounds;
    private Rectangle backButtonBounds;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        spriteManager = new SpriteManager();
        spriteManager.load();

        soundManager = new SoundManager();
        soundManager.load();

        // Запуск фоновой музыки главного меню
        soundManager.menuMusic.play();

        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        deskBounds = Resize.getScaledBounds(192f, 96f, 3.5f, screenW, screenH);
        objectBounds = Resize.getScaledBounds(64f, 32f, 4.0f, screenW, screenH);

        shopButtonBounds = new Rectangle(screenW - 160f, screenH - 70f, 140f, 50f);
        backButtonBounds = new Rectangle(30f, screenH - 70f, 140f, 50f);
    }

    @Override
    public void render() {
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = screenH - Gdx.input.getY();

            if (!Values.inShop) {
                if (shopButtonBounds.contains(touchX, touchY)) {
                    Values.inShop = true;
                    soundManager.playClick();
                    // Переключаем музыку при входе в магазин
                    soundManager.menuMusic.pause();
                    soundManager.shopMusic.play();
                } else if (objectBounds.contains(touchX, touchY)) {
                    Values.transistorCount++;
                    Values.dollars += Values.getCurrentProfit();
                    soundManager.playClick();
                }
            } else {
                if (backButtonBounds.contains(touchX, touchY)) {
                    Values.inShop = false;
                    soundManager.playClick();
                    // Возвращаем музыку меню
                    soundManager.shopMusic.pause();
                    soundManager.menuMusic.play();
                }
            }
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!Values.inShop) {
            // Рисуем верстак (desk)
            batch.draw(spriteManager.desk, deskBounds.x, deskBounds.y, deskBounds.width, deskBounds.height);

            // Транзисторы с авто-ресайзом
            long count = Math.max(1, Values.transistorCount);
            float scale = Math.max(0.12f, 1.0f / (1.0f + 0.07f * (count - 1)));
            
            float itemW = objectBounds.width * scale;
            float itemH = objectBounds.height * scale;

            int cols = (int) Math.sqrt(count) + 1;
            for (int i = 0; i < Math.min(count, 150); i++) {
                int r = i / cols;
                int c = i % cols;
                
                float posX = objectBounds.x + c * (itemW + 1f);
                float posY = objectBounds.y + r * (itemH + 1f);

                batch.draw(spriteManager.transistors, posX, posY, itemW, itemH);
            }

            // Кнопка Магазин
            batch.setColor(0.2f, 0.22f, 0.3f, 1f);
            batch.draw(spriteManager.uiPixel, shopButtonBounds.x, shopButtonBounds.y, shopButtonBounds.width, shopButtonBounds.height);
            batch.setColor(1, 1, 1, 1);

            font.getData().setScale(1.2f);
            font.draw(batch, "Магазин", shopButtonBounds.x + 25f, shopButtonBounds.y + 32f);

        } else {
            // Окно магазина
            batch.setColor(0.12f, 0.12f, 0.16f, 1f);
            batch.draw(spriteManager.uiPixel, 50f, 50f, screenW - 100f, screenH - 120f);
            batch.setColor(1, 1, 1, 1);

            font.getData().setScale(2.0f);
            font.draw(batch, "ЛАБОРАТОРНЫЙ МАГАЗИН", 90f, screenH - 90f);

            // Кнопка Обратно
            batch.setColor(0.3f, 0.2f, 0.2f, 1f);
            batch.draw(spriteManager.uiPixel, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);
            batch.setColor(1, 1, 1, 1);

            font.getData().setScale(1.2f);
            font.draw(batch, "Обратно", backButtonBounds.x + 25f, backButtonBounds.y + 32f);
        }

        // Верхний HUD (монеты и доход)
        float topY = screenH - 50f;
        batch.draw(spriteManager.coin, 30f, topY - 12f, 32f, 32f);
        
        String coinText = "$" + Coins.format(Values.dollars);
        font.getData().setScale(1.8f);
        font.draw(batch, coinText, 72f, topY + 10f);

        font.getData().setScale(1.2f);
        font.draw(batch, "Доход: +" + Coins.format(Values.getCurrentProfit()), 72f, topY - 20f);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        spriteManager.dispose();
        soundManager.dispose();
    }
}
