package ua.kpi.mishchenko.monitoringsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sections")
@Setter
@Getter
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "section", cascade = ALL)
    private List<SectionParameterEntity> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = ALL)
    private List<WorkingDaysEntity> workingDays = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = ALL)
    private List<ActivityEntity> activities = new ArrayList<>();
}
