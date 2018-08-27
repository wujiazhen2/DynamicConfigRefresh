package com.qworldr.processor;

import com.qworldr.annotation.Config;
import com.qworldr.file.listener.FileAlterationReload;
import com.qworldr.reader.ResourcerManager;
import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.config.ConfigFormat;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ConfigAwareBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationListener {
    private HashMap<String, List<String>> files= new HashMap<>();
    private boolean beginListener;
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
        ResourcerManager.getInstance().injectProperty(bean,configDefinition);
        //加文件监听
        if(configDefinition.getReload()){
            List<String> strings = files.get(configDefinition.getDir());
            if(strings==null){
                strings=new ArrayList<>();
                files.put(configDefinition.getDir(),strings);
            }
            strings.add(configDefinition.getFilename());
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        /*
        Spring存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。
        这种情况下，就会造成onApplicationEvent方法被执行两次。所以要判断一下。
         */
        if(!beginListener && files!=null &&files.size()>0){
            //默认10秒刷新一次文件
            FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor();
            files.forEach((k,v)->{
                FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(this.getClass().getClassLoader().getResource(k).getPath(),new NameFileFilter(v));
                FileAlterationListener fileAlterationReload =new FileAlterationReload();
                fileAlterationObserver.addListener(fileAlterationReload);
                fileAlterationMonitor.addObserver(fileAlterationObserver);
                try {
                    fileAlterationMonitor.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            files.clear();
            files=null;
            beginListener=true;
        }
    }
}
