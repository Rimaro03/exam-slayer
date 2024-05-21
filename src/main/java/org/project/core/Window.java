package org.project.core;

import lombok.Getter;
import org.project.core.rendering.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Represent the application window.
 */
@Getter
public class Window extends JPanel implements ComponentListener {
    private int width, height;
    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        setup();

        Application.getInstance().addComponentListener(this);
    }

    /** Updates the window and draws the frame. */
    public void update() {
        repaint();
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
    public void componentResized(ComponentEvent e) {
        width = e.getComponent().getWidth();
        height = e.getComponent().getHeight();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
