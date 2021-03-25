package homework;

import homework.student.MyCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.Assert;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Check {

    public static void main(String[] args) {
        List<ResultInfo> list = new ArrayList<>();
        //1 iteratorRemove
        list.add(iteratorRemove());
        list.add(iteratorRemoveNull());
        list.add(negativeIteratorRemoveBeforeNext());
        list.add(negativeIteratorRemoveTwoTimes());
        // 2 contains
        list.add(containsTrue());
        list.add(containsFalse());
        list.add(containsNull());
        // 3 containsAll
        list.add(containsAllTrue());
        list.add(containsAllFalse());
        list.add(containsAllNull());
        // 4 remove
        list.add(removeFirstOfTwo());
        list.add(removeFalse());
        list.add(removeNull());
        // 5 removeAll
        list.add(removeAllSameElements());
        list.add(removeAllSeveral());
        list.add(removeAllFalse());
        // 6 retainAll
        list.add(retainAllSameElements());
        list.add(retainAllSeveral());
        list.add(retainAllFalse());
        // 7 clear
        list.add(clear());
        list.add(clearEmptyCollections());
        // 8 addAll
        list.add(addAll());
        // 9 toArray()
        list.add(toArray());
        //10 toArray(T[] a)
        list.add(toArrayTEmptyArr());
        list.add(toArrayTEqualsSize());
        list.add(toArrayTMoreSize());

        printResultAll(list);
    }

    // 10 toArray(T[] a)

    static ResultInfo toArrayTEmptyArr() {
        return testResult(
                "toArray(T[] a)",
                "Проверить что метод вернул массив элементов коллекции передан массив нулевой размерности",
                getCollection(0, 2, null, 5, 2),
                Arrays.toString(new Integer[]{0, 2, null, 5, 2}),
                "Arrays.toString(c.toArray(new Integer[]{}));",
                (c) -> Arrays.toString(c.toArray(new Integer[]{})));
    }

    static ResultInfo toArrayTEqualsSize() {
        return testResult(
                "toArray(T[] a)",
                "Проверить что метод вернул массив элементов коллекции передан массив размером с коллекцию",
                getCollection(0, 2, null, 5, 2),
                Arrays.toString(new Integer[]{0, 2, null, 5, 2}),
                "Arrays.toString(c.toArray(new Integer[5]));",
                (c) -> Arrays.toString(c.toArray(new Integer[5])));
    }

    static ResultInfo toArrayTMoreSize() {
        return testResult(
                "toArray(T[] a)",
                "Проверить что метод вернул массив элементов коллекции передан массив размером больше чем коллекция",
                getCollection(0, 2, null, 5, 2),
                Arrays.toString(new Integer[]{0, 2, null, 5, 2, 0, 0}),
                "Arrays.toString(c.toArray(new Integer[]{0, 0, 0, 0, 0, 0, 0}));",
                (c) -> Arrays.toString(c.toArray(new Integer[]{0, 0, 0, 0, 0, 0, 0})));
    }

    // 9 toArray()
    static ResultInfo toArray() {
        return testResult(
                "toArray",
                "Проверить что метод вернул массив элементов коллекции",
                getCollection(0, 2, null, 5, 2),
                Arrays.toString(new Object[]{0, 2, null, 5, 2}),
                "Arrays.toString(c.toArray());",
                (c) -> Arrays.toString(c.toArray()));
    }

    // 8 addAll
    static ResultInfo addAll() {
        return testCollection(
                "addAll(Collection<? extends E> c)",
                "Проверить что элементы добавились в коллекцию'",
                getCollection("house", "tree"),
                getCollection("house", "tree", "forest", "dog"),
                "c.addAll(Arrays.asList(\"forest\", \"dog\"));",
                (c) -> c.addAll(Arrays.asList("forest", "dog")));
    }

    // 7 clear
    static ResultInfo clear() {
        return testCollection(
                "clear()",
                "Проверить что метод очистил коллекцию",
                getCollection(0, 2, null, 5, 2),
                getCollection(),
                "c.clear();",
                (c) -> c.clear());
    }

    static ResultInfo clearEmptyCollections() {
        return testCollection(
                "clear()",
                "Проверить что метод очистил уже пустую коллекцию коллекцию",
                getCollection(),
                getCollection(),
                "c.clear();",
                (c) -> c.clear());
    }

    // 6 retainAll
    static ResultInfo retainAllSameElements() {
        return testCollection(
                "retainAll(Collection<?> c)",
                "Проверить что метод оставил переданное значение.",
                getCollection(0, 2, null, 5, 2),
                getCollection(2, 2),
                "c.retainAll(Arrays.asList(2));",
                (c) -> c.retainAll(Arrays.asList(2)));
    }

    static ResultInfo retainAllSeveral() {
        return testCollection(
                "retainAll(Collection<?> c)",
                "Проверить что метод оставил переданные значения.",
                getCollection(0, 2, null, 5, 2),
                getCollection(2, 5, 2),
                "c.retainAll(Arrays.asList(2, 5));",
                (c) -> c.retainAll(Arrays.asList(2, 5)));
    }

    static ResultInfo retainAllFalse() {
        return testCollection(
                "retainAll(Collection<?> c)",
                "Проверить что метод не изменил коллекцию.",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, null, 5, 2),
                "c.retainAll(Arrays.asList(null, 5, 2, 0));",
                (c) -> c.retainAll(Arrays.asList(null, 5, 2, 0)));
    }

    //1 iteratorRemove
    static ResultInfo iteratorRemove() {
        return testCollection(
                "it.remove()",
                "Проверить что итератор корректно удалил элемент",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, null, 2),
                "int deletedNumber = 5;\n" +
                        "        Collection<Integer> c = getCollection(0, 2, null, 5, 2);\n" +
                        "        Iterator<Integer> it = c.iterator();\n" +
                        "        while (it.hasNext()) {\n" +
                        "            Integer el = it.next();\n" +
                        "            if (Integer.valueOf(deletedNumber).equals(el)) {\n" +
                        "                it.remove();\n" +
                        "            }\n" +
                        "        }",
                (c) -> {
                    int deletedNumber = 5;
                    Iterator<Integer> it = c.iterator();
                    while (it.hasNext()) {
                        Integer el = it.next();
                        if (Integer.valueOf(deletedNumber).equals(el)) {
                            it.remove();
                        }
                    }
                });
    }

    static ResultInfo iteratorRemoveNull() {
        return testCollection(
                "it.remove()",
                "Проверить что итератор корректно удалил элемент = null",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, 5, 2),
                "        Iterator<Integer> it = c.iterator();\n" +
                        "        while (it.hasNext()) {\n" +
                        "            Integer el = it.next();\n" +
                        "            if (el == null) {\n" +
                        "                it.remove();\n" +
                        "            }\n" +
                        "        }",
                (c) -> {
                    Iterator<Integer> it = c.iterator();
                    while (it.hasNext()) {
                        Integer el = it.next();
                        if (el == null) {
                            it.remove();
                        }
                    }
                });
    }

    static ResultInfo negativeIteratorRemoveBeforeNext() {
        return testResult(
                "it.remove()",
                "Проверить что итератор вернул исключение если вызвать метод remove() до вызова next()",
                getCollection(0, 2, null, 5, 2),
                "IllegalStateException",
                "                    Iterator<Integer> it = c.iterator();\n" +
                        "                    String exception = null;\n" +
                        "                    try{\n" +
                        "                        it.remove();\n" +
                        "                    } catch (Exception e){\n" +
                        "                        exception = e.getClass().getSimpleName();\n" +
                        "                    }\n" +
                        "                    return exception;",
                (c) -> {
                    Iterator<Integer> it = c.iterator();
                    String exception = null;
                    try {
                        it.remove();
                    } catch (Exception e) {
                        exception = e.getClass().getSimpleName();
                    }
                    return exception;
                });
    }

    static ResultInfo negativeIteratorRemoveTwoTimes() {
        return testResult(
                "it.remove()",
                "Проверить что итератор вернул исключение если вызвать метод remove() два раза подряд",
                getCollection(0, 2, null, 5, 2),
                "IllegalStateException",
                "                    Iterator<Integer> it = c.iterator();\n" +
                        "                    String exception = null;\n" +
                        "                    int count = 0;\n" +
                        "                    \n" +
                        "                    while (it.hasNext()) {\n" +
                        "                        it.next();\n" +
                        "                        if (count == 1) {\n" +
                        "                            it.remove();\n" +
                        "                            try {\n" +
                        "                                it.remove();\n" +
                        "                            } catch (Exception e){\n" +
                        "                                exception = e.getClass().getSimpleName();\n" +
                        "                            }\n" +
                        "                        }\n" +
                        "                        count++;\n" +
                        "                    }\n" +
                        "                    return exception;",
                (c) -> {
                    Iterator<Integer> it = c.iterator();
                    String exception = null;
                    int count = 0;

                    while (it.hasNext()) {
                        it.next();
                        if (count == 1) {
                            it.remove();
                            try {
                                it.remove();
                            } catch (Exception e) {
                                exception = e.getClass().getSimpleName();
                            }
                        }
                        count++;
                    }
                    return exception;
                });
    }

    static ResultInfo removeAllSeveral() {
        return testCollection(
                "removeAll(Collection<?> c)",
                "Проверить что метод удалил все элементы из коллекции. На вход коллекция с несколькими элементами",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, null),
                "c.removeAll(Arrays.asList(2, 5));",
                (c) -> c.removeAll(Arrays.asList(2, 5)));
    }

    static ResultInfo removeAllSameElements() {
        return testCollection(
                "removeAll(Collection<?> c)",
                "Проверить что метод удалил все элементы из коллекции. На вход коллекция с одним элементом",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, null, 5),
                "c.removeAll(Arrays.asList(2))",
                (c) -> c.removeAll(Arrays.asList(2)));
    }

    static ResultInfo removeAllFalse() {
        return testCollection(
                "removeAll(Collection<?> c)",
                "Проверить что метод не удалил элементы из коллекции.",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, null, 5, 2),
                "c.removeAll(Arrays.asList(10));",
                (c) -> c.removeAll(Arrays.asList(10)));
    }


    static ResultInfo removeNull() {
        return testCollection(
                "remove(Object o)",
                "Проверить что метод удалил null",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, 5, 2),
                "c.remove(null);",
                (c) -> c.remove(null));
    }

    static ResultInfo removeFalse() {
        return testCollection(
                "remove(Object o)",
                "Проверить что метод не нашел элемент для удаления",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, 2, null, 5, 2),
                "c.remove(10);",
                (c) -> c.remove(10));
    }

    static ResultInfo removeFirstOfTwo() {
        return testCollection(
                "remove(Object o)",
                "Проверить что метод удалил первый из двух одинаковых элементов",
                getCollection(0, 2, null, 5, 2),
                getCollection(0, null, 5, 2),
                "c.remove(2);",
                (c) -> c.remove(2));
    }

    static ResultInfo containsAllFalse() {
        return testResult(
                "containsAll(Collection<?> c)",
                "Проверить что метод containsAll(Collection<?> c) вернул false",
                getCollection(0, 2, null, 5, 2),
                false,
                "c.containsAll(Arrays.asList(2, 10);",
                (c) -> c.containsAll(Arrays.asList(2, 10)));

    }

    static ResultInfo containsAllNull() {
        return testResult(
                "containsAll(Collection<?> c)",
                "Проверить что метод вернул true при попытке проверить на наличие null",
                getCollection(0, 2, null, 5, 2),
                true,
                "            Collection<Integer> containsCollection = new ArrayList<>();\n" +
                        "            containsCollection.add(null);\n" +
                        "            actual = c.containsAll(containsCollection);",
                (c) -> {
                    Collection<Integer> containsCollection = new ArrayList<>();
                    containsCollection.add(null);
                    return c.containsAll(containsCollection);
                });
    }

    static ResultInfo containsAllTrue() {
        return testResult(
                "containsAll(Collection<?> c)",
                "Проверить что метод вернул true",
                getCollection(0, 2, null, 5, 2),
                true,
                "c.containsAll(Arrays.asList(2, 0));",
                (c) -> c.containsAll(Arrays.asList(2, 0)));
    }

    // 2 remove
    static ResultInfo containsTrue() {
        return testResult(
                "contains(Object o)",
                "Проверить что метод вернул true",
                getCollection(0, 2, null, 5, 2),
                true,
                "c.contains(2);",
                (c) -> c.contains(2));
    }

    static ResultInfo containsFalse() {
        return testResult(
                "contains(Object o)",
                "Проверить что метод вернул false",
                getCollection(0, 2, null, 5, 2),
                false,
                "c.contains(10);",
                (c) -> c.contains(10));
    }


    static ResultInfo containsNull() {
        return testResult(
                "contains(Object o)",
                "Проверить что метод вернул true c переданным значением null",
                getCollection(0, 2, null, 5, 2),
                true,
                "c.contains(null);",
                (c) -> c.contains(null));
    }

    static <T> ResultInfo testResult(String method, String info, Collection<Integer> c, T expected, String action, Function<Collection<Integer>, T> function) {
        ResultInfo resultInfo = new ResultInfo(method, info);
        resultInfo.setBefore(c);

        T actual = null;

        resultInfo.setAction(action);
        try {
            actual = function.apply(c);
        } catch (Exception e) {
            resultInfo.setErrorWhileAction(e.getClass().getName() + " " + e.getMessage());
        }
        if (resultInfo.getErrorWhileAction() != null) {
            return resultInfo;
        }
        resultInfo.setAfter(c);
        resultInfo.check(expected, actual);

        return resultInfo;
    }

    static <E> ResultInfo testCollection(String method, String info, Collection<E> c, Collection<E> expected, String action, Consumer<Collection<E>> consumer) {
        ResultInfo resultInfo = new ResultInfo(method, info);
        resultInfo.setBefore(c);

        resultInfo.setAction(action);
        try {
            consumer.accept(c);
        } catch (Exception e) {
            resultInfo.setErrorWhileAction(e.getClass().getName() + " " + e.getMessage());
        }
        if (resultInfo.getErrorWhileAction() != null) {
            return resultInfo;
        }
        resultInfo.setAfter(c);
        resultInfo.check(expected, c);

        return resultInfo;
    }

    private static void printResultAll(List<ResultInfo> resultInfoList) {
        List<ResultInfo> correctResult = resultInfoList.stream()
                .filter(r -> r.isCorrect)
                .collect(Collectors.toList());

        List<ResultInfo> errorResult = resultInfoList.stream()
                .filter(r -> !r.isCorrect)
                .collect(Collectors.toList());

        System.out.println(String.format("Кол-во тестов: %d корректных: %d некорректных: %d",
                resultInfoList.size(), correctResult.size(), errorResult.size()));

        printSimpleInfo(resultInfoList);
        System.out.println("\n----------INCORRECT TESTS------------\n");
        System.out.println(String.join("\n",
                errorResult.stream()
                        .map(r -> "Ошибка " + r.method + " " + r.info)
                        .collect(Collectors.toList())));
        System.out.println("\n\n");
        printResult(errorResult);
        System.out.println("\n----------CORRECT TESTS------------\n");
        System.out.println(String.join("\n",
                correctResult.stream()
                        .map(r -> "Корректно " + r.method + " " + r.info)
                        .collect(Collectors.toList())));
        System.out.println("\n\n");
        printResult(correctResult);
    }

    private static void printSimpleInfo(List<ResultInfo> resultInfoList) {
        Map<String, List<Boolean>> map = new HashMap<>();
        for (ResultInfo resultInfo : resultInfoList) {
            String method = resultInfo.method;
            List<Boolean> listResults = map.get(method);
            if (listResults == null) {
                listResults = new ArrayList<>();
                listResults.add(resultInfo.isCorrect);
                map.put(method, listResults);
            } else {
                listResults.add(resultInfo.isCorrect);
                map.put(method, listResults);
            }
        }

        String correctTest = splitResult(map, entry -> {
            List<Boolean> results = new ArrayList<>(entry.getValue());
            results.removeAll(Arrays.asList(true));
            return results.isEmpty();
        });

        String semiCorrectTest = splitResult(map, entry -> {
            List<Boolean> results = new ArrayList<>(entry.getValue());
            return results.containsAll(Arrays.asList(true, false));
        });

        String incorrectTest = splitResult(map, entry -> {
            List<Boolean> results = new ArrayList<>(entry.getValue());
            results.removeAll(Arrays.asList(false));
            return results.isEmpty();
        });

        String result = String.format("\nПолностью корректные методы:\n%s" +
                        "\n\nЧастично корректные методы:\n%s\n\nНекорректные методы:\n%s",
                correctTest, semiCorrectTest, incorrectTest);
        System.out.println(result);
    }

    private static String splitResult(Map<String, List<Boolean>> map, Predicate<Map.Entry<String, List<Boolean>>> predicate) {
        Map<String, String> correctMethod = map.entrySet().stream()
                .filter(entry -> predicate.test(entry))
                .collect(Collectors.toMap((entry) -> "метод: " + entry.getKey() + " ", (entry) -> " тесты: " + entry.getValue()));
        return String.join("\n",
                correctMethod.entrySet().stream().map(Object::toString).collect(Collectors.toList()));
    }

    private static void printResult(List<ResultInfo> resultInfoList) {
        System.out.println(
                String.join("\n\n -------------------------------\n\n",
                        resultInfoList.stream().map(Object::toString).collect(Collectors.toList())));
    }

    private static <E> Collection<E> getCollection(E... elements) {
        Collection<E> c = new MyCollection<>();
        for (E e : elements) {
            c.add(e);
        }
        return c;
    }

    private static <E> Collection<E> toArrayList(Collection<E> collection) {
        Collection<E> c = new ArrayList<>();
        Iterator<E> it = collection.iterator();
        while (it.hasNext()){
            c.add(it.next());
        }
        return c;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    private static class ResultInfo {
        String method;
        Boolean isCorrect;
        String info;
        String before;
        String action;
        String expected;
        String actual;
        String after;
        String checkMessage;
        String errorWhileAction = null;

        public ResultInfo(String method, String info) {
            this.method = method;
            this.info = info;
        }

        public void setErrorWhileAction(String errorWhileAction) {
            this.isCorrect = false;
            this.errorWhileAction = errorWhileAction;
        }


        public void setBefore(Object before) {
            this.before = objToStr(before);
        }

        public void setAfter(Object after) {
            this.after = objToStr(after);
        }

        <T> void check(T expected, T actual) {
            String message = "Успешная проверка";
            this.expected = objToStr(expected);
            this.actual = objToStr(actual);
            isCorrect = true;
            try {
                if (expected instanceof Collection<?>) {
                    Collection<?> expC = (Collection<?>) expected;
                    Collection<?> actC = (Collection<?>) actual;
                    ReflectionAssert.assertReflectionEquals(toArrayList(expC), toArrayList(actC));
                } else {
                    Assert.assertEquals(expected, actual);
                }
            } catch (Error error) {
                isCorrect = false;
                message = "Проверка с ошибкой ";
            }
            checkMessage = String.format("%s\nexpected: %s\nactual:   %s", message, this.expected, this.actual);
        }

        private String objToStr(Object o) {
            if (o == null) {
                return null;
            }
            if (o instanceof Collection<?>) {
                StringBuilder sb = new StringBuilder("[");
                Collection<?> c = (Collection<?>) o;
                Iterator<?> it = c.iterator();
                int size = c.size();
                int count = 0;
                while (it.hasNext()){
                    if (count++ != size - 1) {
                        sb.append(it.next()).append(", ");
                    } else {
                        sb.append(it.next());
                    }
                }
                return sb.append("]").toString();
                //return new ArrayList<Object>((Collection<?>) o).toString();
            }
            return o.toString();
        }

        @Override
        public String toString() {
            if (errorWhileAction == null) {
                return String.format("%s %s\nbefore: %s\naction:\n %s\nafter:  %s\n\n%s", method, info, before, action, after, checkMessage);
            }
            return String.format("%s %s\nbefore: %s\naction:\n %s\nОшибка во время выполнения метода:  %s", method, info, before, action, errorWhileAction);
        }
    }
}
