package cn.lefer.august.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件加载工具类
 *
 * @author fangchao
 * @since 2018-08-21 15:43
 **/
public final class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties loadProperties(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + ": 文件不存在.");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            logger.error("加载 properties 文件失败", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭 input stream 失败", e);
                }

            }
        }
        return properties;
    }
}
