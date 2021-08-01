package math.game.validator;

import math.game.CooperateGame;
import math.game.IDoubleFunction;
import math.game.exceptions.PersonalIdentityException;
import math.game.exceptions.SuperAdditivityException;
import math.util.MathUtil;

import java.util.*;

public class CooperateGameValidator {

    public static <P> void validate(CooperateGame<P> game) throws PersonalIdentityException, SuperAdditivityException {
        Set<P> players = game.getPlayers();
        IDoubleFunction<Set<P>> characteristicFunction = game.getCharacteristicFunction();

        validateFunctionProperty(players, characteristicFunction);
    }

    public static <P> void validateFunctionProperty(Set<P> players, IDoubleFunction<Set<P>> function) throws
            PersonalIdentityException, SuperAdditivityException {

        if (function.f(Collections.emptySet()) != 0d) {
            throw new PersonalIdentityException("Нарушено условие персональности!");
        }

        if (players.isEmpty()) {
            return;
        }

        for (Set<P> coalitions: MathUtil.subsets(players)) {
            double payment = function.f(coalitions);
            if (coalitions.isEmpty()) {
                continue;
            }

            List<P> coalitionWithoutOnePlayer = new ArrayList<>(coalitions);
            coalitionWithoutOnePlayer.remove(coalitionWithoutOnePlayer.size() - 1);

            for (Set<P> subCoalitions : MathUtil.subsets(coalitionWithoutOnePlayer)) {
                Set<P> diffCoalitions = MathUtil.diffSet(coalitions, subCoalitions);

                if (function.f(subCoalitions) + function.f(diffCoalitions) > payment) {
                    throw new SuperAdditivityException(String
                            .format("Функция не удовлетворяет условию аддидитивности для коалиций %s и %s", subCoalitions, diffCoalitions));
                }
            }
        }
    }

}
