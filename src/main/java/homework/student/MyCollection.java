package homework.student;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;
    private boolean isEmpty = false;

    private Object[] elementData = new Object[10];

    @Override
    public boolean add(E e) {
        if (!isEmpty) {
            if (size == elementData.length) {
                elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
            }
            elementData[size++] = e;
            return true;
        }
        elementData = Arrays.copyOf(elementData, 2);
        size = 2;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(Object o) {
        for (Object i : elementData) {
            if (i.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return Arrays.copyOf(a, size);
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < elementData.length; i++) {
            if (elementData[i].equals(o)) {
                System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int cache = 0;
        for (int a = 0; a < c.size(); a++) {
            for (Object i : elementData) {
                if (i.equals(c.toArray()[a])) {
                    cache++;
                }
            }
        }
        return cache == c.toArray().length;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (int i = 0; i < c.size(); i++) {
            add((E) c.toArray()[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (int a = 0; a < c.size(); a++) {
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i].equals(c.toArray()[a])) {
                    System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                    size--;
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        for (int a = 0; a < c.size(); a++) {
            for (int i = 0; i < elementData.length; i++) {
                if (!elementData[i].equals(c.toArray()[a])) {
                    System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                    size--;
                    result = true;
                }
            }
        }
        return result;
    }


    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public void clear() {
        elementData = Arrays.copyOf(elementData, 0);
        size = 0;
        isEmpty = true;
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        private boolean cache = false;

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            cache = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (!cache) {
                throw new IllegalStateException("No such element");
            }
            System.arraycopy(elementData, cursor + 1, elementData, cursor, size - cursor - 1);
            size--;
            cache = false;
        }
    }
}