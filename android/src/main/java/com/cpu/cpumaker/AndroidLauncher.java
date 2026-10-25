package com.cpu.cpumaker;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        
        // Отключаем неиспользуемые датчики для стабильности
        config.useAccelerometer = false;
        config.useCompass = false;
        
        initialize(new SiliconRoots(), config);
    }
}
