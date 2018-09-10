package cn.lefer.august.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author fangchao
 * @since 2018-09-10 13:31
 **/
public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 通过反射获取类的实例
     *
     * @param cls 待实例化的类
     * @return 实例化后的对象
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("反射获取实例失败", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 通过反射调用实例的方法
     *
     * @param object 目标类
     * @param method 目标方法
     * @param args   方法入参
     * @return 方法出参
     */
    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("反射调用方法失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 通过反射设置对象成员变量值
     *
     * @param object 目标类
     * @param field  目标成员
     * @param value  目标值
     */
    public static void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("反射设置成员失败", e);
            throw new RuntimeException(e);
        }
    }
}
