package com.xtubetv.app.domain.persistence;

import com.querydsl.core.types.Predicate;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TypesafeJpaRepository<T> extends QueryDslPredicateExecutor<T>{
    void update(Predicate where, Update update);
}
