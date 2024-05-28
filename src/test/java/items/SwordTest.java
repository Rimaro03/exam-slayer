package items;

import org.junit.jupiter.api.Test;
import org.project.items.Sword;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwordTest {

    @Test
    void getName() {
        Sword sword = new Sword("Sword", 10, "resources/textures/touchable/sword.png", "resources/textures/stats/item/sword.png");
        assertEquals("Sword", sword.getName());
    }

    @Test
    void getWeight() {
        Sword sword = new Sword("Sword", 10, "resources/textures/touchable/sword.png", "resources/textures/stats/item/sword.png");
        assertEquals(10, sword.getWeight());
    }

    @Test
    void setName() {
        Sword sword = new Sword("Sword", 10, "resources/textures/touchable/sword.png", "resources/textures/stats/item/sword.png");
        sword.setName("Sword2");
        assertEquals("Sword2", sword.getName());
    }

    @Test
    void setWeight() {
        Sword sword = new Sword("Sword", 10, "resources/textures/touchable/sword.png", "resources/textures/stats/item/sword.png");
        sword.setWeight(20);
        assertEquals(20, sword.getWeight());
    }


    @Test
    void use() {
        Sword sword = new Sword("Sword", 10, "resources/textures/touchable/sword.png", "resources/textures/stats/item/sword.png");
        sword.use();
    }
}