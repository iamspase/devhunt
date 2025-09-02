package com.iamspase.devhunt.company;

import com.iamspase.devhunt.user.User;
import com.iamspase.devhunt.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company createCompany(Company company, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            throw new EntityNotFoundException("User not found.");
        }

        company.setUserId(user.getId());

        return companyRepository.save(company);
    }
}
