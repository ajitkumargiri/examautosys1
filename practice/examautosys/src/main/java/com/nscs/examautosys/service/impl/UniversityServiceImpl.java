package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.University;
import com.nscs.examautosys.repository.UniversityRepository;
import com.nscs.examautosys.service.UniversityService;
import com.nscs.examautosys.service.dto.UniversityDTO;
import com.nscs.examautosys.service.mapper.UniversityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link University}.
 */
@Service
@Transactional
public class UniversityServiceImpl implements UniversityService {

    private final Logger log = LoggerFactory.getLogger(UniversityServiceImpl.class);

    private final UniversityRepository universityRepository;

    private final UniversityMapper universityMapper;

    public UniversityServiceImpl(UniversityRepository universityRepository, UniversityMapper universityMapper) {
        this.universityRepository = universityRepository;
        this.universityMapper = universityMapper;
    }

    @Override
    public UniversityDTO save(UniversityDTO universityDTO) {
        log.debug("Request to save University : {}", universityDTO);
        University university = universityMapper.toEntity(universityDTO);
        university = universityRepository.save(university);
        return universityMapper.toDto(university);
    }

    @Override
    public UniversityDTO update(UniversityDTO universityDTO) {
        log.debug("Request to save University : {}", universityDTO);
        University university = universityMapper.toEntity(universityDTO);
        university = universityRepository.save(university);
        return universityMapper.toDto(university);
    }

    @Override
    public Optional<UniversityDTO> partialUpdate(UniversityDTO universityDTO) {
        log.debug("Request to partially update University : {}", universityDTO);

        return universityRepository
            .findById(universityDTO.getId())
            .map(existingUniversity -> {
                universityMapper.partialUpdate(existingUniversity, universityDTO);

                return existingUniversity;
            })
            .map(universityRepository::save)
            .map(universityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityDTO> findAll() {
        log.debug("Request to get all Universities");
        return universityRepository.findAll().stream().map(universityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UniversityDTO> findOne(Long id) {
        log.debug("Request to get University : {}", id);
        return universityRepository.findById(id).map(universityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete University : {}", id);
        universityRepository.deleteById(id);
    }
}
