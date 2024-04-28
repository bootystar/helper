package io.github.bootystar.helper.easyexcel;



import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterLoader;

import io.github.bootystar.helper.easyexcel.converter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author booty
 */
public abstract class EasyExcelConverterHelper {
    private static final Logger log = LoggerFactory.getLogger(EasyExcelConverterHelper.class);
    private static final AtomicBoolean MARK = new AtomicBoolean(false);
    private static final String WRITE_METHOD = "putWriteConverter";
    private static final String ALL_METHOD = "putAllConverter";
    public static void init() {
        if (MARK.get()){
            return;
        }
        MARK.set(true);
        try {
            Class<DefaultConverterLoader> clazz = DefaultConverterLoader.class;
            Method method = clazz.getDeclaredMethod(WRITE_METHOD, Converter.class);
            addConverters(method);
            method = clazz.getDeclaredMethod(ALL_METHOD,Converter.class);
            addConverters(method);
        }catch (Exception e ){
            log.warn("EasyExcel add excel converter failed , export or import may produce error on special field!");
            log.debug("error",e);
        }
    }

    private static void addConverters(Method method) throws IllegalAccessException, InvocationTargetException {
        method.setAccessible(true);
        method.invoke(null, new LocalDateConverter());
        method.invoke(null, new LocalDateTimeConverter());
        method.invoke(null, new LocalTimeConverter());
        method.invoke(null, new TimestampConverter());
        method.invoke(null, new TimeConverter());
        method.invoke(null, new LongConverter());
        method.invoke(null, new DoubleConverter());
        method.invoke(null, new BooleanConverter());
    }


}
