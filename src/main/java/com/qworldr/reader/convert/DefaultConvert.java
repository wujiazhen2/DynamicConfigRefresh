package com.qworldr.reader.convert;

import com.qworldr.reader.exception.MissEditorException;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.core.convert.ConversionService;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.util.Date;

public class DefaultConvert extends PropertyEditorRegistrySupport {

    public DefaultConvert(){
        registerDefaultEditors();
        registerCustomEditor(Date.class, new DateEditor());
    }

    public <T>  T  covert(String value,Class<T> clazz)  {
        PropertyEditor defaultEditor = this.getDefaultEditor(clazz);
        if(defaultEditor==null){
            defaultEditor=this.findCustomEditor(clazz,null);
        }
        if(defaultEditor==null){
            throw new MissEditorException(String.format("缺少%d类型ProertyeEditor",clazz));
        }
        defaultEditor.setAsText(value);
        return (T) defaultEditor.getValue();
    }
}
