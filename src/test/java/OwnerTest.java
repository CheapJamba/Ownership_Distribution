import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void getPermitted() {
    }

    @Test
    void addPermitted() {
    }

    @Test
    void addPermitted1() {
    }

    @Test
    void valuableIsPermitted() {
    }

    @Test
    void cascadeOwned() {
    }

    @Test
    void compareTo() {
        Owner lowPrioOwner = new Owner(true, new Valuable[]{Mockito.mock(Valuable.class)});
        Owner normalPrioOwner = new Owner(false, new Valuable[]{Mockito.mock(Valuable.class)});

        List<Owner> prioList = new ArrayList<>();
        prioList.add(lowPrioOwner);
        prioList.add(normalPrioOwner);
        assertEquals(Collections.max(prioList), normalPrioOwner);
    }
}