package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.College;
import com.nscs.examautosys.repository.CollegeRepository;
import com.nscs.examautosys.service.CollegeService;
import com.nscs.examautosys.service.dto.CollegeDTO;
import com.nscs.examautosys.service.mapper.CollegeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link College}.
 */
@Service
@Transactional
public class CollegeServiceImpl implements CollegeService {

    private final Logger log = LoggerFactory.getLogger(CollegeServiceImpl.class);

    private final CollegeRepository collegeRepository;

    private final CollegeMapper collegeMapper;

    public CollegeServiceImpl(CollegeRepository collegeRepository, CollegeMapper collegeMapper) {
        this.collegeRepository = collegeRepository;
        this.collegeMapper = collegeMapper;
    }

    @Override
    public CollegeDTO save(CollegeDTO collegeDTO) {
        log.debug("Request to save College : {}", collegeDTO);
        College college = collegeMapper.toEntity(collegeDTO);
        college = collegeRepository.save(college);
        return collegeMapper.toDto(college);
    }

    @Override
    public CollegeDTO update(CollegeDTO collegeDTO) {
        log.debug("Request to save College : {}", collegeDTO);
        College college = collegeMapper.toEntity(collegeDTO);
        college = collegeRepository.save(college);
        return collegeMapper.toDto(college);
    }

    @Override
    public Optional<CollegeDTO> partialUpdate(CollegeDTO collegeDTO) {
        log.debug("Request to partially update College : {}", collegeDTO);

        return collegeRepository
            .findById(collegeDTO.getId())
            .map(existingCollege -> {
                collegeMapper.partialUpdate(existingCollege, collegeDTO);

                return existingCollege;
            })
            .map(collegeRepository::save)
            .map(collegeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollegeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colleges");
        return collegeRepository.findAll(pageable).map(collegeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollegeDTO> findOne(Long id) {
        log.debug("Request to get College : {}", id);
        return collegeRepository.findById(id).map(collegeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete College : {}", id);
        collegeRepository.deleteById(id);
    }
}
