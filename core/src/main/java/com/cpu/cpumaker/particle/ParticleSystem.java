
package com.cpu.cpumaker.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem {

    private static class Particle {
        float x;
        float y;
        float vx;
        float vy;
        float life;
        float maxLife;
        float size;
    }

    private final List<Particle> particles = new ArrayList<>();

    public void spawn(float x, float y, int count) {
        int safeCount = Math.max(0, Math.min(count, 500));

        for (int i = 0; i < safeCount; i++) {
            Particle p = new Particle();

            p.x = x;
            p.y = y;

            float angle = MathUtils.random(
                0f,
                MathUtils.PI2
            );

            float speed = MathUtils.random(60f, 180f);

            p.vx = MathUtils.cos(angle) * speed;
            p.vy = MathUtils.sin(angle) * speed;

            p.life = 0f;
            p.maxLife = MathUtils.random(0.3f, 0.8f);
            p.size = MathUtils.random(2f, 6f);

            particles.add(p);
        }
    }

    public void update(float delta) {
        float dt = MathUtils.clamp(delta, 0f, 0.1f);

        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);

            p.x += p.vx * dt;
            p.y += p.vy * dt;
            p.life += dt;

            if (p.life >= p.maxLife) {
                particles.remove(i);
            }
        }
    }

    public void draw(SpriteBatch batch, Texture pixelTexture) {
        if (batch == null || pixelTexture == null) {
            return;
        }

        for (Particle p : particles) {
            float alpha = MathUtils.clamp(
                1f - p.life / p.maxLife,
                0f,
                1f
            );

            batch.setColor(1f, 0.75f, 0.2f, alpha);

            batch.draw(
                pixelTexture,
                p.x,
                p.y,
                p.size,
                p.size
            );
        }

        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void clear() {
        particles.clear();
    }

    public int getParticleCount() {
        return particles.size();
    }
}
