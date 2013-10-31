package by.bsu.appconfig.loader;

import by.bsu.appconfig.manager.AppConfigManager;
import by.bsu.appconfig.manager.WindowConfigManager;
import by.bsu.appconfig.model.Window;

public class WindowLoader {

    private static volatile WindowLoader instance;
    private WindowConfigManager windowConfigManager;

    private WindowLoader()
    {
        windowConfigManager = WindowConfigManager.getInstance();
    }

    public static WindowLoader getInstance()
    {
        if (null == instance)
        {
            synchronized (WindowLoader.class)
            {
                if (null == instance)
                {
                    instance = new WindowLoader();
                }
            }
        }

        return instance;
    }

    public Window getWindow(String windowFileName)
    {
        Window window = new Window();

        windowConfigManager.loadProperties(WindowFilenames.MAIN_WINDOW);
        window.setBackgroundColor(windowConfigManager.getProperty(WindowConfigManager.BACKGROUND_COLOR));
        window.setHeight(Double.parseDouble(windowConfigManager.getProperty(WindowConfigManager.HEIGHT)));
        window.setWidth(Double.parseDouble(windowConfigManager.getProperty(WindowConfigManager.WIDTH)));

        return window;
    }

}
