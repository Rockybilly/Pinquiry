package com.pinquiry.api.repository;


import com.pinquiry.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    Page<User> findByUsername(String username, Pageable pageable);
    Page<User> countByRole(User.UserRole role, Pageable pageable);

    Page<User> findAll(Pageable pageable);

}
