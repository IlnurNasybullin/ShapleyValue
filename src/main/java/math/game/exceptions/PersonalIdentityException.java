package math.game.exceptions;

import math.game.CooperateGame;

/**
 * Исключение, выбрасываемое при валидации кооперативной игры в том случае, если было нарушено свойство
 * характеристической функции игры - условие персональности
 * @see math.game.validator.CooperateGameValidator
 * @see CooperateGame#getCharacteristicFunction()
 */
public class PersonalIdentityException extends RuntimeException {

    public PersonalIdentityException() {
        super();
    }

    public PersonalIdentityException(String message) {
        super(message);
    }
}
