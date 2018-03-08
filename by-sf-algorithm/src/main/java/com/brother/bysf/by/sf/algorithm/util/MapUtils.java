package com.brother.bysf.by.sf.algorithm.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class MapUtils {
    static Map<String, Set<String>> mapAdd(Map<String, Set<String>> mapNew, Map<String, Set<String>> mapOld){
        Map<String, Set<String>> mapAdd = new HashMap<>();
        mapNew.forEach((k, v) -> mapAdd.put(k, mapOld.containsKey(k) ? v.stream().filter(s -> !mapOld.get(k).contains(s)).collect(Collectors.toSet()) : v));
        return mapAdd.keySet().stream().filter(k -> mapAdd.get(k).size() > 0).collect(Collectors.toMap(k -> k, mapAdd::get));
    }

    static Map<String, Set<String>> mapDelete(Map<String, Set<String>> mapNew, Map<String, Set<String>> mapOld){
        Map<String, Set<String>> mapDelete =  new HashMap<>();
        mapOld.forEach((k, v) -> mapDelete.put(k, mapNew.containsKey(k) ? v.stream().filter(s -> !mapNew.get(k).contains(s)).collect(Collectors.toSet()) : v));
        return mapDelete.keySet().stream().filter(k -> mapDelete.get(k).size() > 0).collect(Collectors.toMap(k -> k, mapDelete::get));
    }

    static Map<String, Set<String>> mapMerge(Map<String, Set<String>> mapNew, Map<String, Set<String>> mapOld){
        Map<String, Set<String>> mapMerge = new HashMap<>();
        mapNew.forEach((k, v) -> mapMerge.merge(k, v, (vo, vn) -> {
            vo.addAll(vn);
            return vo;
        }));
        mapOld.forEach((k, v) -> mapMerge.merge(k, v, (vo, vn) -> {
            vo.addAll(vn);
            return vo;
        }));
        return mapMerge;
    }
}

class Test{
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
        mapOld.put("t3", setb);

        System.out.println("mapNew before: " + mapNew);
        System.out.println("mapOld before: " + mapOld);
        System.out.println("mapAdd: " + MapUtils.mapAdd(mapNew, mapOld));
        System.out.println("mapDelete: " + MapUtils.mapDelete(mapNew, mapOld));
        System.out.println("mapNew after: " + mapNew);
        System.out.println("mapOld after: " + mapOld);

        System.out.println("mapMerge after: " + MapUtils.mapMerge(mapNew, mapOld));
        System.out.println("mapNew after: " + mapNew);
        System.out.println("mapOld after: " + mapOld);
    }
}
