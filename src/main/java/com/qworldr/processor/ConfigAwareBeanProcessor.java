package com.qworldr.processor;

import com.qworldr.annotation.Config;
import com.qworldr.reader.ReaderManager;
import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.config.ConfigFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

@Component
public class ConfigAwareBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    @Autowired
    private ConfigFormat defaultFormat;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        Config annotation = aClass.getAnnotation(Config.class);
        if(annotation==null) {
            return bean;
        }
        ConfigResource configDefinition = ConfigResource.valueOf(annotation,aClass,defaultFormat);
        loadResource(bean,configDefinition);
        return bean;
    }

    private void loadResource(Object bean, ConfigResource configDefinition) {
        ReaderManager.getInstance().injectProperty(bean,configDefinition);
        //加文件监听
        if(configDefinition.getReload()){

        }
    }

}
