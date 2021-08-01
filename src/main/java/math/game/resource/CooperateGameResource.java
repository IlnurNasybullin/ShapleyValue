package math.game.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import math.game.CooperateGame;
import math.game.DoubleTableFunction;
import math.game.IDoubleFunction;
import math.game.util.IntegerSetDeserializer;

import java.util.Map;
import java.util.Set;

public class CooperateGameResource {

    private Set<Integer> players;

    @JsonDeserialize(keyUsing = IntegerSetDeserializer.class)
    private Map<Set<Integer>, Double> tableMap;
    private double defaultValue = 0;

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Set<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Integer> players) {
        this.players = players;
    }

    public Map<Set<Integer>, Double> getTableMap() {
        return tableMap;
    }

    public void setTableMap(Map<Set<Integer>, Double> tableMap) {
        this.tableMap = tableMap;
    }

    public CooperateGame<Integer> toGame() {
        return new CooperateGame<>(players, getIDoubleFunction());
    }

    public IDoubleFunction<Set<Integer>> getIDoubleFunction() {
        return new DoubleTableFunction<>(tableMap, defaultValue);
    }
}
