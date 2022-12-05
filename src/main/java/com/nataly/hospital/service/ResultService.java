package com.nataly.hospital.service;

import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.entity.Result;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.ResultRepository;
import com.nataly.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public List<ResultDto> getAllResultByPatientId(long id) {
        return resultRepository.findAll(id).stream()
                .map(result -> new ResultDto(result.getDate(), result.getResult()))
                .collect(Collectors.toList());
    }

    public void addResult(ResultDto resultDto, long userId) {
        User patient = userRepository.findById(userId).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        Result result = new Result(1L, resultDto.getDate(), resultDto.getResult(), patient);
        resultRepository.save(result);
    }
}
