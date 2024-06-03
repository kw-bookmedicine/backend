package kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneLineHelpfulEmotion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_line_helpful_emotion_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_line_prescription_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OneLinePrescription oneLinePrescription;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @Builder
    public OneLineHelpfulEmotion(OneLinePrescription oneLinePrescription, Client client) {
        this.oneLinePrescription = oneLinePrescription;
        this.client = client;
    }
}
