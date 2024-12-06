package vn.hoidanit.jobhunter.domain.response.resume;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.StatusEnum;

@Getter
@Setter
public class ResCreateResumeDTO {
    private long id;

    private Instant createAt;

    private String createdBy;

}
