package math.game;

import math.util.MathUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, вычисляющий значение вектора Шепли кооперативной игры для переданного игрока
 * @param <P> - тип (класс) игрока
 */
public class ShapleyValue<P> {

    private final CooperateGame<P> game;

    public ShapleyValue(CooperateGame<P> game) {
        this.game = game;
    }

    public double avgShapleyValue(P player) {
        checkPlayer(player);

        Set<P> otherPlayers = getOtherPlayers(player);
        int n = players().size();
        int k;

        double avgShapleyValue = 0;

        for (Set<P> coalition: coalitions(otherPlayers)) {
            k = coalition.size();
            avgShapleyValue += fact(n - k - 1) * fact(k) * contribution(coalition, player);
        }

        avgShapleyValue /= fact(n);
        return avgShapleyValue;
    }

    public Map<P, Double> shapleyValues() {
        Map<P, Double> shapleyValues = players().stream().collect(Collectors.toMap(p -> p, p -> 0d));


        return shapleyValues;
    }

    private Set<P> players() {
        return game.getPlayers();
    }

    private Iterable<Set<P>> coalitions(Collection<P> players) {
        return MathUtil.subsets(players);
    }

    private double contribution(Set<P> coalition, P player) {
        Set<P> coalitionWithPlayer = new HashSet<>(coalition);
        coalitionWithPlayer.add(player);

        return function().f(coalitionWithPlayer) - function().f(coalition);
    }

    private IDoubleFunction<Set<P>> function() {
        return game.getCharacteristicFunction();
    }

    private long fact(int n) {
        return MathUtil.fact(n);
    }

    private void checkPlayer(P player) {
        if (!players().contains(player)) {
            throw new IllegalArgumentException(String.format("Player %s isn't contain in cooperate game!", player));
        }
    }

    private Set<P> getOtherPlayers(P player) {
        Set<P> otherPlayer = new HashSet<>(players());
        otherPlayer.remove(player);

        return otherPlayer;
    }
}
