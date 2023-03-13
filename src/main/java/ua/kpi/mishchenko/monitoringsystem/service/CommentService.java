package ua.kpi.mishchenko.monitoringsystem.service;

import ua.kpi.mishchenko.monitoringsystem.dto.CommentDTO;

public interface CommentService {

    void createComment(CommentDTO comment);

    CommentDTO getCommentByDepartmentIdAndParameterName(Long departmentId, String parameterName);
}
