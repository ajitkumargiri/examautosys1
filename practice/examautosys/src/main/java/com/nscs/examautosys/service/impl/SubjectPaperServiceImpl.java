package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.SubjectPaper;
import com.nscs.examautosys.repository.SubjectPaperRepository;
import com.nscs.examautosys.service.SubjectPaperService;
import com.nscs.examautosys.service.dto.SubjectPaperDTO;
import com.nscs.examautosys.service.mapper.SubjectPaperMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubjectPaper}.
 */
@Service
@Transactional
public class SubjectPaperServiceImpl implements SubjectPaperService {

    private final Logger log = LoggerFactory.getLogger(SubjectPaperServiceImpl.class);

    private final SubjectPaperRepository subjectPaperRepository;

    private final SubjectPaperMapper subjectPaperMapper;

    public SubjectPaperServiceImpl(SubjectPaperRepository subjectPaperRepository, SubjectPaperMapper subjectPaperMapper) {
        this.subjectPaperRepository = subjectPaperRepository;
        this.subjectPaperMapper = subjectPaperMapper;
    }

    @Override
    public SubjectPaperDTO save(SubjectPaperDTO subjectPaperDTO) {
        log.debug("Request to save SubjectPaper : {}", subjectPaperDTO);
        SubjectPaper subjectPaper = subjectPaperMapper.toEntity(subjectPaperDTO);
        subjectPaper = subjectPaperRepository.save(subjectPaper);
        return subjectPaperMapper.toDto(subjectPaper);
    }

    @Override
    public SubjectPaperDTO update(SubjectPaperDTO subjectPaperDTO) {
        log.debug("Request to save SubjectPaper : {}", subjectPaperDTO);
        SubjectPaper subjectPaper = subjectPaperMapper.toEntity(subjectPaperDTO);
        subjectPaper = subjectPaperRepository.save(subjectPaper);
        return subjectPaperMapper.toDto(subjectPaper);
    }

    @Override
    public Optional<SubjectPaperDTO> partialUpdate(SubjectPaperDTO subjectPaperDTO) {
        log.debug("Request to partially update SubjectPaper : {}", subjectPaperDTO);

        return subjectPaperRepository
            .findById(subjectPaperDTO.getId())
            .map(existingSubjectPaper -> {
                subjectPaperMapper.partialUpdate(existingSubjectPaper, subjectPaperDTO);

                return existingSubjectPaper;
            })
            .map(subjectPaperRepository::save)
            .map(subjectPaperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubjectPaperDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubjectPapers");
        return subjectPaperRepository.findAll(pageable).map(subjectPaperMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectPaperDTO> findOne(Long id) {
        log.debug("Request to get SubjectPaper : {}", id);
        return subjectPaperRepository.findById(id).map(subjectPaperMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectPaper : {}", id);
        subjectPaperRepository.deleteById(id);
    }
}
