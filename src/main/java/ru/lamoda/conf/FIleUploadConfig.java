package ru.lamoda.conf;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
public class FIleUploadConfig {

    @Autowired
    Environment env;

    @Bean
    public DiskFileItemFactory diskFileItemFactory() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(env.getProperty("temp.file.storage")));
        factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        return factory;
    }
}
