package com.example.LibraryManagementSystem.service.Impl;

import com.example.LibraryManagementSystem.model.Patron;
import com.example.LibraryManagementSystem.repo.PatronRepository;
import com.example.LibraryManagementSystem.service.PatronService;
import org.springframework.stereotype.Service;
import com.example.LibraryManagementSystem.exception.PatronNotFoundException;
import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    private final  PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
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
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException(id));
        patronRepository.delete(existingPatron);
    }
}
