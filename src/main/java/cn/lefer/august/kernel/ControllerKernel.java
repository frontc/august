package cn.lefer.august.kernel;

import cn.lefer.august.annotation.RequestMapping;
import cn.lefer.august.object.Handler;
import cn.lefer.august.object.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制层核心类：用于从控制层按照规定语义解析出action映射
 *
 * @author fangchao
 * @since 2018-09-10 14:18
 **/
public final class ControllerKernel {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassKernel.getControllerClassSet();
        for (Class<?> cls : classSet) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    Request request = new Request(requestMapping.method().toLowerCase(), requestMapping.path());
                    Handler handler = new Handler(cls, method);
                    ACTION_MAP.put(request, handler);
                }
            }
        }
    }

    /**
     * 获取handler
     *
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     * @return 处理器
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
