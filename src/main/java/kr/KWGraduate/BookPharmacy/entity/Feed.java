package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    public Long id;

    @OneToOne(mappedBy = "feed")
    public ClientBook clientbook;

    public String comment;

    public float rating;

    public Feed(String comment, float rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public void setClientbook(ClientBook clientbook) {
        this.clientbook = clientbook;
    }
}
