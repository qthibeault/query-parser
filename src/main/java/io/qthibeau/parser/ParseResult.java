package io.qthibeau.parser;

import org.derive4j.Data;

@Data
public interface ParseResult {

    interface Cases<R> {
        R completed(ParseOutput output);
        R started(ParseOutput output);
    }

    <R> R match(Cases<R> cases);
}
