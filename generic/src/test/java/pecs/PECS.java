package pecs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PECS {
    @Test
    void 제네릭_없는_컬렉션() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add("3");

        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
//            sum  += list.get(i);
        }
    }

    @Test
    void 제네릭_있는_컬렉션() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
//        list.add("3");

        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
    }

    @Test
    void 와일드카드_extends_제네릭() {
        List<? extends Number> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
    }

    @Test
    void 와일드카드_super_제네릭() {
        List<? super Number> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1.1);
        list.add(1L);
    }
}
