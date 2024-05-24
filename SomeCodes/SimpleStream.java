/**
 * @file SimpleStream.java
 * @brief 简化版的流API实现，提供了基本的流操作，如filter、map、forEach、reduce和collect。
 * 
 * 这个类模拟了Java 8中引入的Stream API的一些基本功能，允许对集合进行链式操作，以简洁的方式处理数据。
 */

import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

public class SimpleStream<T> {

    private final Collection<T> collection; // 存储流中的元素

    /**
     * 私有构造函数，防止外部直接创建实例。
     * @param collection 要从中创建流的集合。
     */
    private SimpleStream(Collection<T> collection) {
        this.collection = collection;
    }

    /**
     * 静态工厂方法，用于创建一个新的SimpleStream实例。
     * @param <T> 元素类型
     * @param collection 要从中创建流的集合
     * @return 一个新的SimpleStream实例
     */
    public static <T> SimpleStream<T> of(Collection<T> collection) {
        return new SimpleStream<>(collection);
    }

    /**
     * 对流中的元素进行过滤。
     * @param predicate 一个谓词函数，用于测试元素是否应该被包含在结果流中。
     * @return 一个包含所有通过谓词测试的元素的新流。
     */
    public SimpleStream<T> filter(Predicate<T> predicate) {
        Collection<T> filtered = new ArrayList<>();
        for (T t : collection) {
            if (predicate.test(t)) {
                filtered.add(t);
            }
        }
        return new SimpleStream<>(filtered);
    }

    /**
     * 对流中的每个元素应用一个函数，并将结果收集到一个新的流中。
     * @param <U> 结果流的元素类型
     * @param mapper 一个函数，将T类型的元素映射到U类型的结果。
     * @return 一个新的流，包含原始流中每个元素应用映射函数后的结果。
     */
    public <U> SimpleStream<U> map(Function<T, U> mapper) {
        Collection<U> result = new ArrayList<>();
        for (T t : collection) {
            U u = mapper.apply(t);
            result.add(u);
        }
        return new SimpleStream<>(result);
    }

    /**
     * 对流中的每个元素执行一个操作。
     * @param consumer 一个消费者函数，对每个元素执行操作。
     */
    public void forEach(Consumer<T> consumer) {
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    /**
     * 使用提供的身份值和累加器函数对流中的元素进行归约。
     * @param identity 归约操作的身份值。
     * @param accumulator 一个将当前归约结果和当前元素合并产生新结果的函数。
     * @return 归约操作的结果。
     */
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        T result = identity;
        for (T t : collection) {
            result = accumulator.apply(result, t);
        }
        return result;
    }

    /**
     * 收集流中的元素，将它们累加到一个结果容器中。
     * @param <C> 结果容器的类型
     * @param supplier 提供一个新的结果容器的函数。
     * @param accumulator 一个将元素累加到结果容器中的函数。
     * @return 一个包含了所有流元素的结果容器。
     */
    public <C> C collect(Supplier<C> supplier, BiConsumer<C, T> accumulator) {
        C result = supplier.get();
        for (T t : collection) {
            accumulator.accept(result, t);
        }
        return result;
    }

    /**
     * 主函数，演示SimpleStream类的使用。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 示例：使用filter、map和forEach操作
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3);
        SimpleStream.of(list).filter(i -> i % 2 == 0).map(i -> i * i).forEach(System.out::println);

        System.out.println("--------------------");

        // 示例：使用reduce操作
        System.out.println(SimpleStream.of(list).reduce(0, Integer::sum));
        System.out.println(SimpleStream.of(list).reduce(1, (a, b) -> a * b));
        System.out.println(SimpleStream.of(list).reduce(Integer.MAX_VALUE, Integer::min));
        System.out.println(SimpleStream.of(list).reduce(Integer.MIN_VALUE, Integer::max));

        System.out.println("--------------------");

        // 示例：使用collect操作
        System.out.println(Optional.ofNullable(SimpleStream.of(list).collect(ArrayList::new, ArrayList::add)));
        System.out.println(SimpleStream.of(list).collect(() -> new HashSet<>(), (set, i) -> set.add(i)));
        //System.out.println(SimpleStream.of(list).collect(() -> new HashSet<>(), HashSet::add));
        System.out.println(Optional.ofNullable(SimpleStream.of(list).collect(StringBuilder::new, StringBuilder::append)));
        System.out.println(
                SimpleStream.of(list).collect(() -> new StringBuilder(), (sb, i) -> sb.append(i).append(", ")));
        System.out.println(SimpleStream.of(list).collect(() -> new StringJoiner("-"), (sj, j) -> sj.add(j.toString())));
        System.out.println(
                SimpleStream.of(list).map(String::valueOf).collect(() -> new StringJoiner("-"), StringJoiner::add));

        System.out.println("--------------------");

        // 示例：使用collect操作进行更复杂的数据聚合
        HashMap<Integer, Integer> map = SimpleStream.of(list).collect(HashMap::new, (m, t) -> {
            if (m.containsKey(t)) {
                m.put(t, m.get(t) + 1); // 如果键已存在，增加计数
            } else {
                m.put(t, 1); // 否则，初始化计数为1
            }
        });
        System.out.println(map);
    }
}