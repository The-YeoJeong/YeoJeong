package com.project.yeojeong.repository;

import com.project.yeojeong.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    @Query(value = "select * from travel_post p order by p.post_heart_cnt desc LIMIT 3", nativeQuery = true)
    List<Post> findTopList();

    @Query(value = "SELECT pr.post_no FROM travel_post_region pr " +
            "where pr.region_no = ANY(SELECT r.region_no FROM region r where r.region_name IN (:region))" , nativeQuery = true)
    Integer[] findByRegionName(@Param("region") String[] region);


}
