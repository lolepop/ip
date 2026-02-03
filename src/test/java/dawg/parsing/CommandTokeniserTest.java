package dawg.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class CommandTokeniserTest {

    @Test
    public void emptyInput_behaviour() {
        CommandTokeniser t = new CommandTokeniser("   ");
        assertTrue(t.isEmpty(), "should be treated as blank input");
        assertTrue(!t.hasMoreTokens());
        assertTrue(!t.nextString().isPresent());
    }

    @Test
    public void standardInput_expected_success() {
        CommandTokeniser t = new CommandTokeniser("one two three");

        assertTrue(!t.isEmpty());
        assertTrue(t.hasMoreTokens());

        Optional<String> a = t.nextString();
        assertTrue(a.isPresent());
        assertEquals("one", a.get());

        Optional<String> b = t.nextString();
        assertTrue(b.isPresent());
        assertEquals("two", b.get());

        Optional<String> c = t.nextString();
        assertTrue(c.isPresent());
        assertEquals("three", c.get());

        assertTrue(!t.hasMoreTokens(), "all tokens should be consumed");
        assertTrue(!t.nextString().isPresent());
    }

    @Test
    public void testConstructor_trimsSpaces_success() {
        CommandTokeniser t = new CommandTokeniser("   alpha beta   ");
        assertEquals("alpha", t.nextString().orElseThrow());
        assertEquals("beta", t.nextString().orElseThrow());
    }

    @Test
    public void invalidIntParsed_throws() {
        CommandTokeniser t = new CommandTokeniser("10 20 abc");

        Optional<Integer> v1 = t.nextInt();
        assertTrue(v1.isPresent());
        assertEquals(10, v1.get().intValue());

        Optional<Integer> v2 = t.nextInt();
        assertTrue(v2.isPresent());
        assertEquals(20, v2.get().intValue());

        assertThrows(NumberFormatException.class, t::nextInt);
    }
}
