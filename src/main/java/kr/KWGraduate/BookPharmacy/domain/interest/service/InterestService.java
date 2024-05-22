package kr.KWGraduate.BookPharmacy.domain.interest.service;

import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import kr.KWGraduate.BookPharmacy.domain.category.repository.CategoryRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.interest.domain.Interest;
import kr.KWGraduate.BookPharmacy.domain.interest.dto.request.InterestRequestDto;
import kr.KWGraduate.BookPharmacy.domain.interest.repository.InterestRepository;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestService {

    private final InterestRepository interestRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public List<Interest> getInterestsByLoginId(String LoginId){
        List<Interest> interestList = interestRepository.findByLoginId(LoginId);

        return interestList;
    }

    @Transactional
    public void updateInterest(List<String> categoryStrList, Client client) {

        List<Categories> categoryList = categoryRepository.findCategoriesByList(categoryStrList);

        List<Interest> interestList = categoryList
                .stream()
                .map(category -> Interest.builder().client(client).category(category).build())
                .collect(Collectors.toList());

        interestRepository.saveAllAndFlush(interestList);
    }
}
