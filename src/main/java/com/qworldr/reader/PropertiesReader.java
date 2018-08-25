package com.qworldr.reader;

import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.enu.ReaderType;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesReader implements    Reader {
    private final Logger logger = Logger.getLogger(PropertiesReader.class);
    @Override
    public Map<String,String> read(ConfigResource configDefinition) {
        Properties properties= new Properties();
        Map<String,String> map = new HashMap<String, String>();
        try {
            properties.load(configDefinition.getInputStream());
            properties.forEach((a,b)->{
                map.put(String.valueOf(a),String.valueOf(b));
            });
        } catch (IOException e) {
            logger.error(String.format("%s配置文件读取失败",configDefinition.getPath()));
            e.printStackTrace();
        }
        return map;
    }
    @Override
    public ReaderType getReaderType() {
        return ReaderType.PROPERTIES;
    }
}
