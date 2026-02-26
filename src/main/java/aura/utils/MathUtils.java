package aura.utils;

public class MathUtils {
    
    public static double clamp(float val, float min, float max){
        return Math.max(Math.min(val, max), min);
    }

}
