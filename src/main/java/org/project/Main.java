package org.project;

import lombok.extern.log4j.Log4j2;
import org.project.core.Application;

@Log4j2
class Main{
    
    public static void main(String[] args) {
        Application.init();
        Application.run();
    }
}