package io.qthibeau.parser;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.javatuples.Pair;

import static io.qthibeau.parser.Matchers.*;
import static org.junit.Assert.*;

public class ParserTest {
    List<ParserMatcher> matchers = Arrays.asList();

    @Before public void setupMatchers() {
        matchers = Arrays.asList(
            tag("name"),
            tag("category"),
            word()
        );
    }

    @Test public void testTagParsing() {
        List<Pair<String, List<String>>> tags = Parser.parse("foo name: bar category: baz", matchers);

        assertEquals(3, tags.size());

        assertEquals("", tags.get(0).getValue0());
        assertEquals("foo", tags.get(0).getValue1().get(0));

        assertEquals("name", tags.get(1).getValue0());
        assertEquals("bar", tags.get(1).getValue1().get(0));

        assertEquals("category", tags.get(2).getValue0());
        assertEquals("baz", tags.get(2).getValue1().get(0));
    }

    @Test public void testTagNoMatches() {
        List<Pair<String, List<String>>> tags = Parser.parse("foo name: bar", matchers);

        assertEquals(2, tags.size());

        assertEquals("", tags.get(0).getValue0());
        assertEquals("foo", tags.get(0).getValue1().get(0));

        assertEquals("name", tags.get(1).getValue0());
        assertEquals("bar", tags.get(1).getValue1().get(0));
    }

    @Test public void testTagNotSpecified() {
        List<Pair<String, List<String>>> tags = Parser.parse("foo name: bar class: quux", matchers);

        assertEquals(2, tags.size());

        assertEquals("", tags.get(0).getValue0());
        assertEquals("foo", tags.get(0).getValue1().get(0));

        assertEquals("name", tags.get(1).getValue0());
        assertEquals("bar", tags.get(1).getValue1().get(0));
        assertEquals("class:", tags.get(1).getValue1().get(1));
        assertEquals("quux", tags.get(1).getValue1().get(2));
    }

    @Test public void testTagDuplicate() {
        List<Pair<String, List<String>>> tags = Parser.parse("foo name: bar name: quux", matchers);

        assertEquals(3, tags.size());

        assertEquals("", tags.get(0).getValue0());
        assertEquals("foo", tags.get(0).getValue1().get(0));

        assertEquals("name", tags.get(1).getValue0());
        assertEquals("bar", tags.get(1).getValue1().get(0));


        assertEquals("name", tags.get(2).getValue0());
        assertEquals("quux", tags.get(2).getValue1().get(0));
    }

    @Test public void testNoMatchers() {
        List<Pair<String, List<String>>> tags = Parser.parse("foo name: bar name: quux", Arrays.asList());

        assertEquals(1, tags.size());
        assertEquals("", tags.get(0).getValue0());
        assertEquals(0, tags.get(0).getValue1().size());
    }
}
