package org.project.componentsystem.components.colliders;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AbstractBoxColliderTest {

    @Test
    public void overlapOccursWhenBoxesIntersect() {
        GameObject gameObject1 = new GameObject("test");
        gameObject1.setPosition(new Vec2(0, 0));
        BoxCollider boxCollider1 = new BoxCollider(gameObject1, new Vec2(10, 10), true);

        GameObject gameObject2 = new GameObject("test2");
        gameObject2.setPosition(new Vec2(5, 5));
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true);

        Vec2 overlap = boxCollider1.getOverlap(boxCollider2);

        assertEquals(new Vec2(-5, -5), overlap);
    }

    @Test
    public void noOverlapOccursWhenBoxesDoNotIntersect() {
        GameObject gameObject1 = new GameObject("test");
        gameObject1.setPosition(new Vec2(0, 0));
        BoxCollider boxCollider1 = new BoxCollider(gameObject1, new Vec2(10, 10), true);

        GameObject gameObject2 = new GameObject("test2");
        gameObject2.setPosition(new Vec2(20, 20));
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true);

        Vec2 overlap = boxCollider1.getOverlap(boxCollider2);

        assertNull(overlap);
    }

    @Test
    public void overlapOccursWhenBoxesTouchAtEdge() {
        GameObject gameObject1 = new GameObject("test");
        gameObject1.setPosition(new Vec2(0, 0));
        BoxCollider boxCollider1 = new BoxCollider(gameObject1, new Vec2(10, 10), true);

        GameObject gameObject2 = new GameObject("test2");
        gameObject2.setPosition(new Vec2(10, 10));
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true);

        Vec2 overlap = boxCollider1.getOverlap(boxCollider2);

        assertNull(overlap);
    }
}