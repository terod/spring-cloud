package com.geo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geo.bo.Bookmark;

public interface BookmarkRepository extends PagingAndSortingRepository<Bookmark, Long> {

	Bookmark findByUserIdAndId(String userId, Long id);

	List<Bookmark> findByUserId(String userId);
}