package io.qthibeau.parser;

import org.javatuples.Pair;
import com.google.common.collect.ImmutableList;

class Matchers {
    public static ParserMatcher tag(String value) {
        return (word, output) -> {
            if (!word.equalsIgnoreCase(value + ":")) {
                throw new ParseError();
            }

            return new ParseOutput(
                new Pair<String, ImmutableList<String>>(value, ImmutableList.of()),
                new ImmutableList.Builder<Pair<String, ImmutableList<String>>>()
                    .addAll(output.list)
                    .add(output.data)
                    .build()
            );
        };
    }

    public static ParserMatcher word() {
        return (word, output) -> {
            return new ParseOutput(
                output.data.setAt1(
                    new ImmutableList.Builder<String>()
                        .addAll(output.data.getValue1())
                        .add(word)
                        .build()
                ),
                output.list
            );
        };
    }
}
