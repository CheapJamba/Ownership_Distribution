import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockableListTest {

    LockableList<String> testList = new LockableList<>();

    @BeforeEach
    void setUp() {
        testList = new LockableList<>();
        testList.add("First");
        testList.add("Second");
    }

    @Test
    void addTest() {
        assertTrue(testList.add("Third"));
        assertEquals(testList.getInnerList().size(), 3);
        assertEquals(testList.getInnerList().get(2), "Third");

        testList.lock();
        assertTrue(testList.add("Fourth"));
        assertEquals(testList.getInnerList().size(), 3);
        assertFalse(testList.getInnerList().contains("Fourth"));
        testList.add("Fifth");
        testList.unlock();
        assertEquals(testList.getInnerList().size(), 5);
        assertEquals(testList.getInnerList().get(3), "Fourth");
        assertEquals(testList.getInnerList().get(4), "Fifth");
    }

    @Test
    void removeTest() {
        assertFalse(testList.remove("Third"));
        testList.add("Third");
        assertEquals(testList.getInnerList().size(), 3);
        assertEquals(testList.getInnerList().get(2), "Third");
        assertTrue(testList.remove("Third"));
        assertEquals(testList.getInnerList().size(), 2);
        assertFalse(testList.getInnerList().contains("Third"));

        testList.lock();
        assertTrue(testList.remove("Second"));
        assertTrue(testList.remove("Fourth"));
        assertTrue(testList.add("Fourth"));
        assertEquals(testList.getInnerList().size(), 2);
        assertTrue(testList.getInnerList().contains("Second"));
        assertFalse(testList.getInnerList().contains("Fourth"));
        testList.unlock();

        assertEquals(testList.getInnerList().size(), 2);
        assertFalse(testList.getInnerList().contains("Second"));
        assertTrue(testList.getInnerList().contains("Fourth"));
    }
}