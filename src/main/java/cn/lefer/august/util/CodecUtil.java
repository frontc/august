package cn.lefer.august.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编解码工具类
 *
 * @author fangchao
 * @since 2018-09-10 14:58
 **/
public final class CodecUtil {
    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码时发生错误", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String deocdeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("解码时发生错误", e);
            throw new RuntimeException(e);
        }
        return target;
    }

}
