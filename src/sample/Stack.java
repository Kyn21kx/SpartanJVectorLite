package sample;

public class Stack<T> {

    private Stack<T> prev;
    private T data;
    private int size = 0;

    public Stack () {
        this.data = null;
        this.prev = null;
    }

    public Stack (T data) {
        this.data = data;
        this.prev = null;
    }

    public void PushBack (T data) {
        if (this.data == null)
            this.data = data;
        else {
            Stack cache = new Stack(this.data);
            cache.prev = this.prev;
            this.data = data;
            this.prev = cache;
        }
        this.size++;
    }

    public int GetSize () {
        return size;
    }

    public T Peek() {
        return this.data;
    }

    public T Pop () {
        if (size <= 0) {
            System.out.println("Empty stack, pop operation will result in null");
            return null;
        }
        T valueToPop = this.data;
        if (this.prev == null) {
            this.data = null;
            this.prev = null;
            size--;
            return valueToPop;
        }
        this.data = this.prev.data;
        this.prev = this.prev.prev;
        size--;
        return valueToPop;
    }

}
