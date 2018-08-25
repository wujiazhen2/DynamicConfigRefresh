package com.qworldr.reader;

import com.qworldr.config.TestConfig;
import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.convert.DefaultConvert;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReaderManager {
    private static final Logger LOGGER = Logger.getLogger(ReaderManager.class);
    private Map<String, Reader> readerMap = new HashMap<>();
    private static ReaderManager readerManager = new ReaderManager();
    private DefaultConvert defaultConvert = new DefaultConvert();

    private ReaderManager() {
        //注册Reader
       this.registerReader(new PropertiesReader());
    }

    ;

    public static ReaderManager getInstance() {
        return readerManager;
    }

    public Reader registerReader(Reader reader) {
        return readerMap.put(reader.getReaderType().getSuffix(), reader);
    }

    public Reader getReader(String suffix) {
        return readerMap.get(suffix);
    }


    public <T> T injectProperty(T t, ConfigResource configResource) {
        Reader reader = getReader(configResource.getSuffix());
        Map<String, String> read = reader.read(configResource);
        read.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(t.getClass(), k);
            Class type=field.getType();
            Optional.ofNullable(field).ifPresent(f ->{
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


}
