package ua.kpi.mishchenko.monitoringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.mishchenko.monitoringsystem.dto.CommentDTO;
import ua.kpi.mishchenko.monitoringsystem.entity.CommentEntity;
import ua.kpi.mishchenko.monitoringsystem.mapper.impl.CommentMapper;
import ua.kpi.mishchenko.monitoringsystem.repository.CommentRepository;
import ua.kpi.mishchenko.monitoringsystem.service.CommentService;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public void createComment(CommentDTO comment) {
        Optional<CommentEntity> commentEntity = commentRepository.findBySectionIdAndParameterName(comment.getSectionId(), comment.getParameterName());
        commentEntity.ifPresent(entity -> comment.setId(entity.getId()));
        commentRepository.save(commentMapper.dtoToEntity(comment));
    }

    private boolean existsBySectionIdAndParameterName(Long sectionId, String parameterName) {
        return commentRepository.existsBySectionIdAndParameterName(sectionId, parameterName);
    }

    @Override
    public CommentDTO getCommentByDepartmentIdAndParameterName(Long departmentId, String parameterName) {
        CommentEntity commentEntity = commentRepository.findBySectionIdAndParameterName(departmentId, parameterName).orElse(null);
        if (isNull(commentEntity)) {
            return new CommentDTO();
        }
        return commentMapper.entityToDto(commentEntity);
    }
}
