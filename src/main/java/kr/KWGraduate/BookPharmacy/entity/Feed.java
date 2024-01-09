package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    public Feed(String comment, float rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public void setClientBook(ClientBook clientbook) {
        this.clientBook = clientbook;
    }


}
