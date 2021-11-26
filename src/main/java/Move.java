public class Move implements Comparable<Move>, ValuableListener{

    private Owner oppressor;

    private Owner victim;

    private Valuable opportunity;

    public double score() {
        return oppressor.getOwnedAmount() - victim.getOwnedAmount();
    }

    public boolean isReversed() {
        return (score() < 0);
    }

    public Move(Owner oppressor, Owner victim) {
        this.oppressor = oppressor;
        this.victim = victim;
        findOpportunity();
    }

    public void findOpportunity() {
        if (opportunity != null) {
            opportunity.unsubscribe(this);
        }
        if (isReversed()) {
            opportunity = victim.findOwnedIntersection(oppressor);
        } else {
            opportunity = oppressor.findOwnedIntersection(victim);
        }
        if (opportunity != null) {
            opportunity.subscribe(this);
        }
    }

    public Owner getOppressor() {
        return oppressor;
    }

    public Owner getVictim() {
        return victim;
    }

    @Override
    public int compareTo(Move other) {
        if (other.opportunity != null) {
            if (this.opportunity == null) {
                return -1;
            }
        } else {
            if (this.opportunity != null) {
                return 1;
            }
        }
        if ((Math.abs(this.score()) - Math.abs(other.score())) > 0) {
            return 1;
        } else if ((Math.abs(this.score()) - Math.abs(other.score())) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean isAvailable() {
        return (Math.abs(score()) > 1 && opportunity != null);
    }

    public void execute() {
        opportunity.executeMove(this);
    }

    @Override
    public void onContestantRemoved(Valuable sender) {

    }

    @Override
    public void onOwnerChanged(Valuable sender) {
        findOpportunity();
    }
}
