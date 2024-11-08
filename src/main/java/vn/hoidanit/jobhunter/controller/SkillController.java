package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @ApiMessage("Create a skill")
    public ResponseEntity<Skill> createNewSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {
        boolean isNameExist = this.skillService.isNameExist(skill.getName());
        if (isNameExist && skill.getName() != null) {
            throw new IdInvalidException("Skill " + skill.getName() + " đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(skill));
    }

    // @PutMapping
    // @ApiMessage("Update a skill")
    // public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill)
    // throws IdInvalidException {
    // boolean isNameExist = this.skillService.isNameExist(skill.getName());
    // if (isNameExist) {
    // throw new IdInvalidException("Skill " + skill.getName() + " đã tồn tại");
    // }
    // return
    // ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleUpdateSkill(skill));
    // }

    @PutMapping
    @ApiMessage("Update a skill")
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws IdInvalidException {
        // check id
        Skill currentSkill = this.skillService.fetchSkillById(skill.getId());
        if (currentSkill == null) {
            throw new IdInvalidException("Skill id " + skill.getId() + " không tồn tại");
        }
        if (skill.getName() != null && this.skillService.isNameExist(skill.getName())) {
            throw new IdInvalidException("Skill " + skill.getName() + " đã tồn tại");
        }
        currentSkill.setName(skill.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleUpdateSkill(currentSkill));
    }

    @GetMapping()
    @ApiMessage("fetch all skill")
    public ResponseEntity<ResultPaginationDTO> getAllSkill(@Filter Specification<Skill> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleGetAllSkill(spec, pageable));
    }

    // Thực tế không cần xóa skill, vì khi xóa một job, thì hibernate nó đã tự xóa
    // table job_skill vì Job.java chứa table đó
    @DeleteMapping("{id}")
    @ApiMessage("Delete a skill")
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") long id) throws IdInvalidException {
        // check id
        Skill currentSkill = this.skillService.fetchSkillById(id);
        if (currentSkill == null) {
            throw new IdInvalidException("Skill id " + id + " không tồn tại");
        }

        return ResponseEntity.ok().body(null);
    }

}
