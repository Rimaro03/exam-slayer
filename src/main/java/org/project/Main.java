package org.project;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.entites.Player;
import org.project.componentsystem.items.Sword;
import org.project.utils.Vec2;

import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
class Main{
    
    public static void main(String[] args) {
        AtomicInteger id = new AtomicInteger(0);
        Player player = new Player(id, "tonno7103", 20);
        log.info("Player created with id: " + player.getId());
        log.info("Player name: " + player.getName());

        log.info("Player position pre: " + player.getPosition().getX() + ", " + player.getPosition().getY());
        player.setPosition(new Vec2(10, 10));
        log.info("Player position post: " + player.getPosition().getX() + ", " + player.getPosition().getY());
        log.info("GLOBAL ID: " + id);
        player.getInventory().put(new Sword(id, "Diamond sword", 104), 1);
        log.info("GLOBAL ID: " + id);
        log.info(player.getInventory());
        log.info(player.getId());


        player.start();
        player.update();
    }
}