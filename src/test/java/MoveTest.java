import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoveTest {

    Owner oppressor;

    Owner victim;

    Move subject;

    MoveTest() {
        oppressor = Mockito.mock(Owner.class);
        victim = Mockito.mock(Owner.class);
        subject = new Move(oppressor, victim);
    }

    @Test
    void compareTo() {
        Owner oppressor1 = Mockito.mock(Owner.class);
        Owner oppressor2 = Mockito.mock(Owner.class);
        Owner victim1 = Mockito.mock(Owner.class);
        Owner victim2 = Mockito.mock(Owner.class);

        Mockito.when(oppressor1.findOwnedIntersection(ArgumentMatchers.any())).thenReturn(null);
        Mockito.when(oppressor2.findOwnedIntersection(ArgumentMatchers.any())).thenReturn(Mockito.mock(Valuable.class));

        Valuable mockOpportunity = Mockito.mock(Valuable.class);
        Mockito.when(mockOpportunity.subscribe(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(oppressor2.findOwnedIntersection(ArgumentMatchers.any())).thenReturn(mockOpportunity);

        Move move1 = new Move(oppressor1, victim1);
        Move move2 = new Move(oppressor2, victim2);

        Mockito.when(oppressor1.getOwnedAmount()).thenReturn(10);
        Mockito.when(victim1.getOwnedAmount()).thenReturn(2);
        Mockito.when(oppressor2.getOwnedAmount()).thenReturn(19);
        Mockito.when(victim2.getOwnedAmount()).thenReturn(3);

        assertEquals(move1.score(), 8);
        assertEquals(move2.score(), 16);

        assertTrue(move1.compareTo(move2) < 0);

        List<Move> movesToSort = new ArrayList<>();
        movesToSort.add(move1);
        movesToSort.add(move2);
        assertEquals(Collections.max(movesToSort), move2);
    }
}