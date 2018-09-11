package cn.lefer.august.helper;

import cn.lefer.august.annotation.Aspect;
import cn.lefer.august.proxy.AspectProxy;
import cn.lefer.august.proxy.Proxy;
import cn.lefer.august.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP助手类
 *
 * @author fangchao
 * @since 2018-09-11 09:45
 **/
public class AOPHelper {
    private static final Logger logger = LoggerFactory.getLogger(AOPHelper.class);

    static {
        try {
            //所有aop类及其对应的目标类
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            //所有目标类及其对应的代理类
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            //将代理类注入到bean中
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            logger.error("AOP代理异常", e);
        }
    }

    /**
     * 获取所有继承了AspectProxy的类及其指定的切入点类(@Aspect)
     *
     * @return aop类及切点集和的映射
     * @throws Exception 注解抛出的异常
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = getTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    private static Set<Class<?>> getTargetClassSet(Aspect aspect) throws Exception {
        Class<? extends Annotation> annotationClass = aspect.value();
        return annotationClass.equals(Aspect.class) ? new HashSet<>() : ClassHelper.getClassSetByAnnotation(annotationClass);
    }

    /**
     * 获取目标类及其代理
     *
     * @param proxyMap aop类及切点集和的映射
     * @return 目标类及代理类的映射
     * @throws Exception Proxy异常
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
