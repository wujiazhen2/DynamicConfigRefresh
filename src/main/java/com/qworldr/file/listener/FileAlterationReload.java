package com.qworldr.file.listener;

import com.qworldr.reader.ResourcerManager;
import com.qworldr.reader.config.ConfigResource;
import com.qworldr.utils.ApplicationContextUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.context.ApplicationContext;

import java.io.File;

public class FileAlterationReload extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        ResourcerManager instance = ResourcerManager.getInstance();
        ConfigResource configResource = instance.getConfigResource(file.getPath());
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        Object bean = applicationContext.getBean(configResource.getaClass());
        instance.injectProperty(bean,configResource);
    }
}
