package com.qworldr.reader.config;

import com.qworldr.annotation.Config;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.File;

public class ConfigResource  extends ClassPathResource {
    private Config anno;
    private Class aClass;
    private Boolean reload;
    private String dir;
    private String suffix;
    public static ConfigResource valueOf(Config anno, Class aclass, ConfigFormat configFormat){
        String value = anno.value();
        String path = anno.path();
        String suffix = anno.suffix();
        boolean load = anno.reload();
        //anno没有配置的，用默认的configFormat
        if (StringUtils.isEmpty(path)) {
            path=configFormat.getPath();
        }
        if (StringUtils.isEmpty(suffix)) {
            suffix=configFormat.getSuffix();
        }
        StringBuilder stringBuilder=new StringBuilder(path).append(File.separator);
        //没配置文件名，默认用类名
        String location;
        if (!StringUtils.isEmpty(value)) {
            location = stringBuilder.append(value).append(".").append(suffix).toString();
        } else {
            location =stringBuilder.append(aclass.getSimpleName()).append(".").append(suffix).toString();
        }
        return  new ConfigResource(anno,aclass,suffix,path,location);
    }
    private ConfigResource(Config config, Class aclass,String suffix,String dir,String location) {
        super(location);
        this.aClass = aclass;
        this.anno = config;
        boolean load = anno.reload();
        this.reload =load;
        this.dir=dir;
        this.suffix=suffix;

    }

    public String getDir() {
        return dir;
    }


    public String getSuffix() {
        return suffix;
    }

    public Config getAnno() {
        return anno;
    }

    public Class getaClass() {
        return aClass;
    }


    public Boolean getReload() {
        return reload;
    }
}
