package math.game.util;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegerSetDeserializer extends KeyDeserializer {
    @Override
    public Set<Integer> deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        String substring = s.substring(1, s.length() - 1);
        if (substring.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(substring.split(",\s*"))
                .map(Integer::parseInt).collect(Collectors.toSet());
    }
}
