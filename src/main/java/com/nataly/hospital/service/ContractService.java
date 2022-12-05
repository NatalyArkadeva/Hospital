package com.nataly.hospital.service;

import com.nataly.hospital.dto.PatientDto;
import com.nataly.hospital.entity.Contract;
import com.nataly.hospital.entity.PriceList;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.ContractRepository;
import com.nataly.hospital.repository.PriceListRepository;
import com.nataly.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final PriceListRepository priceListRepository;
    private final UserRepository userRepository;

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

    public Contract getContractById(long id) {
        return contractRepository.findById(id).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public Contract getContractByDateAndNumber(String date, int number) {
        String[] arr = date.split("\\.");
        return contractRepository.findByDateAndNumber(LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[1])
                        , Integer.parseInt(arr[0])), number).
                orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public void addContract(PatientDto patientDto, List<String> titles) {
        List<PriceList> services = titles.stream()
                .map(s -> priceListRepository.findByTitle(s).get())
                .collect(Collectors.toList());
        double cost = services.stream()
                .map(PriceList::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
        User patient = userRepository.findByPhoneNumber(patientDto.getPhoneNumber()).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        contractRepository.save(new Contract(services, cost, patient));
    }

    public List<Contract> getContractByPatient(long patientId) {
        return contractRepository.findAllByPatientId(patientId);
    }
}
