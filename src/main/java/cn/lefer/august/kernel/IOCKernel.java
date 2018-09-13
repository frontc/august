package cn.lefer.august.kernel;

import cn.lefer.august.annotation.AutoWired;
import cn.lefer.august.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 控制反转核心类，在bean就绪后，注入对象
 *
 * @author fangchao
 * @since 2018-09-10 13:57
 **/
public final class IOCKernel {
    static {
        Map<Class<?>, Object> beanMap = BeanKernel.getBeanMap();
        if (!beanMap.isEmpty()) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] beanFileds = beanClass.getDeclaredFields();
                for (Field field : beanFileds) {
                    if (field.isAnnotationPresent(AutoWired.class)) {
                        Class<?> beanFieldClass = field.getType();
                        Object beanFieldClassInstance = BeanKernel.getBean(beanFieldClass);
                        if (beanFieldClassInstance != null) {
                            ReflectionUtil.setField(beanInstance, field, beanFieldClassInstance);
                        }
                    }
                }
            }
        }
    }
}
