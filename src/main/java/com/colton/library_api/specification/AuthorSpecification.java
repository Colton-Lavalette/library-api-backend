package com.colton.library_api.specification;

import com.colton.library_api.model.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecification {
    public static Specification<Author> hasFirstName(String first) {
        return (root, query, cb) ->
                cb.equal(root.get("name").get("first"), first);
    }

    public static Specification<Author> hasLastName(String last) {
        return (root, query, cb) ->
                cb.equal(root.get("name").get("last"), last);
    }

    public static Specification<Author> hasBirthYear(Integer birthYear) {
        return (root, query, cb) ->
                cb.equal(root.get("birthYear"), birthYear);
    }
}
