package com.stupor.zmr.service;

import com.stupor.zmr.Mapper.Mapper;
import com.stupor.zmr.dto.RefPointDto;
import com.stupor.zmr.dto.RefPointResponseDto;
import com.stupor.zmr.entity.RefPoint;
import com.stupor.zmr.kafka.KafkaProducer;
import com.stupor.zmr.repository.RefPointRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefPointService {
    private final RefPointRepository refPointRepository;
    private final KafkaProducer kafkaProducer;
    private final Mapper mapper;
    private final Logger log = LogManager.getLogger(RefPointService.class);

    public RefPoint getReferencePoint() {
        Optional<RefPoint> refPoint = refPointRepository.findFirst();
        log.debug("Реперная точка успешно получена");
        return refPoint.orElse(null);
    }

    public RefPointResponseDto createReferencePoint(RefPointDto refPointDto) {
        refPointRepository.deleteAll(); // Удаляем все существующие точки (может быть только одна)
        RefPoint refPoint = mapper.convertToEntity(refPointDto);
        RefPoint save = refPointRepository.save(refPoint);
        RefPointResponseDto responseDto = mapper.convertToResponse(save);
        log.debug("Реперная точка успешно создана: {}", refPointDto);
        kafkaProducer.sendMessage("refpoint-created", responseDto);
        return responseDto;
    }

    public RefPointResponseDto updateReferencePoint(RefPointDto refPointDto) {
        refPointRepository.deleteAll();
        RefPoint refPoint = mapper.convertToEntity(refPointDto);
        RefPoint save = refPointRepository.save(refPoint);
        RefPointResponseDto responseDto = mapper.convertToResponse(save);
        log.debug("Реперная точка успешно обновлена: {}", refPointDto);
        kafkaProducer.sendMessage("refpoint-updated", responseDto);
        return responseDto;
    }

    public void deleteReferencePoint() {
        refPointRepository.deleteAll();
        log.debug("Реперная точка успешно удалена");
        kafkaProducer.sendMessage("refpoint-deleted", "Реперная точка удалена");
    }
}
