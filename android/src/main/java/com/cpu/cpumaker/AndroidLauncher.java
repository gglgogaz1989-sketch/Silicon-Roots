package com.cpu.cpumaker;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

private static final String TAG = "SiliconRoots";

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    try {
        AndroidApplicationConfiguration config =
                new AndroidApplicationConfiguration();

        // Отключаем датчики, которые игре пока не нужны.
        config.useAccelerometer = false;
        config.useCompass = false;

        Log.i(TAG, "Launching Silicon Roots...");

        initialize(new SiliconRoots(), config);

    } catch (Exception e) {
        Log.e(TAG, "Failed to launch the game", e);
        throw e;
    }
}

}
