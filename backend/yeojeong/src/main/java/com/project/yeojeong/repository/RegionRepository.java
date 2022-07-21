package com.project.yeojeong.repository;

import com.project.yeojeong.entity.PostRegion;
import com.project.yeojeong.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region getByRegionName(String regionName);
}
