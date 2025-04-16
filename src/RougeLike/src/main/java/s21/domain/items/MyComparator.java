package s21.domain.items;

import java.util.Comparator;
import java.util.Objects;

public class MyComparator implements Comparator{


//        @Override
//        public int compare(s21.domain.Character p1, s21.domain.Character p2) {
//            int gold1 = p1.getGold();
//            int gold2 = p2.getGold();
//            String s;
//            s.compareTo()
//            return gold2.compareTo(gold1)
//        }

    @Override
    public int compare(Object o1, Object o2) {
        String gold1 = o1.toString();
        String gold2 = o2.toString();
        return gold2.compareTo(gold1);
    }
}
