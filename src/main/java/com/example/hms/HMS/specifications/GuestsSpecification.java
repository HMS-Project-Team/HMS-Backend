package com.example.hms.HMS.specifications;

import com.example.hms.HMS.entities.Guests;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GuestsSpecification {

    public static Specification<Guests> search(
            String name,
            String email,
            String phone
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                Predicate firstName = cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + name.toLowerCase() + "%"
                );
                Predicate lastName = cb.like(
                        cb.lower(root.get("lastName")),
                        "%" + name.toLowerCase() + "%"
                );
                predicates.add(cb.or(firstName, lastName));
            }

            if (email != null && !email.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }

            if (phone != null && !phone.isBlank()) {
                predicates.add(
                        cb.like(root.get("phoneNumber"), "%" + phone + "%")
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

