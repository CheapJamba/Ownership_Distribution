import java.util.*;
import java.util.stream.Collectors;

public class Arrangement {

    private Map<String, Owner> owners;

    private List<Valuable> valuables;

    private Map<Owner, Map<Owner, Move>> movesTable;

    public Arrangement(int valuablesAmount) {
        valuables = new ArrayList<>();
        for (int i = 0; i < valuablesAmount; i++) {
            valuables.add(new Valuable());
        }
        owners = new HashMap<>();
        movesTable = new HashMap<>();
    }

    public boolean addOwner(String tag, boolean lowprio, int... valIndexes) {
        if(owners.containsKey(tag)) {
            return false;
        } else {
            Valuable[] newOwnerPermitted = new Valuable[valIndexes.length];
            for (int i = 0; i < newOwnerPermitted.length; i++) {
                newOwnerPermitted[i] = valuables.get(valIndexes[i]);
            }
            Owner newOwner = new Owner(lowprio, newOwnerPermitted);
            owners.put(tag, newOwner);

            if (!lowprio) {
                movesTable.put(newOwner, new HashMap<>());
                List<Owner> normalPrioOwners = owners.values().stream().filter(o -> !o.isLowPrio()).collect(Collectors.toList());
                normalPrioOwners.forEach((Owner otherOwner) -> {
                    if (otherOwner != newOwner) {
                        movesTable.get(newOwner).put(otherOwner, new Move(otherOwner, newOwner));
                    }
                });
                balance();
            }


            return true;
        }
    }

    public boolean removeOwner(String tag) {
        if(owners.containsKey(tag)) {
            Owner removedOwner = owners.get(tag);
            owners.remove(tag);
            removedOwner.dismiss();
            movesTable.remove(removedOwner);
            for (Owner other : movesTable.keySet()) {
                movesTable.get(other).remove(removedOwner);
            }
            balance();
            return true;
        } else {
            return false;
        }
    }

    public void balance() {
        Collection<Move> moveCollection = new HashSet<>();
        for(Owner key : movesTable.keySet()) {
            moveCollection.addAll(movesTable.get(key).values());
        }
        if (!moveCollection.isEmpty()) {
            boolean keepGoing = true;
            while (keepGoing) {
                Move nextMove = Collections.max(moveCollection);
                if (!nextMove.isAvailable()) {
                    keepGoing = false;
                } else {
                    nextMove.execute();
                }
            }
        }
    }

    public Owner getOwner(String tag) {
        return owners.get(tag);
    }

    public List<Valuable> getValuables() {
        return valuables;
    }

    public void print() {
        String formatCell = "%-12s ";
        String formatEnder = "%-12s%n";

        System.out.printf(formatCell, "*blank*");
        for (int i = 0; i < valuables.size() - 1; i++) {
            System.out.printf(formatCell, "Valuable " + i);
        }
        System.out.printf(formatEnder, "Valuable " + (valuables.size() - 1));

        for (String key : owners.keySet()) {
            Owner currentOwner = owners.get(key);
            System.out.printf(formatCell, key);
            for (int j = 0; j < valuables.size() - 1; j++) {
                if (valuables.get(j).getOwner() == currentOwner) {
                    System.out.printf(formatCell, "X");
                } else {
                    System.out.printf(formatCell, "O");
                }
            }
            if (valuables.get(valuables.size() - 1).getOwner() == currentOwner) {
                System.out.printf(formatEnder, "X");
            } else {
                System.out.printf(formatEnder, "O");
            }
        }
    }
}
