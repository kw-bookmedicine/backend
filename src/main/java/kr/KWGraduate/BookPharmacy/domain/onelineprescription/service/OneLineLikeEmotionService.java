package kr.KWGraduate.BookPharmacy.domain.onelineprescription.service;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLineLikeEmotion;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLineLikeEmotionRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLinePrescriptionRepository;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OneLineLikeEmotionService {

    private final OneLineLikeEmotionRepository oneLineLikeEmotionRepository;
    private final ClientRepository clientRepository;
    private final OneLinePrescriptionRepository oneLinePrescriptionRepository;

    @Transactional
    public void insert(Long oneLinePrescriptionId, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();

        // 예외처리는 추후에 한번에 적용하겠음
        Client client = clientRepository.findByLoginId(loginId).get();
        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();

        // '좋아요'가 이미 존재하면 에러 반환
        if(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLinePrescriptionId).isPresent()){

        }

        // 좋아요 엔티티 생성
        OneLineLikeEmotion oneLineLikeEmotion = OneLineLikeEmotion.builder()
                .client(client)
                .oneLinePrescription(oneLinePrescription)
                .build();

        // 한줄처방 좋아요 개수 + 1
        oneLinePrescription.setLikeCount(oneLinePrescription.getLikeCount() + 1);

        oneLineLikeEmotionRepository.save(oneLineLikeEmotion);
        oneLinePrescriptionRepository.save(oneLinePrescription);
    }

    @Transactional
    public void delete(Long oneLinePrescriptionId, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();

        // 예외처리는 추후에 한번에 적용하겠음
        Client client = clientRepository.findByLoginId(loginId).get();
        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();

        OneLineLikeEmotion oneLineLikeEmotion = oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLinePrescriptionId).get();

        // 한줄처방 좋아요 개수 - 1
        // (한줄처방이 존재하지 않는다면 위 find함수에서 에러를 던질 것이고, 그렇게 되면 count가 감소하지 않으므로, 따로 if(count<=0)과 같은 조건처리를 해줄 필요가 없음)
        oneLinePrescription.setLikeCount(oneLinePrescription.getLikeCount() - 1);

        oneLineLikeEmotionRepository.delete(oneLineLikeEmotion);
        oneLinePrescriptionRepository.save(oneLinePrescription);

    }
}
