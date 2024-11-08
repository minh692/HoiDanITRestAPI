package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.SkillRepository;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public boolean isNameExist(String name) {
        return this.skillRepository.existsByName(name);
    }

    public Skill fetchSkillById(long id) {
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        if (skillOptional.isPresent()) {
            return skillOptional.get();
        }
        return null;
    }

    public Skill handleUpdateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    // public Skill handleUpdateSkill(Skill skill) {
    // Optional<Skill> skillOptional = this.skillRepository.findById(skill.getId());
    // if (skillOptional.isPresent()) {
    // Skill skill1 = skillOptional.get();
    // skill1.setName(skill.getName());
    // skill1 = this.skillRepository.save(skill1);
    // }
    // return null;
    // }

    public void deleteSkill(long id) {
        // delete job(inside job_skill table)
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        Skill currentSkill = skillOptional.get();
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));
        // delete skill
        this.skillRepository.delete(currentSkill);
    }

    public ResultPaginationDTO handleGetAllSkill(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1); // trang hiện tại
        meta.setPageSize(pageable.getPageSize()); // lấy bao nhiêu phần tử

        meta.setPages(pageSkill.getTotalPages()); // tổng số trang
        meta.setTotal(pageSkill.getTotalElements()); // tổng số phần tử

        rs.setMeta(meta);
        rs.setResult(pageSkill.getContent());

        return rs;
    }
}
