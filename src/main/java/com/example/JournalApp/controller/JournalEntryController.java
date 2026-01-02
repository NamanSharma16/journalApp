package com.example.JournalApp.controller;

import com.example.JournalApp.entities.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

//    private Map<Long, JournalEntry> journalEntries = new HashMap();
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry entry){
//        journalEntries.put(entry.getId(), entry);
//        return true;
//    }
//
//    @GetMapping("id/{id}")
//    public JournalEntry findJournalById(@PathVariable Long id){
//        return journalEntries.get(id);
//    }
//
//    @DeleteMapping("id/{id}")
//    public void deleteJournalByID(@PathVariable Long id){
//        journalEntries.remove(id);
//    }
//
//    @PutMapping("/id/{id}")
//    public JournalEntry editJournalById(@PathVariable Long id, @RequestBody JournalEntry entry){
//        journalEntries.put(id, entry);
//        return entry;
//    }
}
