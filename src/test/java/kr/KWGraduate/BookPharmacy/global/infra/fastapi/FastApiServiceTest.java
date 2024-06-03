package kr.KWGraduate.BookPharmacy.global.infra.fastapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FastApiServiceTest {
    @Autowired
    FastApiService fastApiService;

    @Test
    void test() throws IOException {
        System.out.println(fastApiService.RecommendUpdate(FastApiService.RecommendType.Board,167L));
        System.out.println(fastApiService.RecommendUpdate(FastApiService.RecommendType.Book,34039L));
        System.out.println(fastApiService.RecommendUpdate(FastApiService.RecommendType.Client,150L));

    }
}