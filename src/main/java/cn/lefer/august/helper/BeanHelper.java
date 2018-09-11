package cn.lefer.august.helper;

import cn.lefer.august.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean容器
 *
 * @author fangchao
 * @since 2018-09-10 13:48
 **/
public final class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        ClassHelper.getBeanClassSet().forEach(cls -> BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls)));
    }

    /**
     * 获取BeanMap
     *
     * @return BeanMap
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * @param cls 类
     * @param <T> 泛型
     * @return Bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (BEAN_MAP.containsKey(cls)) {
            return (T) BEAN_MAP.get(cls);
        } else {
            throw new RuntimeException("无法获取Bean:" + cls);
        }
    }

    public static void setBean(Class<?> cls,Object object){
        BEAN_MAP.put(cls,object);
    }
}
