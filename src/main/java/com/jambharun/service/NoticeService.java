package com.jambharun.service;

import com.jambharun.entity.Notice;
import com.jambharun.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository repository;

    public Notice saveNotice(Notice notice) {
        return repository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return repository.findAll();
    }

    public void deleteNotice(Long id) {
        repository.deleteById(id);
    }
}