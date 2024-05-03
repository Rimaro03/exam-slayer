package org.project;

import org.project.core.Application;
import org.project.core.Core;
class Main{
    public static void main(String[] args) {
        Application app = new DevApplication();
        Core.init();

        app.run();
    }
}