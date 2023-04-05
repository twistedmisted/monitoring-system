package ua.kpi.mishchenko.monitoringsystem.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.kpi.mishchenko.monitoringsystem.dto.CommentDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.CommentEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.Mapper;
import ua.kpi.mishchenko.monitoringsystem.repository.SectionRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CommentMapper implements Mapper<CommentEntity, CommentDTO> {

    private final SectionRepository sectionRepository;

    @Override
    public CommentEntity dtoToEntity(CommentDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        CommentEntity entity = new CommentEntity();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setParameterName(dto.getParameterName());
        entity.setSection(sectionRepository.findById(dto.getSectionId()).get());
        return entity;
    }

    @Override
    public CommentDTO entityToDto(CommentEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setParameterName(entity.getParameterName());
        dto.setSectionId(entity.getSection().getId());
        return dto;
    }

    @Override
    public List<CommentEntity> dtosToEntities(List<CommentDTO> dtos) {
        return null;
    }

    @Override
    public List<CommentDTO> entitiesToDtos(List<CommentEntity> entities) {
        return null;
    }
}
