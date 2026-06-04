package com.example.service;

import com.example.entity.Nidhi;
import com.example.repository.NidhiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NidhiService {

    @Autowired
    private NidhiRepository repository;

    public Nidhi saveNidhi(Nidhi nidhi) {
        return repository.save(nidhi);
    }

    public List<Nidhi> getAllNidhi() {
        return repository.findAll();
    }

    public Nidhi updateNidhi(Long id, Nidhi nidhi) {
        Nidhi old = repository.findById(id).orElseThrow();

        old.setWorkName(nidhi.getWorkName());
        old.setSchemeName(nidhi.getSchemeName());
        old.setApprovedFund(nidhi.getApprovedFund());
        old.setExpense(nidhi.getExpense());
        old.setStatus(nidhi.getStatus());

        return repository.save(old);
    }

    public void deleteNidhi(Long id) {
        repository.deleteById(id);
    }
}