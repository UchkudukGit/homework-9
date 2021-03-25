package homework.WWWexample;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollectionWWW<E> implements Collection<E> {

    private int size;

    private Object[] elementData = new Object[10];

    @Override
    public boolean add(E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E el = it.next();
            if (o == null && el == null) {
                return true;
            }
            if (o != null && o.equals(el)) {
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
        if (a.length >= size) {
            System.arraycopy(elementData, 0, a, 0, size);
            return a;
        }
        return (T[]) Arrays.copyOf(elementData, size, a.getClass());
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E el = it.next();
            if (o == null && el == null) {
                it.remove();
                return true;
            }
            if (o != null && o.equals(el)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (E object : c) {
            add(object);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E el = it.next();
            if (c.contains(el)) {
                it.remove();
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E el = it.next();
            if (!c.contains(el)) {
                it.remove();
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    public String toString() {
        Iterator<E> it = iterator();
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (it.hasNext()) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
        return sb.append(']').toString();
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;
        boolean deletedCondition = false;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            deletedCondition = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (!deletedCondition) {
                throw new IllegalStateException();
            }
            Object[] newArr = new Object[elementData.length];
            System.arraycopy(elementData, 0, newArr, 0, cursor);
            System.arraycopy(elementData, cursor, newArr, cursor - 1, size - cursor);
            elementData = newArr;
            deletedCondition = false;
            cursor--;
            size--;
        }
    }
}
