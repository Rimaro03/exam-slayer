package org.project;

import org.project.core.Application;
import org.project.core.Logger;

public class DevApplication extends Application {
    public DevApplication(){ super("Dev"); }
    public void run() {
        Logger.info("Dev application started!");
        while(true){
            window.update();
        }
    }
}
