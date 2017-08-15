package com.xtubetv.app.domain.video;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hyg on 2017/7/26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Video {
    /**
     * video id
     */
    @Id
    @GenericGenerator(name="SemiIdentityGenerator", strategy="com.xtubetv.app.domain.persistence.SemiIdentityGenerator")
    @GeneratedValue(generator="SemiIdentityGenerator")
    private Long id;
    private String url;

    /**
     * video description
     */
    @Size(max = 20)
    private String description;
    private Integer stat = 0;
    private Long duration = 0L;
    private Double score = 0.0;
}
