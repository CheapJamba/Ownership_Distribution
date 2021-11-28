import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A wrapper class for {@code List}. Provides temporary locking functionality.
 * When {@code LockableList} instance is locked, {@code add()} and {@code remove()}
 * operations aren't executed right away but rather stored in a queue and executed
 * in the same order they've been called, as soon as it's unlocked.
 *
 * Typically to be used to avoid {@link java.util.ConcurrentModificationException}.
 *
 * Contents can be accessed through an inner List, which is returned by {@code getInnerList}
 * method.
 *
 * @param <T> Stored objects type.
 */
public class LockableList<T> {

    /**
     * List for storing elements.
     */
    private List<T> innerList;

    /**
     * Queue for storing operations when list is locked;
     */
    private Queue<Command> commandQueue;

    private boolean isLocked;

    LockableList() {
        innerList = new ArrayList<>();
        commandQueue = new ArrayDeque<>();
        isLocked = false;
    }

    /**
     * Locks the list. When list is locked add {@code add()} and {@code remove()} are stored until
     * {@code unlock()} method is called;
     */
    public void lock() {
        isLocked = true;
    }

    /**
     * Unlocks the list and executes all stored operations.
     */
    public void unlock() {
        isLocked = false;
        while (commandQueue.peek() != null) {
            commandQueue.poll().execute();
        }
    }

    /**
     * If list is unlocked, adds the data to it right away. Otherwise, adds an {@code add(data)} command
     * to a {@link #commandQueue commandQueue}.
     * @param data object to add to the list.
     * @return {@code true} if the element was added to {@link #innerList innerList}, or a
     *          command was added to {@link #commandQueue commandQueue}, else
     *         {@code false}
     */
    public boolean add(T data) {
        if (isLocked) {
            return commandQueue.offer(new Command(data, innerList::add));
        }
        else {
            return innerList.add(data);
        }
    }

    /**
     * If list is unlocked, removes the data from it right away. Otherwise, adds an {@code remove(data)} command
     * to a {@link #commandQueue commandQueue}.
     * @param data object to remove from the list.
     * @return {@code true} if the element was removed from {@link #innerList innerList}, or a
     *          command was added to {@link #commandQueue commandQueue}, else
     *         {@code false}
     */
    public boolean remove(T data) {
        if (isLocked) {
            return commandQueue.offer(new Command(data, innerList::remove));
        }
        else {
            return innerList.remove(data);
        }
    }

    public List<T> getInnerList() {
        return innerList;
    }

    private class Command {

        T data;

        Operation<T> operation;

        Command(T data, Operation<T> operation) {
            this.data = data;
            this.operation = operation;
        }

        public void execute() {
            operation.change(data);
        }
    }

    private interface Operation<K> {
        boolean change(K data);
    }
}
