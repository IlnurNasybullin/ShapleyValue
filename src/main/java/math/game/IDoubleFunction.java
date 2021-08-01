package math.game;

import java.util.function.ToDoubleFunction;

@FunctionalInterface
public interface IDoubleFunction<X> extends ToDoubleFunction<X> {
    /** Alias for {@link ToDoubleFunction#applyAsDouble(X)} */
    default double f(X x) {
        return applyAsDouble(x);
    }
}


