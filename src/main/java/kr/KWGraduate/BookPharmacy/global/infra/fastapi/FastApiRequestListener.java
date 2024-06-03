package kr.KWGraduate.BookPharmacy.global.infra.fastapi;

import kr.KWGraduate.BookPharmacy.domain.board.event.BoardUpdatedEvent;
import kr.KWGraduate.BookPharmacy.domain.readexperience.event.ReadExperienceUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class FastApiRequestListener {
    private final FastApiService fastApiService;

    @EventListener
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleClientUpdatedEvent(ReadExperienceUpdatedEvent event) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return fastApiService.RecommendUpdate(FastApiService.RecommendType.Client, event.getClientId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventListener
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBoardUpdatedEvent(BoardUpdatedEvent event) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return fastApiService.RecommendUpdate(FastApiService.RecommendType.Board, event.getBoardId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
