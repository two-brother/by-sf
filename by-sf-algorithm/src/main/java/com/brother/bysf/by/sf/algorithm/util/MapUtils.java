package com.brother.bysf.by.sf.algorithm.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapUtils {
    public static void main(String[] args) {
        Map<String, Set<String>> mapNew = new HashMap<>();
        Map<String, Set<String>> mapOld = new HashMap<>();

        Set<String> seta = new LinkedHashSet<>();
        Set<String> setb = new LinkedHashSet<>();
        Set<String> setc = new LinkedHashSet<>();

        seta.add("1");seta.add("2");seta.add("3");seta.add("8");
        setb.add("1");setb.add("2");setb.add("5");setb.add("7");
        setc.add("1");setc.add("3");setc.add("5");setc.add("7");

        mapNew.put("t1", seta);
        mapNew.put("t2", setb);

        mapOld.put("t2", setc);
        mapOld.put("t3", setc);


        Map<String, Set<String>> mapCommon = mapNew.keySet().stream().filter(k -> mapOld.containsKey(k)).collect(Collectors.toMap(k -> k, k -> mapNew.get(k)));
        System.out.println("common: " + mapCommon);


        Map<String, Set<String>> mapAdd = mapNew.keySet().stream().map(k -> k).collect(Collectors.toMap(k -> k, k -> mapNew.get(k)));
        Map<String, Set<String>> mapUpdate = null;
        Map<String, Set<String>> mapDelete = null;

        System.out.println("add: " + mapAdd);
        System.out.println("update: " + mapUpdate);
        System.out.println("delete: " + mapDelete);

    }
}
