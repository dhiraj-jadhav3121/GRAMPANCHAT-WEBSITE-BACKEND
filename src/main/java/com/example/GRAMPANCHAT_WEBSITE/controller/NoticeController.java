package com.example.GRAMPANCHAT_WEBSITE.controller;

import com.example.GRAMPANCHAT_WEBSITE.entity.Notice;
import com.example.GRAMPANCHAT_WEBSITE.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "http://localhost:5173")
public class NoticeController {

    @Autowired
    private NoticeService service;

    @PostMapping
    public Notice saveNotice(@RequestBody Notice notice) {
        return service.saveNotice(notice);
    }

    @GetMapping
    public List<Notice> getAllNotices() {
        return service.getAllNotices();
    }

    @DeleteMapping("/{id}")
    public String deleteNotice(@PathVariable Long id) {
        service.deleteNotice(id);
        return "Notice deleted successfully";
    }
}