package com.xtubetv.app.domain.video;


import com.xtubetv.app.config.RepositoryConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan("com.xtubetv.app.domain.video")
@ContextConfiguration(classes=RepositoryConfig.class)
public class VideoRepositoryUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(VideoRepositoryUnitTest.class);
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VideoRepository videoRepository;

    @Test
    public void findOne() throws Exception {
        // given
        Video video = new Video(null, "url", "des", 0, 0L, 0.0);
        entityManager.persistAndFlush(video);

        // when
        Video found = videoRepository.findOne(video.getId());

        // then
        assertEquals(found.getId(), video.getId());
    }
}