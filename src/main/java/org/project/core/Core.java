package org.project.core;

import org.project.core.rendering.Renderer;

public class Core {
    /**
     * Initialize the core library.
     */
    public static void init(){
        Input.init();
        Renderer.init();
    }
}
