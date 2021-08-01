package math.game.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import math.game.CooperateGame;
import math.game.ShapleyValue;
import math.game.resource.CooperateGameResource;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CooperateGameUtil {

    /**
     * Считывание данных об игре с JSON-файла в объект класса {@link CooperateGameResource}
     */
    public static CooperateGameResource readGame(URL url) throws IOException {
         return new ObjectMapper().readerFor(CooperateGameResource.class).readValue(url);
    }

    /**
     * Вычисление вектора Шепли для всех игроков кооперативной игры. Вычисления проводятся параллельно, в качестве
     * ответа возвращается ассоциативный массив с парой "игрок" - "значение вектора Шепли"
     * @see ShapleyValue#avgShapleyValue(P)
     */
    public static <P> Map<P, Double> shapleyValues(CooperateGame<P> game) {
        Set<P> players = game.getPlayers();
        ShapleyValue<P> sv = new ShapleyValue<>(game);

        return players.parallelStream()
                .collect(Collectors.toMap(player -> player, sv::avgShapleyValue));
    }


}
