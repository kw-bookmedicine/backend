package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ClientKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordItem keywordItem;

    public ClientKeyword() {
    }

    /**
     * 연관관계 편의 메서드
     */
    public void setClientAndKeyword(Client client, KeywordItem keywordItem){
        addClientKeywordForClient(client);
        this.client = client;
        addClientKeywordForKeyword(keywordItem);
        this.keywordItem = keywordItem;
    }

    private void addClientKeywordForClient(Client client){
        if(!client.getClientKeywords().contains(this)){
            client.getClientKeywords().add(this);
        }
    }

    private void addClientKeywordForKeyword(KeywordItem keywordItem){
        if(!keywordItem.getClientKeywords().contains(this)){
            keywordItem.getClientKeywords().add(this);
        }
    }
}
