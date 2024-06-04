package kr.KWGraduate.BookPharmacy.domain.prescription.service;

import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.request.PrescriptionCreateDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.request.PrescriptionModifyDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.response.PrescriptionBoardPageDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.response.PrescriptionMyPageDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.prescription.domain.Prescription;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<PrescriptionBoardPageDto> getPrescriptions(Pageable pageable , Long boardId){
        return prescriptionRepository.findByBoardId(pageable, boardId)
                .map(PrescriptionBoardPageDto::new);
    }

    public Page<PrescriptionMyPageDto> getPrescriptions(Pageable pageable , AuthenticationAdapter authentication){
        return prescriptionRepository.findByUsername(pageable, authentication.getUsername())
                .map(PrescriptionMyPageDto::new);
    }

    public PrescriptionBoardPageDto getPrescription(Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId).get();
        PrescriptionBoardPageDto dto = PrescriptionBoardPageDto.builder().prescription(prescription).build();

        return dto;
    }

    @Transactional
    public Long createPrescription(PrescriptionCreateDto prescriptionCreateDto, AuthenticationAdapter authentication){
        Client client = clientRepository.findByLoginId(authentication.getUsername()).get();
        Book book = bookRepository.findOptionalByIsbn(prescriptionCreateDto.getIsbn()).get();
        Board board = boardRepository.findById(prescriptionCreateDto.getBoardId()).get();

        Prescription prescription = prescriptionCreateDto.toEntity(client, book, board);

        return prescriptionRepository.save(prescription).getId();
    }
    @Transactional
    public Long modifyPrescription(Long prescriptionId, PrescriptionModifyDto prescriptionModifyDto){
        Prescription prescription = prescriptionRepository.findById(prescriptionId).get();
        Book book = bookRepository.findOptionalByIsbn(prescriptionModifyDto.getIsbn()).get();
        prescription.modifyPrescription(book , prescriptionModifyDto.getTitle(), prescriptionModifyDto.getDescription());
        return prescription.getId();
    }
    @Transactional
    public void deletePrescription(Long prescriptionId){
        prescriptionRepository.deleteById(prescriptionId);
    }

}
