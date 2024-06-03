package kr.KWGraduate.BookPharmacy.domain.readexperience.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReadExperienceUpdatedEvent extends ApplicationEvent {
    private final Long clientId;
    public ReadExperienceUpdatedEvent(Object source, Long clientId) {
        super(source);
        this.clientId = clientId;
    }
}
