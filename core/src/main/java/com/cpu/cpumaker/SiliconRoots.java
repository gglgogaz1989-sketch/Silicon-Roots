package com.cpu.cpumaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.cpu.cpumaker.values.Coins;
import com.cpu.cpumaker.values.Values;
import com.cpu.cpumaker.utils.Resize;

public class SiliconRoots extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    
    private Texture workbenchTexture;
    private Texture transistorTexture;
    private Texture coinTexture;
    
    private Sound clickSound;
    
    private Rectangle workbenchBounds;
    private Rectangle objectBounds;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        workbenchTexture = new Texture(Gdx.files.internal("workbench.png"));
        transistorTexture = new Texture(Gdx.files.internal("black_transistor.png"));
        coinTexture = new Texture(Gdx.files.internal("coin.png"));

        Resize.applyPixelFilter(workbenchTexture);
        Resize.applyPixelFilter(transistorTexture);
        Resize.applyPixelFilter(coinTexture);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("laser_click.wav"));

        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        workbenchBounds = Resize.getScaledBounds(192f, 96f, 3.5f, screenW, screenH);
        objectBounds = Resize.getScaledBounds(64f, 32f, 4.0f, screenW, screenH);
    }

    @Override
    public void render() {
        // Клик по зоне кристалла: моментально спавнит транзистор, ставит его и начисляет доход
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (objectBounds.contains(touchX, touchY)) {
                Values.transistorCount++;
                Values.dollars += Values.getCurrentProfit();
                clickSound.play(0.5f);
            }
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 1. Рисуем верстак
        batch.draw(workbenchTexture, workbenchBounds.x, workbenchBounds.y, workbenchBounds.width, workbenchBounds.height);

        // 2. Динамический ресайз и плотная упаковка транзисторов внутри кристалла
        long count = Math.max(1, Values.transistorCount);
        float scale = Math.max(0.12f, 1.0f / (1.0f + 0.07f * (count - 1)));
        
        float itemW = objectBounds.width * scale;
        float itemH = objectBounds.height * scale;

        int cols = (int) Math.sqrt(count) + 1;
        for (int i = 0; i < Math.min(count, 150); i++) { // Ограничение для оптимизации
            int r = i / cols;
            int c = i % cols;
            
            float posX = objectBounds.x + c * (itemW + 1f);
            float posY = objectBounds.y + r * (itemH + 1f);

            batch.draw(transistorTexture, posX, posY, itemW, itemH);
        }

        // 3. Верхняя панель: монетка, баланс и текущий доход за клик
        float topY = Gdx.graphics.getHeight() - 50f;
        batch.draw(coinTexture, 30f, topY - 12f, 32f, 32f);
        
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
        workbenchTexture.dispose();
        transistorTexture.dispose();
        coinTexture.dispose();
        clickSound.dispose();
    }
}
