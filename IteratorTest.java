import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class IteratorTest {

    private List<String> list;
    private Iterator<String> itr;

    @Before
    public void setUp()         // set up test fixture
    {
        list = new ArrayList<String>();
        list.add("apple");
        list.add("game");
        itr = list.iterator();
    }

    // 3 Tests for Iterator method hasNext() - testing for C1 and C5
    @Test
    public void testHasNext() {
        assertTrue(itr.hasNext());
    }

    @Test
    public void testHasNext_C1() {
        itr.next();
        itr.next();
        assertFalse(itr.hasNext());
    }

    // test is expected to fail
    @Test(expected = ConcurrentModificationException.class)
    public void testHasNext_C5() {
        list.add("ps5");
        itr.hasNext();
    }


    // 4 Tests for Iterator method next() - testing for C1, C2, C5
    @Test
    public void testNext() {
        assertEquals("apple", itr.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNext_C1() {
        itr.next();
        itr.next();
        itr.next();
    }

    @Test
    public void testNext_C2() {
        list = new ArrayList<String>();
        list.add(null);
        itr = list.iterator();
        assertNull(itr.next());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testNext_C5() {
        list.add("balls");
        itr.next();
    }

    // 6 Tests for Iterator method remove() - testing for C1, C2, C3, C4, C5
    @Test
    public void testRemove_BaseCase() {
        itr.next();
        itr.remove();
        assertFalse(list.contains("apple"));
    }

    @Test
    public void testRemove_C1() {
        itr.next();
        itr.next();
        itr.remove();
        assertFalse(list.contains("game"));
    }

    @Test
    public void testRemove_C2() {
        list.add(null);
        list.add("balls");
        itr = list.iterator();
        itr.next();
        itr.next();
        itr.next();
        itr.remove();
        assertFalse(list.contains(null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove_C3() {
        list = Collections.unmodifiableList(list);
        itr = list.iterator();
        itr.next();
        itr.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void testRemove_C4() {
        itr.remove();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testRemove_C5() {
        itr.next();
        list.add("bro");
        itr.remove();
    }
}
