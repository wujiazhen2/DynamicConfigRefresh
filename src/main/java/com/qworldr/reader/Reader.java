package com.qworldr.reader;

import com.qworldr.reader.config.ConfigResource;
import com.qworldr.reader.enu.ReaderType;

import java.util.Map;

public interface  Reader {
     Map<String,String> read(ConfigResource configDefinition);
     ReaderType getReaderType();
}
