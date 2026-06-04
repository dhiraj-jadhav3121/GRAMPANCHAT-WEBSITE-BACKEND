package com.jambharun.service;

import com.jambharun.entity.Contact;
import com.jambharun.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public Contact saveContact(Contact contact) {
        return repository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public void deleteContact(Long id) {
        repository.deleteById(id);
    }
}