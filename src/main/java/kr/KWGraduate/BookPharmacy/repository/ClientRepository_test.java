package kr.KWGraduate.BookPharmacy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {
    @PersistenceContext
    private EntityManager em;

    public String save(Client client){
        em.persist(client);
        return client.getId();
    }
    public void delete(Client client){
        em.remove(client);
    }
    public Client findById(String id){
        return em.find(Client.class,id);
    }
    public Client findByNickname(String nickname){
        return em.createQuery("select m from Client m where m.nickname = :name",Client.class)
                .setParameter("name",nickname)
                .getSingleResult();
    }
    public Client findByEmail(String email){
        return em.createQuery("select m from Client m where m.email = :email",Client.class)
                .setParameter("email",email)
                .getSingleResult();
    }
    public List<Client> findAll(){
        return em.createQuery("select m from Client m",Client.class)
                .getResultList();
    }
    public Long count(){
        return em.createQuery("select count(m) from Client m",Long.class).getSingleResult();
    }
}
