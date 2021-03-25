
import homework.student.MyCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class CollectionTest {

    // 1 iteratorRemove
    @Test
    void iteratorRemove() {
        int deletedNumber = 5;
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Iterator<Integer> it = c.iterator();
        while (it.hasNext()) {
            Integer el = it.next();
            if (Integer.valueOf(deletedNumber).equals(el)) {
                it.remove();
            }
        }
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, null, 2), c);
    }

    @Test
    void iteratorRemoveNull() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Iterator<Integer> it = c.iterator();
        while (it.hasNext()) {
            Integer el = it.next();
            if (el == null) {
                it.remove();
            }
        }
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, 5, 2), c);
    }

    @Test
    void negativeIteratorRemoveBeforeNext() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Iterator<Integer> it = c.iterator();
        Assertions.assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    void negativeIteratorRemoveTwoTimes() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Iterator<Integer> it = c.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            if (count == 1) {
                it.remove();
                Assertions.assertThrows(IllegalStateException.class, it::remove);
            }
            count++;
        }

    }

    // 2 remove
    @Test
    void containsTrue() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.contains(2);
        Assertions.assertTrue(result);
    }

    @Test
    void containsFalse() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.contains(10);
        Assertions.assertFalse(result);
    }

    @Test
    void containsNull() {
        MyCollection<Integer> c = new MyCollection<>();
        c.add(2);
        c.add(null);
        boolean result = c.contains(null);
        Assertions.assertTrue(result);
    }

    // 3 containsAll
    @Test
    void containsAllTrue() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.containsAll(Arrays.asList(2, 0));
        Assertions.assertTrue(result);
    }

    @Test
    void containsAllFalse() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.containsAll(Arrays.asList(2, 10));
        Assertions.assertFalse(result);
    }

    @Test
    void containsAllNull() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Collection<Integer> containsCollection = new ArrayList<>();
        containsCollection.add(null);
        boolean result = c.containsAll(containsCollection);
        Assertions.assertTrue(result);
    }

    // 4 remove
    @Test
    void removeFirstOfTwo() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.remove(2);
        Assertions.assertTrue(result);
        ReflectionAssert.assertReflectionEquals(getCollection(0, null, 5, 2), c);
    }

    @Test
    void removeFalse() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.remove(10);
        Assertions.assertFalse(result);
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, null, 5, 2), c);
    }

    @Test
    void removeNull() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.remove(null);
        Assertions.assertTrue(result);
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, 5, 2), c);
    }

    // 5 removeAll
    @Test
    void removeAllSameElements() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.removeAll(Arrays.asList(2));
        ReflectionAssert.assertReflectionEquals(getCollection(0, null, 5), c);
        Assertions.assertTrue(result);
    }

    @Test
    void removeAllSeveral() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.removeAll(Arrays.asList(2, 5));
        ReflectionAssert.assertReflectionEquals(getCollection(0, null), c);
        Assertions.assertTrue(result);
    }

    @Test
    void removeAllFalse() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.removeAll(Arrays.asList(10));
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, null, 5, 2), c);
        Assertions.assertFalse(result);
    }

    @Test
    void removeAllNull() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Collection<Integer> removedCollection = new ArrayList<>();
        removedCollection.add(null);
        boolean result = c.removeAll(removedCollection);
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, 5, 2), c);
        Assertions.assertTrue(result);
    }

    // 6 retainAll
    @Test
    void retainAllSameElements() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.retainAll(Arrays.asList(2));
        ReflectionAssert.assertReflectionEquals(getCollection(2, 2), c);
        Assertions.assertTrue(result);
    }

    @Test
    void retainAllSeveral() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.retainAll(Arrays.asList(2, 5));
        ReflectionAssert.assertReflectionEquals(getCollection(2, 5, 2), c);
        Assertions.assertTrue(result);
    }

    @Test
    void retainAllFalse() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.retainAll(Arrays.asList(null, 5, 2, 0));
        ReflectionAssert.assertReflectionEquals(getCollection(0, 2, null, 5, 2), c);
        Assertions.assertFalse(result);
    }

    @Test
    void retainAllNull() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Collection<Integer> removedCollection = new ArrayList<>();
        removedCollection.add(null);
        boolean result = c.retainAll(removedCollection);
        ReflectionAssert.assertReflectionEquals(removedCollection, c);
        Assertions.assertTrue(result);
    }

    // 7 clear
    @Test
    void clear() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        c.clear();
        ReflectionAssert.assertReflectionEquals(Collections.emptyList(), c);
    }

    // 7 clear
    @Test
    void clearEmptyCollections() {
        MyCollection<Integer> c = new MyCollection<>();
        c.clear();
        ReflectionAssert.assertReflectionEquals(Collections.emptyList(), c);
    }

    // 8 addAll
    @Test
    void addAll() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        boolean result = c.addAll(Arrays.asList(null, null, 5));
        ReflectionAssert.assertReflectionEquals(Arrays.asList(0, 2, null, 5, 2, null, null, 5), c);
        Assertions.assertTrue(result);
    }

    // 9 toArray()
    @Test
    void toArray() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Object[] actArr = c.toArray();
        Object[] expArr = {0, 2, null, 5, 2};
        ReflectionAssert.assertReflectionEquals(expArr, actArr);
        Assertions.assertEquals("Object[]", actArr.getClass().getSimpleName());
        Assertions.assertEquals(c.size(), actArr.length);
    }

    // 10 toArray(T[] a)
    @Test
    void toArrayTEmptyArr() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Object[] actArr = c.toArray(new Integer[]{});
        Integer[] expArr = {0, 2, null, 5, 2};
        ReflectionAssert.assertReflectionEquals(expArr, actArr);
        Assertions.assertEquals("Integer[]", actArr.getClass().getSimpleName());
        Assertions.assertEquals(c.size(), actArr.length);
    }

    @Test
    void toArrayTEqualsSize() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Integer[] testArr = {0, 0, 0, 0, 0};
        Object[] actArr = c.toArray(testArr);
        Integer[] expArr = {0, 2, null, 5, 2};
        ReflectionAssert.assertReflectionEquals(expArr, actArr);
        Assertions.assertEquals("Integer[]", actArr.getClass().getSimpleName());
        Assertions.assertSame(testArr, actArr, "Не вернули тот же экземпляр что и передавали на вход методу");
    }

    @Test
    void toArrayTMoreSize() {
        MyCollection<Integer> c = getCollection(0, 2, null, 5, 2);
        Integer[] testArr = {0, 0, 0, 0, 0, 0, 0};
        Object[] actArr = c.toArray(testArr);
        Integer[] expArr = {0, 2, null, 5, 2, 0, 0};
        ReflectionAssert.assertReflectionEquals(expArr, actArr);
        Assertions.assertEquals("Integer[]", actArr.getClass().getSimpleName());
        Assertions.assertSame(testArr, actArr, "Не вернули тот же экземпляр что и передавали на вход методу");
    }

    private MyCollection<Integer> getCollection() {
        return getCollection(0, 2, null, 5, 2);
    }

    private <E> MyCollection<E> getCollection(E... elements) {
        MyCollection<E> c = new MyCollection<>();
        for (E e : elements) {
            c.add(e);
        }
        return c;
    }
}
