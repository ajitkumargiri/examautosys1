package com.nscs.examautosys.service.impl;

import com.nscs.examautosys.domain.ExamApplicationForm;
import com.nscs.examautosys.repository.ExamApplicationFormRepository;
import com.nscs.examautosys.service.ExamApplicationFormService;
import com.nscs.examautosys.service.dto.ExamApplicationFormDTO;
import com.nscs.examautosys.service.mapper.ExamApplicationFormMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExamApplicationForm}.
 */
@Service
@Transactional
public class ExamApplicationFormServiceImpl implements ExamApplicationFormService {

    private final Logger log = LoggerFactory.getLogger(ExamApplicationFormServiceImpl.class);

    private final ExamApplicationFormRepository examApplicationFormRepository;

    private final ExamApplicationFormMapper examApplicationFormMapper;

    public ExamApplicationFormServiceImpl(
        ExamApplicationFormRepository examApplicationFormRepository,
        ExamApplicationFormMapper examApplicationFormMapper
    ) {
        this.examApplicationFormRepository = examApplicationFormRepository;
        this.examApplicationFormMapper = examApplicationFormMapper;
    }

    @Override
    public ExamApplicationFormDTO save(ExamApplicationFormDTO examApplicationFormDTO) {
        log.debug("Request to save ExamApplicationForm : {}", examApplicationFormDTO);
        ExamApplicationForm examApplicationForm = examApplicationFormMapper.toEntity(examApplicationFormDTO);
        examApplicationForm = examApplicationFormRepository.save(examApplicationForm);
        return examApplicationFormMapper.toDto(examApplicationForm);
    }

    @Override
    public ExamApplicationFormDTO update(ExamApplicationFormDTO examApplicationFormDTO) {
        log.debug("Request to save ExamApplicationForm : {}", examApplicationFormDTO);
        ExamApplicationForm examApplicationForm = examApplicationFormMapper.toEntity(examApplicationFormDTO);
        examApplicationForm = examApplicationFormRepository.save(examApplicationForm);
        return examApplicationFormMapper.toDto(examApplicationForm);
    }

    @Override
    public Optional<ExamApplicationFormDTO> partialUpdate(ExamApplicationFormDTO examApplicationFormDTO) {
        log.debug("Request to partially update ExamApplicationForm : {}", examApplicationFormDTO);

        return examApplicationFormRepository
            .findById(examApplicationFormDTO.getId())
            .map(existingExamApplicationForm -> {
                examApplicationFormMapper.partialUpdate(existingExamApplicationForm, examApplicationFormDTO);

                return existingExamApplicationForm;
            })
            .map(examApplicationFormRepository::save)
            .map(examApplicationFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamApplicationFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamApplicationForms");
        return examApplicationFormRepository.findAll(pageable).map(examApplicationFormMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamApplicationFormDTO> findOne(Long id) {
        log.debug("Request to get ExamApplicationForm : {}", id);
        return examApplicationFormRepository.findById(id).map(examApplicationFormMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamApplicationForm : {}", id);
        examApplicationFormRepository.deleteById(id);
    }
}
