package cn.lefer.august.helper;

import cn.lefer.august.annotation.RequestMapping;
import cn.lefer.august.bean.Handler;
import cn.lefer.august.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * eg:"get:/index"
 *
 * @author fangchao
 * @since 2018-09-10 14:18
 **/
public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
        for (Class<?> cls : classSet) {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    String requestMappingValue = requestMapping.value();
                    if (requestMappingValue.matches("\\w+:/\\w*")) {
                        String[] strs = requestMappingValue.split(":");
                        if (strs.length == 2) {
                            Request request = new Request(strs[0], strs[1]);
                            Handler handler = new Handler(cls, method);
                            ACTION_MAP.put(request, handler);
                        }
                    }
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
