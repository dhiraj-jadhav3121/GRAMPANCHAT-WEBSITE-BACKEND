package com.example.GRAMPANCHAT_WEBSITE.repository;

import com.example.GRAMPANCHAT_WEBSITE.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}