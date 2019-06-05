package com.tensorflow.siamese;

import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.impl.EmbeddingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan
@Configuration
@EnableAsync
public class ConfigClass {
    /*@Bean
    public EmbeddingService embeddingService() {
        EmbeddingService embeddingService = new EmbeddingServiceImpl();
        embeddingService.startService();
        return embeddingService;
    }*/
}
