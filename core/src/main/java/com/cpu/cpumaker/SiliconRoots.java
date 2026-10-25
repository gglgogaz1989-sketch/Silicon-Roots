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
import com.cpu.cpumaker.particles.ParticleSystem;

public class SiliconRoots extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    
    private SpriteManager spriteManager;
    private SoundManager soundManager;
    private ParticleSystem particleSystem;
    
    private Rectangle crystalBounds; // Зона кристалла (и для клика, и для отрисовки рамки)
    
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

        particleSystem = new ParticleSystem();

        soundManager.menuMusic.play();

        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        // Размер и позиция кристалла по центру экрана
        crystalBounds = Resize.getScaledBounds(96f, 96f, 3.5f, screenW, screenH);

        shopButtonBounds = new Rectangle(screenW - 160f, screenH - 70f, 140f, 50f);
        backButtonBounds = new Rectangle(30f, screenH - 70f, 140f, 50f);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        particleSystem.update(deltaTime);

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = screenH - Gdx.input.getY();

            if (!Values.inShop) {
                if (shopButtonBounds.contains(touchX, touchY)) {
                    Values.inShop = true;
                    soundManager.playClick();
                    soundManager.menuMusic.pause();
                    soundManager.shopMusic.play();
                } 
                // Клик именно по кристаллу процессора!
                else if (crystalBounds.contains(touchX, touchY)) {
                    Values.transistorCount++;
                    Values.dollars += Values.getCurrentProfit();
                    soundManager.playClick();
                    particleSystem.spawn(touchX, touchY, 8);
                }
            } else {
                if (backButtonBounds.contains(touchX, touchY)) {
                    Values.inShop = false;
                    soundManager.playClick();
                    soundManager.shopMusic.pause();
                    soundManager.menuMusic.play();
                }
            }
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 1. ФОН: Стол на весь экран
        batch.draw(spriteManager.desk, 0, 0, screenW, screenH);

        if (!Values.inShop) {
            // 2. РИСУЕМ КРИСТАЛЛ ПРОЦЕССОРА (основа, куда всё помещается)
            batch.draw(spriteManager.crystal, crystalBounds.x, crystalBounds.y, crystalBounds.width, crystalBounds.height);

            // 3. ТРАНЗИСТОРЫ С АВТО-РЕСАЙЗОМ ВНУТРИ КРИСТАЛЛА
            long count = Math.max(1, Values.transistorCount);
            
            // Чем больше транзисторов, тем сильнее они уменьшаются, чтобы поместиться внутри crystalBounds
            float scale = Math.max(0.15f, 1.0f / (1.0f + 0.05f * (count - 1)));
            
            float itemW = 32f * scale;
            float itemH = 32f * scale;

            // Смещаем начальную позицию отрисовки внутрь кристалла с отступом
            float padding = 15f * (crystalBounds.width / 96f);
            float startX = crystalBounds.x + padding;
            float startY = crystalBounds.y + crystalBounds.height - padding - itemH;

            int cols = (int) Math.sqrt(count) + 1;
            for (int i = 0; i < Math.min(count, 200); i++) {
                int r = i / cols;
                int c = i % cols;
                
                float posX = startX + c * (itemW + 2f);
                float posY = startY - r * (itemH + 2f);

                // Если транзисторы не вылазят за границы кристалла — рисуем
                if (posY >= crystalBounds.y + padding) {
                    batch.draw(spriteManager.transistors, posX, posY, itemW, itemH);
                }
            }

            // Частицы (искры)
            particleSystem.draw(batch, spriteManager.uiPixel);

            // Кнопка Магазин
            batch.setColor(0.2f, 0.22f, 0.3f, 0.9f);
            batch.draw(spriteManager.uiPixel, shopButtonBounds.x, shopButtonBounds.y, shopButtonBounds.width, shopButtonBounds.height);
            batch.setColor(1, 1, 1, 1);

            font.getData().setScale(1.2f);
            font.draw(batch, "Магазин", shopButtonBounds.x + 25f, shopButtonBounds.y + 32f);

        } else {
            // Окно магазина
            batch.setColor(0.12f, 0.12f, 0.16f, 0.95f);
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
