package aura.core;

import javax.swing.Timer;

import aura.utils.MathUtils;

@SuppressWarnings ("unchecked")
public abstract class Transition<T extends  Transition<T>> {

    private Timer timer;
    private long startTime;
    private int duration;
    private float startVal, endVal;
    private FinishAction finishAction;
    private StartAction startAction;
    private boolean repeat;
    private int delay = 0;
    private boolean forward = true;
    private boolean pingPong = false;
    private Transition<?> serieT;
    private Transition<?> parallelT;
    private AuraBox<?> component;
    private boolean cancel = false;
    private TransitionFloatStep step;

    public static enum TransitionType {
        LINEAR,
        EASE_IN,
        EASE_OUT,
        EASE_IN_OUT,
        BOUNCE_IN,
        BOUNCE_OUT,
        BOUNCE_IN_OUT,
        ELASTIC_IN,
        ELASTIC_OUT,
        ELASTIC_IN_OUT
    }

    public static enum AnimationType {
        MOVE,
        COLOR,
        SCALE,
        PADDING,
        MARGIN,
        BACKGROUND,
        RADIUS,
        OPACITY,
        SHADOW,
        GAP,
        ROTATE,
        NONE
    };

    private TransitionType type;
    private AnimationType animationType;

    public void setup(float initial, float target, int ms, TransitionFloatStep step, AuraBox<?> component, TransitionType type){
        this.startVal = initial;
        this.endVal = target;
        this.duration = ms;
        this.type = type;
        this.animationType = AnimationType.NONE;
        this.component = component;
        this.step = step;

        float min = Math.min(initial, target);
        float max = Math.max(initial, target);

        timer = new Timer(15, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            float progress = (float) elapsed / duration;

           

            if (progress >= 1f) {

                 if(this.repeat){
                    if(this.pingPong){
                        forward = !forward;
                        progress = 0;
                        startTime = System.currentTimeMillis();
                    }else{
                        startTime = System.currentTimeMillis();
                        progress = 0;
                    }
                }
                
                else if (this.pingPong && forward) {
                    forward = false;
                    startTime = System.currentTimeMillis();
                    progress = 0;
                } else {
                    this.stop(false);
                }
            }

            // Ajuste del progreso según la dirección
            float effectiveProgress = forward ? progress : (1f - progress);

            // Aplicamos el Easing y el cálculo final
            float currentValue = startVal + (endVal - startVal) * calc(effectiveProgress);
            step.onUpdate((float)MathUtils.clamp(currentValue, min, max));

        });
    }

    public T delay(int ms){
        this.delay = ms;
        return (T) this;
    }

    public T pingPong(){
        this.pingPong = true;
        return (T) this;
    }

    public T loop(){
        this.repeat = true;
        return (T) this;
    }

    public T animationType(AnimationType type){
        this.animationType = type;
        return (T) this;
    }

    public T type(TransitionType type){
        this.type = type;
        return (T) this;
    }

    public T then(FinishAction action){
        this.finishAction = action;
        return (T) this;
    }

    public T when(StartAction action){
        this.startAction = action;
        return (T) this;
    }

    public T serie(Transition<?> animation){
        this.serieT = animation;
        return (T) this;
    }

    public T parallel(Transition<?> animation){
        this.parallelT = animation;
        return (T) this;
    }

    public T cancelPrev(boolean cancel){
        this.cancel = cancel;
        return (T)this;
    }

    private float calc(float value) {
        return switch (this.type) {
            case LINEAR -> value;
            case EASE_IN -> (float) Math.cbrt(value); 
            case EASE_OUT -> (float) Math.pow(value, 3);
            case EASE_IN_OUT -> Easing.easeInOut(value);
            case BOUNCE_OUT -> Easing.bounceOut(value);
            case BOUNCE_IN -> Easing.easeInBounce(value);
            case BOUNCE_IN_OUT -> Easing.easeInOutBounce(value);
            case ELASTIC_OUT -> Easing.easeOutElastic(value);
            case ELASTIC_IN -> Easing.easeInElastic(value);
            case ELASTIC_IN_OUT -> Easing.easeInOutElastic(value);
            default -> value;
        };
    }


    public void start() {

        new Thread(() -> {
            try {
                
                if(this.component != null) {
                    if(this.cancel){
                        this.component.cancelAnimations(animationType);
                    }
                    this.component.animate(this);
                }
                Thread.sleep(delay);
                if(startAction != null) startAction.onStart();

                startTime = System.currentTimeMillis();
                timer.start();

                if(this.parallelT != null) this.parallelT.start();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }).start();

    }

    public void stop(boolean critic) {
        if (timer != null) timer.stop();

        if(!critic){
            if(this.finishAction != null) finishAction.onFinish();
            if(this.serieT != null) this.serieT.start();
        } else if (this.step != null){
            step.onUpdate(startVal);
        }
        
    }

    public AnimationType getAnimationType(){
        return this.animationType;
    }
}