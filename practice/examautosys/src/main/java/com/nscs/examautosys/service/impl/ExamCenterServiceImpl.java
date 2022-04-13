package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.ExamCenter;
import com.nscs.examautosys.repository.ExamCenterRepository;
import com.nscs.examautosys.service.ExamCenterService;
import com.nscs.examautosys.service.dto.ExamCenterDTO;
import com.nscs.examautosys.service.mapper.ExamCenterMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExamCenter}.
 */
@Service
@Transactional
public class ExamCenterServiceImpl implements ExamCenterService {

    private final Logger log = LoggerFactory.getLogger(ExamCenterServiceImpl.class);

    private final ExamCenterRepository examCenterRepository;

    private final ExamCenterMapper examCenterMapper;

    public ExamCenterServiceImpl(ExamCenterRepository examCenterRepository, ExamCenterMapper examCenterMapper) {
        this.examCenterRepository = examCenterRepository;
        this.examCenterMapper = examCenterMapper;
    }

    @Override
    public ExamCenterDTO save(ExamCenterDTO examCenterDTO) {
        log.debug("Request to save ExamCenter : {}", examCenterDTO);
        ExamCenter examCenter = examCenterMapper.toEntity(examCenterDTO);
        examCenter = examCenterRepository.save(examCenter);
        return examCenterMapper.toDto(examCenter);
    }

    @Override
    public ExamCenterDTO update(ExamCenterDTO examCenterDTO) {
        log.debug("Request to save ExamCenter : {}", examCenterDTO);
        ExamCenter examCenter = examCenterMapper.toEntity(examCenterDTO);
        examCenter = examCenterRepository.save(examCenter);
        return examCenterMapper.toDto(examCenter);
    }

    @Override
    public Optional<ExamCenterDTO> partialUpdate(ExamCenterDTO examCenterDTO) {
        log.debug("Request to partially update ExamCenter : {}", examCenterDTO);

        return examCenterRepository
            .findById(examCenterDTO.getId())
            .map(existingExamCenter -> {
                examCenterMapper.partialUpdate(existingExamCenter, examCenterDTO);

                return existingExamCenter;
            })
            .map(examCenterRepository::save)
            .map(examCenterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamCenterDTO> findAll() {
        log.debug("Request to get all ExamCenters");
        return examCenterRepository.findAll().stream().map(examCenterMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamCenterDTO> findOne(Long id) {
        log.debug("Request to get ExamCenter : {}", id);
        return examCenterRepository.findById(id).map(examCenterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamCenter : {}", id);
        examCenterRepository.deleteById(id);
    }
}
