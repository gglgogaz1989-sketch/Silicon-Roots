package com.cpu.cpumaker.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem {
    private static class Particle {
        float x, y, vx, vy, life, maxLife;
    }

    private final List<Particle> particles = new ArrayList<>();

    // Спавн искр при клике
    public void spawn(float x, float y, int count) {
        for (int i = 0; i < count; i++) {
            Particle p = new Particle();
            p.x = x;
            p.y = y;
            float angle = MathUtils.random(0, 2 * MathUtils.PI);
            float speed = MathUtils.random(60f, 180f);
            p.vx = MathUtils.cos(angle) * speed;
            p.vy = MathUtils.sin(angle) * speed;
            p.life = 0f;
            p.maxLife = MathUtils.random(0.3f, 0.6f);
            particles.add(p);
        }
    }

    // Обновление положения частиц каждый кадр
    public void update(float delta) {
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.x += p.vx * delta;
            p.y += p.vy * delta;
            p.life += delta;
            if (p.life >= p.maxLife) {
                particles.remove(i);
            }
        }
    }

    // Отрисовка искр с затуханием (альфа-каналом)
    public void draw(SpriteBatch batch, Texture pixelTexture) {
        for (Particle p : particles) {
            float alpha = 1.0f - (p.life / p.maxLife);
            batch.setColor(1f, 0.85f, 0.3f, alpha); // Теплый искрящийся цвет
            batch.draw(pixelTexture, p.x, p.y, 5f, 5f);
        }
        batch.setColor(1, 1, 1, 1); // Сброс цвета
    }
}
