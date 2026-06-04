package com.jambharun.controller;

import com.jambharun.entity.Contact;
import com.jambharun.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private ContactService service;

    @InjectMocks
    private ContactController controller;

    @Test
    void saveContactTest() {

        Contact contact = new Contact();
        contact.setName("Dhiraj");

        when(service.saveContact(contact)).thenReturn(contact);

        Contact result = controller.saveContact(contact);

        assertNotNull(result);
        assertEquals("Dhiraj", result.getName());

        verify(service, times(1)).saveContact(contact);
    }

    @Test
    void getAllContactsTest() {

        Contact c1 = new Contact();
        c1.setName("Dhiraj");

        Contact c2 = new Contact();
        c2.setName("Amol");

        List<Contact> contacts = Arrays.asList(c1, c2);

        when(service.getAllContacts()).thenReturn(contacts);

        List<Contact> result = controller.getAllContacts();

        assertEquals(2, result.size());
        assertEquals("Dhiraj", result.get(0).getName());
        assertEquals("Amol", result.get(1).getName());

        verify(service, times(1)).getAllContacts();
    }

    @Test
    void deleteContactTest() {

        Long id = 1L;

        doNothing().when(service).deleteContact(id);

        String result = controller.deleteContact(id);

        assertEquals("Contact deleted successfully", result);

        verify(service, times(1)).deleteContact(id);
    }
}