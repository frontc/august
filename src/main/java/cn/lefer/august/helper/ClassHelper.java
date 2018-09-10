package cn.lefer.august.helper;

import cn.lefer.august.annotation.Controller;
import cn.lefer.august.annotation.Service;
import cn.lefer.august.util.ClassUtil;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类操作助手类
 *
 * @author fangchao
 * @since 2018-09-10 13:16
 **/
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    static {
        String basePackage=ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有类
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取所有service注解类
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getServiceClassSet(){
        return CLASS_SET.stream().filter(cls->cls.isAnnotationPresent(Service.class)).collect(Collectors.toSet());
    }

    /**
     * 获取所有Controller注解类
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getControllerClassSet(){
        return CLASS_SET.stream().filter(cls->cls.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
    }

    /**
     * 获取所有bean
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getBeanClassSet(){
        return CLASS_SET.stream().filter(cls->(cls.isAnnotationPresent(Controller.class)||cls.isAnnotationPresent(Service.class))).collect(Collectors.toSet());
    }
}
