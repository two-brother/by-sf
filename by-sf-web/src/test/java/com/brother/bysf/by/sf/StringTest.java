package com.brother.bysf.by.sf;

public class StringTest {
    public static void main(String[] args) {
         String  s1 = "a" + "b";

        String sb1 = new StringBuffer("ab").toString();
        String a = "a";
        String b = "b";
        String c = "ab";
        String d = new String("a");
        String e = new String("a");



        System.out.println(d.intern() == e.intern());
        System.out.println(s1 == c);
        System.out.println(s1 == a + b);
        System.out.println(s1 == sb1.intern());
        System.out.println(s1.equals(sb1));
        new StringTest().say();
    }
    public void say(){
        Class c=this.getClass();
        System.out.println(c);
    }
}
