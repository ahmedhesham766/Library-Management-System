package com.example.LibraryManagementSystem.service.Impl;


import com.example.LibraryManagementSystem.exception.PatronHasActiveBorrowingsException;
import com.example.LibraryManagementSystem.model.BorrowingRecord;
import com.example.LibraryManagementSystem.model.Patron;
import com.example.LibraryManagementSystem.repo.PatronRepository;
import com.example.LibraryManagementSystem.service.BorrowingService;
import com.example.LibraryManagementSystem.service.PatronService;
import org.springframework.stereotype.Service;
import com.example.LibraryManagementSystem.exception.PatronNotFoundException;
import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    private final  PatronRepository patronRepository;
    private final BorrowingService borrowService;

    public PatronServiceImpl(PatronRepository patronRepository, BorrowingService borrowService) {
        this.patronRepository = patronRepository;
        this.borrowService = borrowService;
    }

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException(id));
    }

    @Override
    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(Long id, Patron patronDetails) {
        if (borrowService.isPatronBorrowing(id)) {
            BorrowingRecord record = borrowService.getActiveBorrowingRecordByPatronId(id);
            throw new PatronHasActiveBorrowingsException(id, record.getBook().getTitle());
        }

        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() ->  new PatronNotFoundException(id));

        existingPatron.setName(patronDetails.getName());
        existingPatron.setEmail(patronDetails.getEmail());
        existingPatron.setPhoneNumber(patronDetails.getPhoneNumber());
        existingPatron.setAddress(patronDetails.getAddress());
        existingPatron.setMembershipDate(patronDetails.getMembershipDate());
        existingPatron.setContactInformation(patronDetails.getContactInformation());

        return patronRepository.save(existingPatron);
    }

    @Override
    public void deletePatron(Long id) {
        if (borrowService.isPatronBorrowing(id)) {
            BorrowingRecord record = borrowService.getActiveBorrowingRecordByPatronId(id);
            throw new PatronHasActiveBorrowingsException(id, record.getBook().getTitle());
        }

        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException(id));
        patronRepository.delete(existingPatron);
    }
}
