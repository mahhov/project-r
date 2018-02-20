package engine;

import control.KeyControl;
import control.MouseButtonControl;
import control.MousePosControl;

public interface EngineRunnable {
    void init(KeyControl keyControl, MousePosControl mousePosControl, MouseButtonControl mouseButtonControl);

    void printHelp();

    void loop();

    void updateFps(int fps);

    void shutDown();
}