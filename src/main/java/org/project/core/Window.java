package org.project.core;

import lombok.Getter;
import org.project.core.rendering.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Represent the application window.
 */
@Getter
public class Window extends JPanel implements WindowStateListener {
    private int width, height;
    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        setup();

        Application.getInstance().addWindowStateListener(this);
    }

    /** Updates the window and draws the frame. */
    public void update() {
        repaint();
        Renderer.clear(Color.GRAY);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Renderer.present(g);
    }

    private void setup(){
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        width = e.getWindow().getWidth();
        height = e.getWindow().getHeight();
    }
}
