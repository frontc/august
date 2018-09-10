package cn.lefer.august.helper;

import cn.lefer.august.annotation.AutoWired;
import cn.lefer.august.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 依赖注入
 *
 * @author fangchao
 * @since 2018-09-10 13:57
 **/
public final class IOCHelper {
    static {
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(!beanMap.isEmpty()){
            for(Map.Entry<Class<?>,Object> entry:beanMap.entrySet()){
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] beanFileds = beanClass.getDeclaredFields();
                for(Field field:beanFileds){
                    if(field.isAnnotationPresent(AutoWired.class)){
                        Class<?> beanFieldClass = field.getType();
                        Object beanFieldClassInstance = BeanHelper.getBean(beanFieldClass);
                        if(beanFieldClassInstance!=null){
                            ReflectionUtil.setField(beanInstance,field,beanFieldClassInstance);
                        }
                    }
                }
            }
        }
    }
}
