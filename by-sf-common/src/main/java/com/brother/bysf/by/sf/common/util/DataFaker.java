package com.brother.bysf.by.sf.common.util;

import java.util.function.Supplier;

/**
 * @author sk-shifanwen
 * @date 2018/9/18
 */
public class DataFaker {
    private static FakerFunction fakerFunction = new FakerFunction();
    public static Object generateDataByFieldNameAndFieldType(String fieldName, String fieldType) throws Exception {
        String keyCustom = fieldType + "_" + fieldName;
        String keyDefault = fieldType;
        Supplier func = fakerFunction.FIELD_NAME_TYPE_FUNCTION.get(keyCustom);
        if (null == func) {
            func = fakerFunction.FIELD_NAME_TYPE_FUNCTION.get(keyDefault);
            if (null == func) {
                throw new Exception("generateDataByFieldNameAndFieldType must not be null!");
            }
        }
        return func.get();
    }
}
