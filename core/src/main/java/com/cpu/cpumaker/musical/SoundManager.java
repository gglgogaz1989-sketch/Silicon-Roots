
package com.cpu.cpumaker.musical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    public Sound clickSound;
    public Music menuMusic;
    public Music shopMusic;

    public void load() {
        dispose();

        try {
            clickSound = Gdx.audio.newSound(
                Gdx.files.internal("music/sfx/click_on_cpu.wav")
            );

            menuMusic = Gdx.audio.newMusic(
                Gdx.files.internal("music/musics/Menu_of_cpu.ogg")
            );

            shopMusic = Gdx.audio.newMusic(
                Gdx.files.internal("music/musics/Shop.ogg")
            );

            menuMusic.setLooping(true);
            shopMusic.setLooping(true);

            Gdx.app.log("SoundManager", "Sounds loaded.");
        } catch (RuntimeException e) {
            Gdx.app.error(
                "SoundManager",
                "Could not load audio. Check asset paths.",
                e
            );

            dispose();
        }
    }

    public void playClick() {
        if (clickSound != null) {
            clickSound.play(0.5f);
        }
    }

    public void playMenuMusic() {
        stopMusic();

        if (menuMusic != null) {
            menuMusic.play();
        }
    }

    public void playShopMusic() {
        stopMusic();

        if (shopMusic != null) {
            shopMusic.play();
        }
    }

    public void stopMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }

        if (shopMusic != null) {
            shopMusic.stop();
        }
    }

    public void dispose() {
        if (clickSound != null) {
            clickSound.dispose();
            clickSound = null;
        }

        if (menuMusic != null) {
            menuMusic.dispose();
            menuMusic = null;
        }

        if (shopMusic != null) {
            shopMusic.dispose();
            shopMusic = null;
        }
    }
}
