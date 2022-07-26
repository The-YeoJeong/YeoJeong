package com.project.yeojeong.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;

@Entity
@Table(name = "img_file")
@Data
public class UploadFile extends BaseCreateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "post_no")
    private Post post;

    @Column
    private String fileName;

    @Column
    private String fileSaveName;

    @Column
    private String filePath;

    @Column
    private long fileSize;
}
