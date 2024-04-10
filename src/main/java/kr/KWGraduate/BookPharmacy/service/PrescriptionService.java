package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionCreateDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionModifyDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionBoardPageDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionMyPageDto;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Prescription;
import kr.KWGraduate.BookPharmacy.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final BoardRepository boardRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;
    public List<PrescriptionBoardPageDto> getPrescriptions(Pageable pageable , Long boardId){
        return prescriptionRepository.findByBoardId(pageable, boardId).stream()
                .map(PrescriptionBoardPageDto::new)
                .collect(Collectors.toList());
    }

    public List<PrescriptionMyPageDto> getPrescriptions(Pageable pageable , AuthenticationAdapter authentication){
        return prescriptionRepository.findByUsername(pageable, authentication.getUsername()).stream()
                .map(PrescriptionMyPageDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createPrescription(PrescriptionCreateDto prescriptionCreateDto, AuthenticationAdapter authentication){
        Client client = clientRepository.findByLoginId(authentication.getUsername()).get();
        Book book = bookRepository.findById(prescriptionCreateDto.getBookId()).get();
        Board board = boardRepository.findById(prescriptionCreateDto.getBoardId()).get();

        Prescription prescription = prescriptionCreateDto.toEntity(client, book, board);

        return prescriptionRepository.save(prescription).getId();
    }
    @Transactional
    public Long modifyPrescription(Long prescriptionId, PrescriptionModifyDto prescriptionModifyDto){
        Prescription prescription = prescriptionRepository.findById(prescriptionId).get();
        Book book = bookRepository.findById(prescriptionModifyDto.getBookId()).get();
        prescription.modifyPrescription(book , prescriptionModifyDto.getTitle(), prescriptionModifyDto.getDescription());
        return prescription.getId();
    }
    @Transactional
    public void deletePrescription(Long prescriptionId){
        prescriptionRepository.deleteById(prescriptionId);
    }

}
