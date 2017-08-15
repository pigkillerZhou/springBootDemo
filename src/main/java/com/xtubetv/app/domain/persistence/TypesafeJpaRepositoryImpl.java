package com.xtubetv.app.domain.persistence;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

import javax.persistence.EntityManager;

import lombok.NonNull;

public class TypesafeJpaRepositoryImpl<T, ID extends Serializable>
        extends QueryDslJpaRepository<T, ID>  implements TypesafeJpaRepository<T> {

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;
    private final JPAQueryFactory queryFactory;

    public TypesafeJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
        queryFactory = new JPAQueryFactory(entityManager);

    }

    @Override
    @Transactional
    public void update(Predicate where, Update update) {
        if (! update.isEmpty()) {
            final JPAUpdateClause updateClause = queryFactory.update(path).where(where);
            update.accept(updateClause);
            updateClause.execute();
        }
    }
}

