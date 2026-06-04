package com.jambharun.controller;

import com.jambharun.entity.Nidhi;
import com.jambharun.service.NidhiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NidhiControllerTest {

    @Mock
    private NidhiService service;

    @InjectMocks
    private NidhiController controller;

    @Test
    void saveNidhiTest() {

        Nidhi nidhi = new Nidhi();
        nidhi.setId(1L);

        when(service.saveNidhi(nidhi)).thenReturn(nidhi);

        Nidhi result = controller.saveNidhi(nidhi);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(service, times(1)).saveNidhi(nidhi);
    }

    @Test
    void getAllNidhiTest() {

        Nidhi n1 = new Nidhi();
        n1.setId(1L);

        Nidhi n2 = new Nidhi();
        n2.setId(2L);

        List<Nidhi> nidhiList = Arrays.asList(n1, n2);

        when(service.getAllNidhi()).thenReturn(nidhiList);

        List<Nidhi> result = controller.getAllNidhi();

        assertEquals(2, result.size());

        verify(service, times(1)).getAllNidhi();
    }

    @Test
    void updateNidhiTest() {

        Long id = 1L;

        Nidhi nidhi = new Nidhi();
        nidhi.setId(id);

        when(service.updateNidhi(id, nidhi)).thenReturn(nidhi);

        Nidhi result = controller.updateNidhi(id, nidhi);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(service, times(1)).updateNidhi(id, nidhi);
    }

    @Test
    void deleteNidhiTest() {

        Long id = 1L;

        doNothing().when(service).deleteNidhi(id);

        String result = controller.deleteNidhi(id);

        assertEquals("Nidhi deleted successfully", result);

        verify(service, times(1)).deleteNidhi(id);
    }
}