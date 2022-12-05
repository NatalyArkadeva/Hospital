package com.nataly.hospital.service;

import com.nataly.hospital.entity.PriceList;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListService {
    private final PriceListRepository priceListRepository;

    public List<PriceList> getPriceList() {
        return priceListRepository.findAll();
    }

    public PriceList getByTitle(String title) {
        return priceListRepository.findByTitle(title)
                .orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public void addPriceList(PriceList priceList) {
        if (priceListRepository.findByTitle(priceList.getTitle()).isPresent()) {
            throw new HospitalException(Message.ALREADY_EXISTS);
        } else {
            priceListRepository.save(priceList);
        }
    }
}
