package cn.lefer.august;

import cn.lefer.august.object.Data;
import cn.lefer.august.object.Handler;
import cn.lefer.august.object.Param;
import cn.lefer.august.object.View;
import cn.lefer.august.kernel.BeanKernel;
import cn.lefer.august.kernel.ConfigKernel;
import cn.lefer.august.kernel.ControllerKernel;
import cn.lefer.august.util.CodecUtil;
import cn.lefer.august.util.JsonUtil;
import cn.lefer.august.util.ReflectionUtil;
import cn.lefer.august.util.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 *
 * @author fangchao
 * @since 2018-09-10 15:08
 **/
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        KernelLoader.init();
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigKernel.getAppJspPath() + "*");
        //注册处理静态资源的servlet
        ServletRegistration resServlet = servletContext.getServletRegistration("default");
        resServlet.addMapping(ConfigKernel.getAppAssertPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到请求方法和路径
        String requestMetod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //得到对应的处理器
        Handler handler = ControllerKernel.getHandler(requestMetod, requestPath);
        if (handler != null) {
            //得到对应的控制层实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanKernel.getBean(controllerClass);
            //得到请求参数
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.deocdeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.splitByWholeSeparator(body, "&");
                if (!ArrayUtils.isEmpty(params)) {
                    for (String p : params) {
                        String[] array = StringUtils.splitByWholeSeparator(p, "=");
                        if ((!ArrayUtils.isEmpty(array)) & array.length == 2) {
                            paramMap.put(array[0], array[1]);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理返回值
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    }
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigKernel.getAppJspPath() + path).forward(req, resp);
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    printWriter.write(json);
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
    }

}
