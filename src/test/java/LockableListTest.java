import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockableListTest {

    LockableList<String> testList = new LockableList<>();

    @Test
    void addTest() {
        testList.lock();
        testList.add("First");
        testList.add("Second");
        assertTrue(testList.getList().isEmpty());
        testList.unlock();
        assertEquals(testList.getList().size(), 2);
        assertTrue(testList.getList().contains("First"));
        assertTrue(testList.getList().contains("Second"));
    }
}