package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"comment", "rating"})
public class Feed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @OneToOne(mappedBy = "feed")
    private ClientBook clientBook;

    private String comment;

    private float rating;

    private LocalDateTime registerDateTime;

    private LocalDateTime modifyDateTime;

    @Builder
    public Feed(String comment, float rating, LocalDateTime registerDateTime, LocalDateTime modifyDateTime) {
        this.comment = comment;
        this.rating = rating;
        this.registerDateTime = registerDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public void setClientBook(ClientBook clientbook) {
        this.clientBook = clientbook;
    }


}
