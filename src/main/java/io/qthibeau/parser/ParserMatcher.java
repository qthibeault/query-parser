package io.qthibeau.parser;

import java.util.function.BiFunction;

interface ParserMatcher extends BiFunction<String, ParseOutput, ParseOutput> {
    @Override
    public ParseOutput apply(String arg1, ParseOutput arg2) throws ParseError;
}
