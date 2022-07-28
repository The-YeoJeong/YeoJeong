package com.project.yeojeong.specification;

import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.PostRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class PostSpecification {

    private static PostRepository postRepository;

    public PostSpecification(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 하트 내림차순 정렬
    public static Specification<Post> orderHeart() {
        return new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("postHeartCnt")));
                return null;
            }
        };
    }

    // 최근 날짜 정렬
    public static Specification<Post> orderDate() {
        return new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdTime")));
                return null;
            }
        };
    }

    // 지역 필터
    public static Specification<Post> findRegionName(String[] regionName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(root.get("postNo").in(postRepository.findByRegionName(regionName)));
    }

    // 후기만 있는 글
    public static Specification<Post> onlyPostContent() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("postContent"), "");
    }

    // 제목 + 내용 검색
    public static Specification<Post> searchTitleAndContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get("postTitle"), "%" + content + "%"),
                criteriaBuilder.like(root.get("postContent"), "%" + content + "%"));
    }

    // 기간 검색
    // 0 - 당일
    // 1 - 1박2일
    // 2 - 2박 3일
    // 3 - 중장기 3박 ~ 13박
    // 4- 장기 14박 이상
    public static Specification<Post> period(Integer period) {
        if (period > -1 && period < 3) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateDiff"), period);
        } else if (period == 3) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateDiff"), 3, 13);
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dateDiff"), 14);
        }
    }

    // 게시글 나만보기, 전체공개 여부
    public static Specification<Post> fullDisclosure() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("postOnlyme"), false);
    }

}
