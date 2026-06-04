package com.example.GRAMPANCHAT_WEBSITE.controller;

import com.example.GRAMPANCHAT_WEBSITE.entity.Nidhi;
import com.example.GRAMPANCHAT_WEBSITE.service.NidhiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nidhi")
@CrossOrigin(origins = "http://localhost:5173")
public class NidhiController {

    @Autowired
    private NidhiService service;

    @PostMapping
    public Nidhi saveNidhi(@RequestBody Nidhi nidhi) {
        return service.saveNidhi(nidhi);
    }

    @GetMapping
    public List<Nidhi> getAllNidhi() {
        return service.getAllNidhi();
    }

    @PutMapping("/{id}")
    public Nidhi updateNidhi(@PathVariable Long id, @RequestBody Nidhi nidhi) {
        return service.updateNidhi(id, nidhi);
    }

    @DeleteMapping("/{id}")
    public String deleteNidhi(@PathVariable Long id) {
        service.deleteNidhi(id);
        return "Nidhi deleted successfully";
    }
}