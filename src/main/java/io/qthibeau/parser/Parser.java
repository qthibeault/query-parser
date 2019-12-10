package io.qthibeau.parser;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import com.google.common.collect.ImmutableList;

class Parser {
    public static List<Pair<String, List<String>>> parse(String input, List<ParserMatcher> matchers) {
        String[] words = input.split(" ");

        ParseOutput outputs = Arrays.stream(words)
            .map(word -> word.trim())
            .filter(word -> word.length() > 0)
            .reduce(
                new ParseOutput(new Pair<String, ImmutableList<String>>("", ImmutableList.of()), ImmutableList.of()),
                (results, word) -> {
                    ParseResult parseResult = matchers.stream().reduce(
                        ParseResults.started(results),
                        (result, matcher) -> ParseResults.caseOf(result)
                            .completed_(result)
                            .started(output -> {
                                try {
                                    return ParseResults.completed(matcher.apply(word, output));
                                }
                                catch (ParseError e) {
                                    return result;
                                }
                            }),
                    (current, previous) -> current);

                    return ParseResults.getOutput(parseResult);
                },
                (current, previous) -> current
            );


        return new ImmutableList.Builder<Pair<String, ImmutableList<String>>>()
            .addAll(outputs.list)
            .add(outputs.data)
            .build()
            .stream()
            .map(pair -> pair.setAt1(pair.getValue1().stream().collect(Collectors.toList())))
            .collect(Collectors.toList());
    }
}
