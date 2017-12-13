package com.brother.bysf.by.sf.algorithm.by;

/**
 * 已知一个数组，知道其中仅有一个数出现一次，其余每个数都出现了三次，设计一个算法，快速找出这个仅出现一次的数
 *
 * 要求：
 * 1. 空间复杂度尽量低，时间复杂度尽量不超过O(n*log(n))
 * 2. 如果这个数组非常庞大，单台机器的内存已经放不下了，怎么解决？
 * @author by
 */
public class Demo {

    public static void main(String[] args) {
        String str = "aaabbbccdddeeefffp";
        int len = str.length();
        int[] str2 = new int[len];
        for (int i = 0; i < len; i++) {
            if (str2[i] == 0) {
                int count = 0;
                for (int j = i + 1; j < len; j++) {
                    if (str2[j] == 0) {
                        if (str.charAt(j) == str.charAt(i)) {
                            str2[j] = 1;
                            count++;
                        }
                    }
                }
                if (count == 0) {
                    System.out.println(str.charAt(i));
                    break;
                }
            }
        }
    }
}