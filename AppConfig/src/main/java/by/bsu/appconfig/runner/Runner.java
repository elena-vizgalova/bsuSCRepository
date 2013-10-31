package by.bsu.appconfig.runner;

import by.bsu.appconfig.loader.WindowFilenames;
import by.bsu.appconfig.loader.WindowLoader;
import by.bsu.appconfig.model.Window;

public class Runner {

    public static void main(String[] args) {

        WindowLoader windowLoader = WindowLoader.getInstance();
        Window mainWindow = windowLoader.getWindow(WindowFilenames.MAIN_WINDOW);

        System.out.println(mainWindow);
    }

}
