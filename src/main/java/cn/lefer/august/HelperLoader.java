package cn.lefer.august;

import cn.lefer.august.helper.*;
import cn.lefer.august.util.ClassUtil;

/**
 * 框架加载器
 *
 * @author fangchao
 * @since 2018-09-10 14:33
 **/
public final class HelperLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                AOPHelper.class,
                IOCHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls:classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
