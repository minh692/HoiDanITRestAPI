package vn.hoidanit.jobhunter.domain.response;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.LevelEnum;

@Getter
@Setter
public class ResCreateJobDTO {
    private long id;

    private String name;

    private String location;

    private double salary;

    private int quantity;

    private LevelEnum level;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private Instant startDate;

    private Instant endDate;

    private boolean active;

    private List<String> skills;

    private Instant createdAt;

    private String createdBy;

}
