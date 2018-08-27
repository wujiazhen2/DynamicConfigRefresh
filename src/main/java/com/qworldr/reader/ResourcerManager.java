package com.qworldr.reader;

import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.convert.DefaultConvert;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ResourcerManager {
    private static final Logger LOGGER = Logger.getLogger(ResourcerManager.class);
    private Map<String, Reader> readerMap = new HashMap<>();
    private static ResourcerManager readerManager = new ResourcerManager();
    private DefaultConvert defaultConvert = new DefaultConvert();
    private Map<String,ConfigResource> configResourceMap;
    private ResourcerManager() {
        //注册Reader
       this.registerReader(new PropertiesReader());
    }
    public static ResourcerManager getInstance() {
        return readerManager;
    }

    public Reader registerReader(Reader reader) {
        return readerMap.put(reader.getReaderType().getSuffix(), reader);
    }

    public Reader getReader(String suffix) {
        return readerMap.get(suffix);
    }


    public <T> T injectProperty(T t, ConfigResource configResource) {
        //保存configResource用于重新加载时使用
        if(configResourceMap==null){
            configResourceMap=new HashMap<>();
        }
        try {
            configResourceMap.put(configResource.getFile().getPath(),configResource);
        } catch (IOException e) {
            LOGGER.error(String.format("%s读取失败",configResource.getPath()));
            return null;
        }
        Reader reader = getReader(configResource.getSuffix());
        Map<String, String> read = reader.read(configResource);
        read.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(t.getClass(), k);
            Optional.ofNullable(field).ifPresent(f ->{
                Class type=field.getType();
                f.setAccessible(true);
                try {
                    f.set(t, type.equals(String.class) ?v:defaultConvert.covert(v, type));
                } catch (IllegalAccessException e) {
                    LOGGER.error(String.format("字段%s转化类型错误，目标类型：%s，值：%s", k, type.toString(), String.valueOf(v)));
                }
            } );
        });
        return t;
    }


    public ConfigResource getConfigResource(String path) {
        return this.configResourceMap.get(path);
    }
}
