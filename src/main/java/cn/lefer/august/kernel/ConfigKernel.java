package cn.lefer.august.kernel;

import cn.lefer.august.config.ConfigConstant;
import cn.lefer.august.util.PropertiesUtil;

import java.util.Properties;

/**
 * 配置文件核心类：用于获取框架配置参数
 *
 * @author fangchao
 * @since 2018-08-21 15:59
 **/
public final class ConfigKernel {
    private static final Properties properties = PropertiesUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        return properties.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return properties.getProperty(ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername(){
        return properties.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return properties.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage(){
        return properties.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath(){
        return properties.getProperty(ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }
    public static String getAppAssertPath(){
        return properties.getProperty(ConfigConstant.APP_ASSERT_PATH,"/assert/");
    }
}
