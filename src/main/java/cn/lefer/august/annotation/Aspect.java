package cn.lefer.august.annotation;

import java.lang.annotation.*;

/**
 * 用于标识AOP的注解
 *
 * @author fangchao
 * @since 2018-09-10 13:05
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();//注解名
}
