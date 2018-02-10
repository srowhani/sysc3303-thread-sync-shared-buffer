import java.util.*;

public class SharedBuffer<T> extends ArrayList implements List {
    private int capacity;

    public SharedBuffer (int capacity) {
        this.capacity = capacity;
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void clear() {
        synchronized (this) {
            while (!isFull()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.clear();
            this.notifyAll();
        }
    }

    public boolean fill (Collection c) {
        synchronized (this) {
            while (!this.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.notifyAll();
            return this.addAll(c);
        }
    }

    public synchronized boolean isFull () {
        return this.size() >= capacity;
    }

}
