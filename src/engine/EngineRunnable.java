package engine;

import control.Controls;

public interface EngineRunnable {
    void init(Controls controls);

    void printHelp();

    void loop();

    void updateFps(int fps);

    void shutDown();
}