import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LockableList<T> {

    public List<T> list;

    public Queue<Command> commandQueue;

    private boolean isLocked;

    LockableList() {
        list = new ArrayList<>();
        commandQueue = new ArrayDeque<>();
        isLocked = false;
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
        while (commandQueue.peek() != null) {
            commandQueue.poll().execute();
        }
    }

    public boolean add(T data) {
        if (isLocked) {
            if(!commandQueue.offer(new Command(data, list::add))) {
                throw new RuntimeException("WTF is wrong?");
            }
            return true;
        }
        else {
            return list.add(data);
        }
    }

    public boolean remove(T data) {
        if (isLocked) {
            if(!commandQueue.offer(new Command(data, list::remove))) {
                throw new RuntimeException("WTF is wrong?");
            }
            return true;
        }
        else {
            return list.remove(data);
        }
    }

    public List<T> getList() {
        return list;
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
