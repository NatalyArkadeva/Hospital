package com.nataly.hospital.service;

import com.nataly.hospital.entity.Visit;
import com.nataly.hospital.repository.RecordRepository;
import com.nataly.hospital.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final RecordRepository recordRepository;

    public void addVisit(long recordId, String description, String recipe) {
        Visit newVisit = new Visit(1L, description, recipe, recordRepository.findById(recordId).get());
        visitRepository.save(newVisit);
    }
}
