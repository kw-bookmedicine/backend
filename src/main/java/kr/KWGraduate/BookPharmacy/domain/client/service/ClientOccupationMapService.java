package kr.KWGraduate.BookPharmacy.domain.client.service;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client.Occupation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static kr.KWGraduate.BookPharmacy.domain.client.domain.Client.Occupation.*;

@Service
public class ClientOccupationMapService {
    private Map<String, Occupation> occupationMap = new HashMap<>();

    public ClientOccupationMapService(){
        occupationMap.put("학생" , STUDENT);
        occupationMap.put("직장인", OFFICE_WORKER);
        occupationMap.put("자영업자", SOLE_PROPRIETORSHIP);
        occupationMap.put("프리랜서", FREELANCER);
        occupationMap.put("무직", UNEMPLOYED);
    }
    public Occupation getValue(String koreanKey){
        return occupationMap.get(koreanKey);
    }
}
