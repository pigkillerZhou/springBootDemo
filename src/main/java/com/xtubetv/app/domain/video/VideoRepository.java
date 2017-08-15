package com.xtubetv.app.domain.video;

import com.xtubetv.app.domain.persistence.TypesafeJpaRepository;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by hyg on 2017/7/26.
 */

public interface VideoRepository extends CrudRepository<Video, Long>, TypesafeJpaRepository<Video> {
}
