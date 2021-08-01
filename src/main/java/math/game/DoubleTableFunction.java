package math.game;

import java.util.Map;
import java.util.function.ToDoubleFunction;

public class DoubleTableFunction<X> implements IDoubleFunction<X> {

    private final Map<X, Double> tableValues;
    private final double defaultValue;

    public DoubleTableFunction(Map<X, Double> tableValues, double defaultValue) {
        this.tableValues = tableValues;
        this.defaultValue = defaultValue;
    }

    @Override
    public double applyAsDouble(X x) {
        return tableValues.getOrDefault(x, defaultValue);
    }
}
