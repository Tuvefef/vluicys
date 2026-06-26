package net.tuvefe.vluicys.common;

public class Common {
    static public float randomRange(float min, float max) {
        return (float)(Math.random() * (max - min + 1.0f) + min);
    }
}
