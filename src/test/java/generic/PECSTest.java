package generic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class A {
    String text() {
        return "A";
    }

    String a() {
        return "a";
    }
}

class B extends A {
    @Override
    String text() {
        return "B";
    }

    String b() {
        return "B";
    }
}

class C extends B {
    @Override
    String text() {
        return "C";
    }

    String c() {
        return "C";
    }
}

class Thing<T extends B> {
    private T thing;

    public Thing(T value) {
        this.thing = value;
    }

    public T thing() {
        return thing;
    }
}

class PECSTest {

    @Test
    void 논_제네릭() {
        List a = new ArrayList();
        a.add(new A());
        a.add(new B());
        a.add(new C());
        for (Object o : a) {
            C a1 = (C) o; // cast exception ==  type - unsafety
            System.out.println(a1.a());
        }
    }

    @Test
    void 파라미터_아닌_와일드카드_super() {
        List<? super B> bs = new ArrayList<>();
//        bs.add(new A()); err
        bs.add(new B());
        bs.add(new C());

//        for (B b : bs) { Only Object
//            ...
//        }
        for (Object b : bs) {
            B tmp = (B) b;
            System.out.println(tmp.text());
        }

        assertEquals("B", ((B) bs.get(0)).text());
        assertEquals("C", ((B) bs.get(1)).text());
    }

    @Test
    void 파라미터_아닌_와일드카드_extends() {
        List<? extends B> be = new ArrayList<>();
//        be.add(new A());
//        be.add(new B());
//        be.add(new C());
    }

    @Test
    void 비한정_와일드카드() {
        List<?> something = new ArrayList<>(Arrays.asList(1, 1.1, "123"));
        System.out.println(something);
        assertEquals("123", something.get(2));
    }

    @Test
    void 파라미터_아닌_와일드카드_옵셔널() {
        Optional<? extends B> ob = Optional.of(new B());
        Optional<? super B> obs = Optional.of(new A());
        Optional<? super B> obss = Optional.of("something");
        Optional<? extends B> oc = Optional.of(new C());
        assertEquals("B", ob.get().text());
//        System.out.println(((B) obs.get()).text()); //unsafety type
        assertEquals("something", obss.get());
        assertEquals("C", oc.get().text());
    }

    @Test
    void 래퍼_제네릭_클래스() {
//        Thing<A> thingA = new Thing<>(new A()); Thing<T extends B>
        Thing<B> thingB = new Thing<>(new C());
//        Thing<C> thingC = new Thing<>(new B());
        Thing<?> thingW = new Thing<>(new B());
        Thing<?> thingW2 = new Thing<>(new C());

        assertEquals("C", thingB.thing().text());
    }

    @Test
    void PECS() {
        List<A> as = new ArrayList<>();
        consume(as);
        List<B> bs = new ArrayList<>();
        consume(bs);
        List<C> cs = new ArrayList<>();
        cs.add(new C());
//        consume(cs);
//        produce(as);
        produce(bs);
        produce(cs);
    }

    private void produce(List<? extends B> list) {
        for (B b : list) {
            System.out.println(b.text());
        }
    }

    private void consume(List<? super B> list) {
//        list.add(new A());
        list.add(new B());
        list.add(new C());
    }

    @Test
    void consumeTest() {
        List<A> as = new ArrayList<>();
        as.add(new A());
//        print(as);

        List<B> bs = new ArrayList<>();
        bs.add(new B());
        print(bs);

        List<C> cs = new ArrayList<>();
        cs.add(new C());
        print(cs);
    }

    @Test
    void addTest() {
        List<B> bs = new ArrayList<>();
        bs.add(new B());
        add(bs);

        List<B> b1 = new ArrayList<>();
        //b1.add(new A());
        b1.add(new C());

    }

    @Test
    void addAllTest() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        addDouble(integers);
    }

    void add(List<? extends B> list) {
        List<A> lists = new ArrayList<>();
        lists.add(new A());
        lists.addAll(list);
        printA(lists);
    }

    void print(List<? extends B> list) {
        for (B b : list) {
            System.out.println(b.text());
        }
    }

    void printA(List<? extends A> list) {
        for (A a : list) {
            System.out.println(a.text());
        }
    }

    private void addDouble(List<? extends Integer> list) {
        List<Number> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.addAll(list);
        numbers.add(3.3);
        System.out.println(numbers);
    }
}