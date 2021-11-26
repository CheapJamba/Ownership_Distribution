import java.util.*;

public class Owner implements Comparable<Owner>, ValuableListener {

    private Set<Valuable> permitted;

    private Set<Valuable> owned;

    private boolean lowPrio;

    public Owner(boolean lowPrio, Valuable[] valuables) {
        this.lowPrio = lowPrio;
        permitted = new HashSet<>();
        owned = new HashSet<>();
        for (Valuable valuable : valuables) {
            permitted.add(valuable);
            valuable.addContestant(this);
        }
    }

    public boolean valuableIsPermitted(Valuable valuable) {
        return permitted.contains(valuable);
    }

    public int compareTo(Owner o) {
        if (this.lowPrio && !o.lowPrio) {
            return -1;
        }
        if (!this.lowPrio && o.lowPrio) {
            return 1;
        }
        return o.owned.size() - this.owned.size();
    }

    public void dismiss() {
        Set<Valuable> copyOwned = new HashSet<>(owned);
        for (Valuable val : copyOwned) {
            val.removeContestant(this);
        }
    }

    public int getOwnedAmount() {
        return owned.size();
    }

    public Valuable findOwnedIntersection(Owner other) {
        for (Valuable ownedVal : owned) {
            if (other.valuableIsPermitted(ownedVal)) {
                return ownedVal;
            }
        }
        return null;
    }

    public boolean isLowPrio() {
        return lowPrio;
    }

    @Override
    public void onContestantRemoved(Valuable sender) {

    }

    @Override
    public void onOwnerChanged(Valuable sender) {
        if (sender.getOwner() == this) {
            owned.add(sender);
        } else {
            owned.remove(sender);
        }
    }
}