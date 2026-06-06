package com.jambharun.controller;

import com.jambharun.entity.Contact;
import com.jambharun.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService service;

    @PostMapping
    public Contact saveContact(@RequestBody Contact contact) {
        return service.saveContact(contact);
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        service.deleteContact(id);
        return "Contact deleted successfully";
    }
}