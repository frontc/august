package cn.lefer.august.kernel;

import cn.lefer.august.annotation.Controller;
import cn.lefer.august.annotation.Service;
import cn.lefer.august.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类操作核心类：用于扫描包体获取各种类的集合
 *
 * @author fangchao
 * @since 2018-09-10 13:16
 **/
public class ClassKernel {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigKernel.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有类
     *
     * @return Set<Class                               <                               ?>>
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有service注解类
     *
     * @return Set<Class                               <                               ?>>
     */
    public static Set<Class<?>> getServiceClassSet() {
        return CLASS_SET.stream().filter(cls -> cls.isAnnotationPresent(Service.class)).collect(Collectors.toSet());
    }

    /**
     * 获取所有Controller注解类
     *
     * @return Set<Class                               <                               ?>>
     */
    public static Set<Class<?>> getControllerClassSet() {
        return CLASS_SET.stream().filter(cls -> cls.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
    }

    /**
     * 获取所有bean类
     *
     * @return Set<Class                               <                               ?>>
     */
    public static Set<Class<?>> getBeanClassSet() {
        return CLASS_SET.stream().filter(cls -> (cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(Service.class))).collect(Collectors.toSet());
    }

    /**
     * 获取指定类的所有派生类
     *
     * @param superClass 父类
     * @return 子类集和
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        return CLASS_SET.stream().filter(cls -> superClass.isAssignableFrom(cls) && !superClass.equals(cls)).collect(Collectors.toSet());
    }

    /**
     * 获取指定注解的所有类
     *
     * @param annotationClass 注解类
     * @return 使用了注解的类集和
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        return CLASS_SET.stream().filter(cls -> cls.isAnnotationPresent(annotationClass)).collect(Collectors.toSet());
    }

}
