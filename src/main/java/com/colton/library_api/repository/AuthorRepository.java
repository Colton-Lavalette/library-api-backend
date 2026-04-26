package com.colton.library_api.repository;

import com.colton.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>,
        JpaSpecificationExecutor<Author> {
    List<Author> findByNameFirst(String first);
    List<Author> findByNameLast(String last);
    List<Author> findByBirthYear(Integer birthYear);
    List<Author> findByNameFirstAndNameLast(String first, String last);

}