package com.project.yeojeong.repository;

import com.project.yeojeong.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    @Query(value = "select * from img_file i where i.post_no = :postNo", nativeQuery = true)
    List<UploadFile> findByPostNo(@Param("postNo") int postNo);
}
