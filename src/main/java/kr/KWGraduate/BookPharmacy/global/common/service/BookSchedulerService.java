package kr.KWGraduate.BookPharmacy.global.common.service;

import kr.KWGraduate.BookPharmacy.domain.book.service.BestSellerBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookSchedulerService {

    private final BestSellerBookService bestSellerBookService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 서울기준, 00시 00분 00초에 실행
    public void setBestSeller() {
        bestSellerBookService.setBestSellerBooks();
    }
}
