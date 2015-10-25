package edu.edwinhollen.doremi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Edwin on 10/24/15
 */
public class Pick {
    private static final Random r = new Random();

    public static <E> E pick(List<E> list){
        return list.get(r.nextInt(list.size()));
    }

    @SafeVarargs
    public static <E> E pick(E... array){
        return array[r.nextInt(array.length)];
    }

    public static <E> List<E> pick(List<E> list, Integer howMany){
        List<E> returnList = new LinkedList<>();
        Collections.shuffle(list, r);
        returnList.addAll(list.subList(0, howMany - 1));
        return returnList;
    }

    public static Integer integer(Integer min, Integer max){
        return r.nextInt((max - min) + 1) + min;
    }

    public static Integer integer(Integer max){
        return integer(0, max);
    }

}
