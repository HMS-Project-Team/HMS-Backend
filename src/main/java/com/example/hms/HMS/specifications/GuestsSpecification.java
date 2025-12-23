package com.example.hms.HMS.specifications;

import com.example.hms.HMS.entities.Guests;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GuestsSpecification {

    public static Specification<Guests> search(
            String query,
            String name,
            String email,
            String phone) {
        return (root, querySq, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                String queryLike = "%" + query.toLowerCase() + "%";
                Predicate firstName = cb.like(cb.lower(root.get("firstName")), queryLike);
                Predicate lastName = cb.like(cb.lower(root.get("lastName")), queryLike);
                Predicate emailPred = cb.like(cb.lower(root.get("email")), queryLike);
                Predicate phonePred = cb.like(root.get("phoneNumber"), "%" + query + "%");

                predicates.add(cb.or(firstName, lastName, emailPred, phonePred));
            }

            if (name != null && !name.isBlank()) {
                Predicate firstName = cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + name.toLowerCase() + "%");
                Predicate lastName = cb.like(
                        cb.lower(root.get("lastName")),
                        "%" + name.toLowerCase() + "%");
                predicates.add(cb.or(firstName, lastName));
            }

            if (email != null && !email.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"));
            }

            if (phone != null && !phone.isBlank()) {
                predicates.add(
                        cb.like(root.get("phoneNumber"), "%" + phone + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
