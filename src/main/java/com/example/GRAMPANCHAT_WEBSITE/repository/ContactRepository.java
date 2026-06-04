package com.example.GRAMPANCHAT_WEBSITE.repository;

import com.example.GRAMPANCHAT_WEBSITE.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}