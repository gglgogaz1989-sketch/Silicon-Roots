package com.cpu.cpumaker.musical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
    public Sound clickSound;
    public Music menuMusic;
    public Music shopMusic;

    public void load() {
        // Звуки из music/sfx/
        clickSound = Gdx.audio.newSound(Gdx.files.internal("music/sfx/click_on_cpu.wav"));
        
        // Музыка из music/musics/
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/musics/Menu_of_cpu.ogg"));
        shopMusic = Gdx.audio.newMusic(Gdx.files.internal("music/musics/Shop.ogg"));

        menuMusic.setLooping(true);
        shopMusic.setLooping(true);
    }

    public void playClick() {
        clickSound.play(0.5f);
    }

    public void dispose() {
        clickSound.dispose();
        if (menuMusic != null) menuMusic.dispose();
        if (shopMusic != null) shopMusic.dispose();
    }
}
