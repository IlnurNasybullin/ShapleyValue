package math.game.exceptions;

import math.game.CooperateGame;

/**
 * Исключение, выбрасываемое при валидации кооперативной игры в том случае, если было нарушено свойство
 * характеристической функции игры - условие супераддитивности
 * @see math.game.validator.CooperateGameValidator
 * @see CooperateGame#getCharacteristicFunction()
 */
public class SuperAdditivityException extends RuntimeException {

    public SuperAdditivityException() {
        super();
    }

    public SuperAdditivityException(String message) {
        super(message);
    }
}
