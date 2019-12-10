package io.qthibeau.parser;

import org.javatuples.Pair;
import com.google.common.collect.ImmutableList;

public class ParseOutput {
    Pair<String, ImmutableList<String>> data;
    ImmutableList<Pair<String, ImmutableList<String>>> list;

    public ParseOutput(Pair<String, ImmutableList<String>> data, ImmutableList<Pair<String, ImmutableList<String>>> list)
    {
        this.data = data;
        this.list = list;
    }
}

