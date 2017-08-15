package com.xtubetv.app.interfaces.controller;

import com.querydsl.core.types.Predicate;
import com.xtubetv.app.domain.persistence.Update;
import com.xtubetv.app.domain.video.QVideo;
import com.xtubetv.app.domain.video.Video;
import com.xtubetv.app.domain.video.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hyg on 2017/7/18.
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    private VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    /**
     * index
     *
     * @param page page number
     * @param size size of a page
     */
    @GetMapping
    public Page<Video> index(Pageable pageable) {
        return videoRepository.findAll(QVideo.video.url.eq("http://a.com"), pageable);
    }

    /**
     * Create video.
     * <p>
     * Create video resource:<br>
     * - blabla<br>
     * - foobar
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Video create(@RequestBody Video video) {
        return videoRepository.save(video);
    }

    /**
     * Read video
     * <p>
     * Read the video by id
     *
     * @param id ID of the video
     */
    @GetMapping("/{id}")
    public Video read(@PathVariable long id) {
        Predicate predicate = QVideo.video.id.eq(id);
        final Video video = videoRepository.findOne(predicate);
        if (video == null) {
            throw new NotFoundException();
        }
        return video;
    }

    /**
     * Create or update video
     *
     * @param id ID of the video
     * @param id ID of the video
     * @param create if not found, should it create a new one
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(
            @PathVariable long id,
            @RequestBody Video video,
            @RequestParam(defaultValue = "true") boolean create) {
        if (!create) {
            final Video one = videoRepository.findOne(id);
            if (one == null)
                throw new NotFoundException();
        }
        video.setId(id);
        videoRepository.save(video);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        videoRepository.delete(id);
    }

    @PostMapping("/{id}/stat")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void increase(@PathVariable long id) {
        final QVideo qVideo = QVideo.video;
        Predicate predicate = qVideo.id.eq(id).and(qVideo.url.endsWith("com"));

        Update update = new Update().add(qVideo.stat, 1).add(qVideo.score, 1.3).set(qVideo.url, "new url");
        videoRepository.update(predicate, update);
    }
}
