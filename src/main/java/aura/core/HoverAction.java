package aura.core;

public interface HoverAction <T> {
    void onHover(T self, boolean isHovered);
}