package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.AcademicBatch;
import com.nscs.examautosys.repository.AcademicBatchRepository;
import com.nscs.examautosys.service.AcademicBatchService;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import com.nscs.examautosys.service.mapper.AcademicBatchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AcademicBatch}.
 */
@Service
@Transactional
public class AcademicBatchServiceImpl implements AcademicBatchService {

    private final Logger log = LoggerFactory.getLogger(AcademicBatchServiceImpl.class);

    private final AcademicBatchRepository academicBatchRepository;

    private final AcademicBatchMapper academicBatchMapper;

    public AcademicBatchServiceImpl(AcademicBatchRepository academicBatchRepository, AcademicBatchMapper academicBatchMapper) {
        this.academicBatchRepository = academicBatchRepository;
        this.academicBatchMapper = academicBatchMapper;
    }

    @Override
    public AcademicBatchDTO save(AcademicBatchDTO academicBatchDTO) {
        log.debug("Request to save AcademicBatch : {}", academicBatchDTO);
        AcademicBatch academicBatch = academicBatchMapper.toEntity(academicBatchDTO);
        academicBatch = academicBatchRepository.save(academicBatch);
        return academicBatchMapper.toDto(academicBatch);
    }

    @Override
    public AcademicBatchDTO update(AcademicBatchDTO academicBatchDTO) {
        log.debug("Request to save AcademicBatch : {}", academicBatchDTO);
        AcademicBatch academicBatch = academicBatchMapper.toEntity(academicBatchDTO);
        academicBatch = academicBatchRepository.save(academicBatch);
        return academicBatchMapper.toDto(academicBatch);
    }

    @Override
    public Optional<AcademicBatchDTO> partialUpdate(AcademicBatchDTO academicBatchDTO) {
        log.debug("Request to partially update AcademicBatch : {}", academicBatchDTO);

        return academicBatchRepository
            .findById(academicBatchDTO.getId())
            .map(existingAcademicBatch -> {
                academicBatchMapper.partialUpdate(existingAcademicBatch, academicBatchDTO);

                return existingAcademicBatch;
            })
            .map(academicBatchRepository::save)
            .map(academicBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcademicBatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicBatches");
        return academicBatchRepository.findAll(pageable).map(academicBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicBatchDTO> findOne(Long id) {
        log.debug("Request to get AcademicBatch : {}", id);
        return academicBatchRepository.findById(id).map(academicBatchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicBatch : {}", id);
        academicBatchRepository.deleteById(id);
    }
}
