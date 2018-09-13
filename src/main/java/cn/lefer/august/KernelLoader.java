package cn.lefer.august;

import cn.lefer.august.kernel.*;
import cn.lefer.august.util.ClassUtil;

/**
 * 核心加载器
 *
 * @author fangchao
 * @since 2018-09-10 14:33
 **/
public final class KernelLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassKernel.class,
                BeanKernel.class,
                AOPKernel.class,
                IOCKernel.class,
                ControllerKernel.class
        };
        for(Class<?> cls:classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
