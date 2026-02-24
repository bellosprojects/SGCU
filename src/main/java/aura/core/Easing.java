package aura.core;

public abstract class Easing {

    public static float easeInOut(float t) {
        return t < 0.5f 
            ? 4f * t * t * t 
            : 1f - (float)Math.pow(-2f * t + 2f, 3f) / 2f;
    }

    public static float bounceOut(float t) {
        if (t < 1f / 2.75f) {
            return 7.5625f * t * t;
        } else if (t < 2f / 2.75f) {
            t -= 1.5f / 2.75f;
            return 7.5625f * t * t + 0.75f;
        } else if (t < 2.5f / 2.75f) {
            t -= 2.25f / 2.75f;
            return 7.5625f * t * t + 0.9375f;
        } else {
            t -= 2.625f / 2.75f;
            return 7.5625f * t * t + 0.984375f;
        }
    }

    public static float easeInBounce(float t) {
        return 1f - bounceOut(1f - t);
    }

    public static float easeInOutBounce(float t) {
        return t < 0.5f
            ? (1f - bounceOut(1f - 2f * t)) / 2f
            : (1f + bounceOut(2f * t - 1f)) / 2f;
    }

    public static float easeOutElastic(float t) {
        if (t == 0 || t == 1) return t;
        float p = 0.3f;
        float s = p / 4f;
        return (float) (Math.pow(2f, -10f * t) * Math.sin((t - s) * (2f * Math.PI) / p) + 1f);
    }

    public static float easeInElastic(float t) {
        if (t == 0 || t == 1) return t;
        float p = 0.3f;
        float s = p / 4f;
        t -= 1;
        return (float) -(Math.pow(2f, 10f * t) * Math.sin((t - s) * (2f * Math.PI) / p));
    }

    public static float easeInOutElastic(float t) {
        if (t == 0 || t == 1) return t;
        t *= 2;
        if (t < 1) {
            float p = 0.3f * 1.5f;
            float s = p / 4f;
            t -= 1;
            return (float) (-0.5f * (Math.pow(2f, 10f * t) * Math.sin((t - s) * (2f * Math.PI) / p)));
        } else {
            t -= 1;
            float p = 0.3f * 1.5f;
            float s = p / 4f;
            return (float) (0.5f * (Math.pow(2f, -10f * t) * Math.sin((t - s) * (2f * Math.PI) / p) + 2f));
        }
    }
    
}
