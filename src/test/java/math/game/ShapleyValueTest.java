package math.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import math.game.exceptions.PersonalIdentityException;
import math.game.exceptions.SuperAdditivityException;
import math.game.resource.CooperateGameResource;
import math.game.util.CooperateGameUtil;
import math.game.util.Shareholder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

public class ShapleyValueTest {

    private <P> void shapleyValuesTest(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        Map<P, Double> actualShapleyValues = CooperateGameUtil.shapleyValues(game);
        Assertions.assertEquals(expectedShapleyValues, actualShapleyValues);
    }

    private <P> void shapleyValuesTest(CooperateGame<P> game, Map<P, Double> expectedShapleyValues, double delta) {
        Map<P, Double> actualShapleyValues = CooperateGameUtil.shapleyValues(game);

        for (Map.Entry<P, Double> entry: expectedShapleyValues.entrySet()) {
            double actualValue = actualShapleyValues.get(entry.getKey());
            double expectedValue = entry.getValue();
            assertThat(String.format("The actual value - %f, the expected value - %f", actualValue, expectedValue),
                    Math.abs(actualValue - expectedValue) < delta);
        }
    }

    /** Testing empty game (with empty set of players) */
    @ParameterizedTest
    @MethodSource("emptyGame_Success_Data")
    public <P> void testEmptyGame_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> emptyGame_Success_Data() {
        CooperateGame<?> emptyGame = new CooperateGame<>(Collections.emptySet(), coalition -> 0d);
        Map<?, Double> shapleyValues = Collections.emptyMap();

        return Stream.of(Arguments.of(emptyGame, shapleyValues));
    }

    /** Testing unary game (with one player) */
    @ParameterizedTest
    @MethodSource("unaryGame_Success_Data")
    public <P> void testUnaryGame_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> unaryGame_Success_Data() throws IOException {
        CooperateGame<Integer> game = readGameResource("unary_game_input.json").toGame();
        Map<Integer, Double> shapleyValues = readShapleyValues("unary_game_output.json");

        return Stream.of(Arguments.of(game, shapleyValues));
    }

    /** Testing binary game (with two players) */
    @ParameterizedTest
    @MethodSource("binaryGame_Success_Data")
    public <P> void testBinaryGame_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> binaryGame_Success_Data() throws IOException {
        CooperateGame<Integer> game = readGameResource("binary_game_input.json").toGame();
        Map<Integer, Double> shapleyValues = readShapleyValues("binary_game_output.json");

        return Stream.of(Arguments.of(game, shapleyValues));
    }

    /** Testing function on personal identity property */
    @ParameterizedTest
    @MethodSource("throwPersonalIdentityException_Success_Data")
    public <P, E extends Throwable> void testThrowPersonalIdentityException_Success(Set<P> players, 
                                                                                    IDoubleFunction<Set<P>> function, 
                                                                                    Class<E> exceptionClass) {
        Assertions.assertThrows(exceptionClass, () -> new CooperateGame<>(players, function));
    }

    public static Stream<Arguments> throwPersonalIdentityException_Success_Data() throws IOException {
        CooperateGameResource gameResource = readGameResource("game_pe.json");
        return Stream.of(Arguments.of(gameResource.getPlayers(), gameResource.getIDoubleFunction(),
                PersonalIdentityException.class));
    }

    /** Testing function on super-additivity property */
    @ParameterizedTest
    @MethodSource("throwSuperAdditivityException_Success_Data")
    public <P, E extends Throwable> void testThrowSuperAdditivityException_Success(Set<P> players,
                                                                                   IDoubleFunction<Set<P>> function,
                                                                                   Class<E> exceptionClass) {
        Assertions.assertThrows(exceptionClass, () -> new CooperateGame<>(players, function));
    }

    public static Stream<Arguments> throwSuperAdditivityException_Success_Data() throws IOException {
        CooperateGameResource gameResource = readGameResource("game_sae.json");
        return Stream.of(Arguments.of(gameResource.getPlayers(), gameResource.getIDoubleFunction(),
                SuperAdditivityException.class));
    }

    /**
     * <img src="../../../resources/math/game/img/game_1.png"/>
     * */
    @ParameterizedTest
    @MethodSource("game_1_Success_Data")
    public <P> void testGame_1_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> game_1_Success_Data() throws IOException {
        CooperateGame<Integer> game = readGameResource("game_1_input.json").toGame();
        Map<Integer, Double> shapleyValues = readShapleyValues("game_1_output.json");

        return Stream.of(Arguments.of(game, shapleyValues));
    }

    /**
     * <img src="../../../resources/math/game/img/game_2.png"/>
     * */
    @ParameterizedTest
    @MethodSource("game_2_Success_Data")
    public <P> void testGame_2_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> game_2_Success_Data() throws IOException {
        CooperateGame<Integer> game = readGameResource("game_2_input.json").toGame();
        Map<Integer, Double> shapleyValues = readShapleyValues("game_2_output.json");

        return Stream.of(Arguments.of(game, shapleyValues));
    }

    /**
     * <img src="../../../resources/math/game/img/game_3.png"/>
     * */
    @ParameterizedTest
    @MethodSource("game_3_Success_Data")
    public <P> void testGame_3_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues) {
        shapleyValuesTest(game, expectedShapleyValues);
    }

    public static Stream<Arguments> game_3_Success_Data() throws IOException {
        CooperateGame<Integer> game = readGameResource("game_3_input.json").toGame();
        Map<Integer, Double> shapleyValues = readShapleyValues("game_3_output.json");

        return Stream.of(Arguments.of(game, shapleyValues));
    }

    /**
     * <img src="../../../resources/math/game/img/UNSecurityCouncil.png"/>
     */
    @ParameterizedTest
    @MethodSource("UNSecurityCouncil_Success_Data")
    public <P> void testUNSecurityCouncil_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues,
                                                  double delta) {
        shapleyValuesTest(game, expectedShapleyValues, delta);
    }

    public static Stream<Arguments> UNSecurityCouncil_Success_Data() {
        CooperateGame<Integer> game = getUNSecurityCouncilGame();
        Map<Integer, Double> shapleyValues = getUNSecurityCouncilShapleyValues();
        return Stream.of(Arguments.of(game, shapleyValues, 0.0001d));
    }

    private static Map<Integer, Double> getUNSecurityCouncilShapleyValues() {
        Map<Integer, Double> councilShapleyValues = new HashMap<>(15);
        int i = 1;
        for (; i <= 5; i++) {
            councilShapleyValues.put(i, 0.1962d);
        }

        for(; i <=15; i++) {
            councilShapleyValues.put(i, 0.001865d);
        }

        return councilShapleyValues;
    }

    private static CooperateGame<Integer> getUNSecurityCouncilGame() {
        Set<Integer> players = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        Set<Integer> permanentPlayers = Set.of(1, 2, 3, 4, 5);

        IDoubleFunction<Set<Integer>> function = coalition -> {
            if (coalition.containsAll(permanentPlayers) && coalition.size() >= 9) {
                return 1;
            }

            return 0;
        };

        return new CooperateGame<>(players, function);
    }

    /**
     * <img src="../../../resources/math/game/img/shareholders.png"/>
     * */
    @ParameterizedTest
    @MethodSource("shareholders_Success_Data")
    public <P> void testShareholders_Success(CooperateGame<P> game, Map<P, Double> expectedShapleyValues,
                                              double delta) {
        shapleyValuesTest(game, expectedShapleyValues, delta);
    }

    public static Stream<Arguments> shareholders_Success_Data() {
        CooperateGame<Shareholder> game = getShareholderGame();
        Map<Shareholder, Double> shapleyValues = getShareholderShapleyValues();
        return Stream.of(Arguments.of(game, shapleyValues, 0.0001d));
    }

    private static Map<Shareholder, Double> getShareholderShapleyValues() {
        Set<Shareholder> players = getShareholders();
        Map<Integer, Double> shapleyValues = Map.of(1, 1d/12, 2, 1d/4, 3, 1d/4, 4, 5d/12);

        return players.stream().collect(Collectors.toMap(shareholder -> shareholder,
                shareholder -> shapleyValues.get(shareholder.getID())));
    }

    private static CooperateGame<Shareholder> getShareholderGame() {
        Set<Shareholder> players = getShareholders();

        IDoubleFunction<Set<Shareholder>> function = coalition -> {
            long actionSum = coalition.stream().mapToInt(Shareholder::getActionsCount).sum();
            if (actionSum > 50) {
                return 1;
            }

            return 0;
        };

        return new CooperateGame<>(players, function);
    }

    private static Set<Shareholder> getShareholders() {
        Set<Shareholder> players = new HashSet<>(4);
        for (int i = 1; i <= 4; i++) {
            Shareholder shareholder = new Shareholder(i);
            shareholder.setActionsCount(10 * i);
            players.add(shareholder);
        }
        return players;
    }

    private static Map<Integer, Double> readShapleyValues(String fileName) throws IOException {
        TypeReference<HashMap<Integer, Double>> typeRef = new TypeReference<>() {};
        return new ObjectMapper().readValue(Paths.get("src", "test", "resources", "math", "game", "data", fileName).toFile(), typeRef);
    }

    private static CooperateGameResource readGameResource(String fileName) throws IOException {
        return CooperateGameUtil.readGame(Paths.get("src", "test", "resources", "math", "game", "data", fileName).toUri().toURL());
    }

}
