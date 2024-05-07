package org.project;

import lombok.extern.log4j.Log4j2;
import org.project.core.Application;
import org.project.core.Core;

@Log4j2
class Main{
    
    public static void main(String[] args) {
        Application app = new Application("Exam Slayer");
        Core.init();

        app.run();
    }
}