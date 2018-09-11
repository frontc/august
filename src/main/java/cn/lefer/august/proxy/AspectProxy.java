package cn.lefer.august.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * aop抽象类
 *
 * @author fangchao
 * @since 2018-09-11 09:19
 **/
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();
        try{
            if(intercept(cls,method,methodParams)){
                before(cls,method,methodParams);
                result = proxyChain.doProxyChain();
                after(cls,method,methodParams,result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("AOP代理失败",e);
            error(cls,method,methodParams,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] methodParams) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] methodParams) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] methodParams, Object result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] methodParams, Throwable e){}

    public void end(){}
}
