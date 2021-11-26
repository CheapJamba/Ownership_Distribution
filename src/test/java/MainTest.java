import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void ProvidedTestCase() {
        Arrangement arrangement = new Arrangement(10);
        HashMap<String, int[]> owned = new HashMap<>();

        arrangement.addOwner("S1", false, 2, 3);
        owned.put("S1", new int[]{2, 3});
        checkOwnership(arrangement, new int[]{0, 1, 4, 5, 6, 7, 8, 9}, owned);

        arrangement.addOwner("S2", false, 2, 4, 5);
        owned.put("S2", new int[]{4, 5});
        checkOwnership(arrangement, new int[]{0, 1, 6, 7, 8, 9}, owned);

        arrangement.addOwner("S3", false, 2);
        owned.put("S1", new int[]{3});
        owned.put("S3", new int[]{2});
        checkOwnership(arrangement, new int[]{0, 1, 6, 7, 8, 9}, owned);

        arrangement.removeOwner("S2");
        owned.remove("S2");
        checkOwnership(arrangement, new int[]{0, 1, 4, 5, 6, 7, 8, 9}, owned);

        arrangement.addOwner("S4", true, 2, 3);
        checkOwnership(arrangement, new int[]{0, 1, 4, 5, 6, 7, 8, 9}, owned);
    }

    @Test
    void AlternativeTestCase() {
        Arrangement arrangement = new Arrangement(12);
        HashMap<String, int[]> owned = new HashMap<>();

        arrangement.addOwner("S1", true, 0, 3, 6, 8, 10);
        owned.put("S1", new int[]{0, 3, 6, 8, 10});
        checkOwnership(arrangement, new int[]{1, 2, 4, 5, 7, 9, 11}, owned);

        arrangement.addOwner("S2", true, 0, 1, 5, 7, 9);
        owned.put("S1", new int[]{3, 6, 8, 10});
        owned.put("S2", new int[]{0, 1, 5, 7, 9});
        checkOwnership(arrangement, new int[]{2, 4, 11}, owned);

        arrangement.addOwner("S3", false, 0, 2, 5, 6, 7, 9, 10, 11);
        owned.put("S1", new int[]{3, 8});
        owned.put("S2", new int[]{1});
        owned.put("S3", new int[]{0, 2, 5, 6, 7, 9, 10, 11});
        checkOwnership(arrangement, new int[]{4}, owned);

        arrangement.addOwner("S4", false, 1, 3, 4, 5, 11);
        owned.put("S1", new int[]{8});
        owned.put("S2", new int[]{});
        owned.put("S3", new int[]{0, 2, 6, 7, 9, 10});
        owned.put("S4", new int[]{1, 3, 4, 5, 11});
        checkOwnership(arrangement, new int[]{}, owned);
    }



    private void checkOwnership(Arrangement arrangement, int[] ownerless, HashMap<String, int[]> ownerTagToOwnedIndexesMap) {
        for (int index : ownerless) {
            assertNull(arrangement.getValuables().get(index).getOwner());
        }
        for (String key : ownerTagToOwnedIndexesMap.keySet()) {
            for (int index : ownerTagToOwnedIndexesMap.get(key)) {
                assertEquals(arrangement.getValuables().get(index).getOwner(), arrangement.getOwner(key));
            }
        }
    }
}
