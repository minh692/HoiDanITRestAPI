package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepositoy;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class CompanyService {
    private final CompanyRepositoy companyRepositoy;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepositoy companyRepositoy, UserRepository userRepository) {
        this.companyRepositoy = companyRepositoy;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepositoy.save(company);
    }

    public Company handleGetOneCompany(long id) {
        Optional<Company> companyOptional = this.companyRepositoy.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        return null;
    }

    public Company hadleUpdateCompany(Company company) {
        Optional<Company> companyOptional = this.companyRepositoy.findById(company.getId());
        if (companyOptional.isPresent()) {
            Company company1 = companyOptional.get();
            company1.setName(company.getName());
            company1.setDescription(company.getDescription());
            company1.setAddress(company.getAddress());
            company1.setLogo(company.getLogo());

            company1 = this.companyRepositoy.save(company1);
        }
        return null;
    }

    public void handleDeleteCompany(long id) {
        Optional<Company> comOptional = this.companyRepositoy.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            // fetch all user belong to this company
            List<User> users = this.userRepository.findByCompany(com);
            this.userRepository.deleteAll(users);
        }
        this.companyRepositoy.deleteById(id);
    }

    public ResultPaginationDTO handleGetAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepositoy.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1); // trang hiện tại
        meta.setPageSize(pageable.getPageSize()); // lấy bao nhiêu phần tử

        meta.setPages(pageCompany.getTotalPages()); // tổng số trang
        meta.setTotal(pageCompany.getTotalElements()); // tổng số phần tử

        rs.setMeta(meta);
        rs.setResult(pageCompany.getContent());

        return rs;
    }

    public Optional<Company> findById(long id) {
        return this.companyRepositoy.findById(id);
    }
}
