package com.brother.bysf.by.sf.web.util;

import java.io.File;

/**
 * @author sk-shifanwen
 * @date 2018/1/31
 */
public class JarToolUtil {
    /**
     * 获取jar绝对路径
     * @return jar path
     */
    public static String getJarPath() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取jar目录
     * @return jar dir
     */
    public static String getJarDir() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getParent();
    }

    /**
     * 获取jar包名
     * @return jar name
     */
    public static String getJarName() {
        File file = getFile();
        if (file == null) {
            return null;
        }
        return getFile().getName();
    }

    /**
     * 获取当前Jar文件
     * @return File
     */
    private static File getFile() {
        String path = JarToolUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return new File(path);
    }

    public static void test() {
        System.out.println("getJarPath:== " + getJarPath());
        System.out.println("getJarDir:== " + getJarDir());
        System.out.println("getJarName:== " + getJarName());
    }

    public static void main(String[] args) {
        test();
    }
}
