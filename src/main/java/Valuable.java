import java.util.*;

public class Valuable{

    private Set<Owner> contestants;

    private Owner owner;

    private LockableList<ValuableListener> listeners;

    public Valuable() {
        contestants = new HashSet<>();
        listeners = new LockableList<>();
    }

    public void addContestant(Owner newContestant) {
        contestants.add(newContestant);
        if (owner == null) {
            setOwner(newContestant);
            return;
        }
        if (owner.isLowPrio()) {
            determineOwner();
        }
    }

    public Owner getOwner() {
        return owner;
    }

    public boolean removeContestant(Owner toRemove) {
        boolean result = contestants.remove(toRemove);
        if (result) {
            for (ValuableListener listener : listeners.getInnerList()) {
                listener.onContestantRemoved(this);
            }
        }
        if (owner == toRemove) {
            determineOwner();
        }
        return result;
    }

    private Owner determineOwner() {
        if (contestants.isEmpty()) {
            setOwner(null);
        } else {
            Owner mostFit = Collections.max(contestants);
            if (owner != mostFit) {
                setOwner(mostFit);
            }
        }
        return owner;
    }

    public void executeMove(Move move) {
        Owner oppressor;
        Owner victim;
        if (move.isReversed()) {
            victim = move.getOppressor();
            oppressor = move.getVictim();
        } else {
            oppressor = move.getOppressor();
            victim = move.getVictim();
        }
        if (owner != oppressor || !contestants.contains(victim)) {
            throw new IllegalStateException("Suggested move cannot be executed on this item: either oppressor doesn't " +
                    "own this item, or the item isn't permitted for the victim.");
        }
        setOwner(victim);
    }

    private void setOwner(Owner newOwner) {
        Owner previousOwner = owner;
        owner = newOwner;
        if (previousOwner != null) {
            previousOwner.onOwnerChanged(this);
        }
        if (newOwner != null) {
            newOwner.onOwnerChanged(this);
        }
        listeners.lock();
        for (ValuableListener listener : listeners.getInnerList()) {
            listener.onOwnerChanged(this);
        }
        listeners.unlock();
    }

    public boolean subscribe(ValuableListener listener) {
        return listeners.add(listener);
    }

    public boolean unsubscribe(ValuableListener listener) {
        return listeners.remove(listener);
    }
}
