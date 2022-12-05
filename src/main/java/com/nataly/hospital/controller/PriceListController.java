package com.nataly.hospital.controller;

import com.nataly.hospital.entity.PriceList;
import com.nataly.hospital.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price")
public class PriceListController {
    private final PriceListService priceListService;

    @GetMapping("/all")
    public List<PriceList> getAllPriceList() {
        return priceListService.getPriceList();
    }

    @PostMapping("/admin/add")
    public ResponseEntity addNewPrice(@RequestBody PriceList priceList) {
        priceListService.addPriceList(priceList);
        return ResponseEntity.status(HttpStatus.OK).body("Service " + priceList.getTitle() + " was successfully added");
    }
}
