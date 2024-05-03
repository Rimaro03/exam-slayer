package org.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.componentsystem.entites.Player;
import org.project.componentsystem.items.Sword;
import org.project.utils.Vec2;

import java.util.concurrent.atomic.AtomicInteger;

class Main{

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        AtomicInteger id = new AtomicInteger(0);
        Player player = new Player(id, "tonno7103", 20);
        logger.info("Player created with id: " + player.getId());
        logger.info("Player name: " + player.getName());

        logger.info("Player position pre: " + player.getPosition().getX() + ", " + player.getPosition().getY());
        player.setPosition(new Vec2(10, 10));
        logger.info("Player position post: " + player.getPosition().getX() + ", " + player.getPosition().getY());
        logger.info("GLOBAL ID: " + id);
        player.getInventory().put(new Sword(id, "Diamond sword", 104), 1);
        logger.info("GLOBAL ID: " + id);
        logger.info(player.getInventory());
        logger.info(player.getId());


        player.start();
        player.update();
    }
}