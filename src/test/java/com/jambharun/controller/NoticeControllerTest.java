package com.jambharun.controller;

import com.jambharun.entity.Notice;
import com.jambharun.service.NoticeService;
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
class NoticeControllerTest {

    @Mock
    private NoticeService service;

    @InjectMocks
    private NoticeController controller;

    @Test
    void saveNoticeTest() {

        Notice notice = new Notice();
        notice.setId(1L);

        when(service.saveNotice(notice)).thenReturn(notice);

        Notice result = controller.saveNotice(notice);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(service, times(1)).saveNotice(notice);
    }

    @Test
    void getAllNoticesTest() {

        Notice n1 = new Notice();
        n1.setId(1L);

        Notice n2 = new Notice();
        n2.setId(2L);

        List<Notice> notices = Arrays.asList(n1, n2);

        when(service.getAllNotices()).thenReturn(notices);

        List<Notice> result = controller.getAllNotices();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(service, times(1)).getAllNotices();
    }

    @Test
    void deleteNoticeTest() {

        Long id = 1L;

        doNothing().when(service).deleteNotice(id);

        String result = controller.deleteNotice(id);

        assertEquals("Notice deleted successfully", result);

        verify(service, times(1)).deleteNotice(id);
    }
}