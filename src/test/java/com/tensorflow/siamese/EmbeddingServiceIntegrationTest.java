package com.tensorflow.siamese;

import com.tensorflow.siamese.services.EmbeddingService;
import com.tensorflow.siamese.services.ImageProcessingService;
import com.tensorflow.siamese.services.TfModelServingService;
import com.tensorflow.siamese.services.impl.EmbeddingServiceImpl;
import com.tensorflow.siamese.services.impl.ImageProcessingServiceImpl;
import com.tensorflow.siamese.services.impl.WriterRecognitionModelSerivce;
import ij.ImagePlus;
import ij.io.Opener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class EmbeddingServiceIntegrationTest {

    @Autowired
    private EmbeddingService embeddingService;

    @Before
    public final void setUp() {
        embeddingService.startService();
    }

    @Test
    public void whenZeroImageEmbeddingShouldBeZero() {
        Opener opener = new Opener();
        String imageFilePath = "src/main/resources/Images/test.png";
        ImagePlus img = opener.openImage(imageFilePath);
        List<Double> embeddings = embeddingService.getEmbeddings(img);
        System.out.println(embeddings);
    }

    @After
    public final void after() {
        embeddingService.closeService();
    }

    @TestConfiguration
    static class EmbeddingServiceIntegrationTestContextConfiguration {

        @Bean
        public TfModelServingService tfModelServingService() {
            return new WriterRecognitionModelSerivce();
        }

        @Bean
        public ImageProcessingService imageProcessingService() {
            return new ImageProcessingServiceImpl();
        }

        @Bean
        public EmbeddingService embeddingService() {
            return new EmbeddingServiceImpl();
        }
    }

}


