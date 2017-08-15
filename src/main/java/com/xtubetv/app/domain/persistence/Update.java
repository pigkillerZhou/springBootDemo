package com.xtubetv.app.domain.persistence;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAUpdateClause;

import java.util.ArrayList;
import java.util.List;

public class Update {
    private List<Path<?>> path;
    private List<Expression> expressions;

    public Update() {
        this.path = new ArrayList<>();
        this.expressions = new ArrayList<>();
    }

    public <N> Update set(Path<N> field, Expression<N> expression) {
        this.path.add(field);
        this.expressions.add(expression);
        return this;
    }

    public <N> Update set(Path<N> field, N value) {
        return set(field, ConstantImpl.create(value));
    }

    public <N extends Number & Comparable<?>> Update add(NumberPath<N> field, Expression<N> expression) {
        this.path.add(field);
        this.expressions.add(field.add(expression));
        return this;
    }

    public <N extends Number & Comparable<?>> Update add(NumberPath<N> field, N value) {
        return add(field, ConstantImpl.create(value));
    }

    @SuppressWarnings("unchecked")
    void accept(JPAUpdateClause updateClause) {
        for (int i = 0; i < this.path.size(); i++) {
            updateClause.set(this.path.get(i), this.expressions.get(i));
        }
    }

    boolean isEmpty() {
        return this.path.size() == 0;
    }
}
