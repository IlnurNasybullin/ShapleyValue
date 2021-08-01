package math.game;

import math.game.validator.CooperateGameValidator;

import java.util.Set;

/**
 * Класс, представляющий собой данные кооперативной игры - множество игроков и характеристическую функцию, определённую
 * на подмножестве множества всех игроков.
 * */
public class CooperateGame<P> {

    /**
     * Множество игроков, P - тип (класс) игрока
     */
    private final Set<P> players;
    /**
     * Характеристическая функция (функция выигрыша коалиции). Характеристическая функция должна удовлетворять 2 свойствам:
     * <br/>
     * 1. Условие персональности - {@link #characteristicFunction}(&empty;) = 0 <br/>
     * 2. Условие супераддитивности - {@link #characteristicFunction}(P<sub>i</sub>) +
     * {@link #characteristicFunction}(P<sub>j</sub>) &le; {@link #characteristicFunction}(P<sub>1</sub> &cup; P<sub>2</sub>),
     * P<sub>i</sub> &cap; P<sub>j</sub> = &empty;, i&ne;j, P<sub>i</sub> &sub; {@link #players},
     * P<sub>j</sub> &sub; {@link #players}
     */
    private final IDoubleFunction<Set<P>> characteristicFunction;

    /**
     * По умолчанию, характеристическая функция проходит валидацию - проверку выполнения условий персональности и
     * условия супераддитивности. При нарушении условия персональности - выбрасывается исключение
     * {@link math.game.exceptions.PersonalIdentityException}, при нарушении условия супераддитивности -
     * {@link math.game.exceptions.SuperAdditivityException}
     * @see #characteristicFunction
     * @see math.game.validator.CooperateGameValidator
     * */
    public CooperateGame(Set<P> players, IDoubleFunction<Set<P>> characteristicFunction) {
        this(players, characteristicFunction, true);
    }

    public CooperateGame(Set<P> players, IDoubleFunction<Set<P>> characteristicFunction, boolean validate) {
        if (validate) {
            validate(players, characteristicFunction);
        }
        this.players = players;
        this.characteristicFunction = characteristicFunction;
    }

    private void validate(Set<P> players, IDoubleFunction<Set<P>> characteristicFunction) {
        CooperateGameValidator.validateFunctionProperty(players, characteristicFunction);
    }

    public Set<P> getPlayers() {
        return players;
    }

    public IDoubleFunction<Set<P>> getCharacteristicFunction() {
        return characteristicFunction;
    }
}
