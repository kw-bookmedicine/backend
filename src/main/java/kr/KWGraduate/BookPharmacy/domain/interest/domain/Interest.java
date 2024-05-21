package kr.KWGraduate.BookPharmacy.domain.interest.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
public class Interest {

    @Builder
    public Interest(Client client, Categories category) {
        this.client = client;
        this.category = category;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Categories category;
}
