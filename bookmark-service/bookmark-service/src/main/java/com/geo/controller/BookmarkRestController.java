package com.geo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geo.bo.Bookmark;
import com.geo.dao.BookmarkRepository;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@RequestMapping(method = RequestMethod.GET)
	Collection<Bookmark> getBookmarks(@PathVariable String userId) {
		return this.bookmarkRepository.findByUserId(userId);
	}

	@RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
	Bookmark getBookmark(@PathVariable String userId,
			@PathVariable Long bookmarkId) {
		return this.bookmarkRepository.findByUserIdAndId(userId, bookmarkId);
	}

	@RequestMapping(method = RequestMethod.POST)
	Bookmark createBookmark(@PathVariable String userId,
			@RequestBody Bookmark bookmark) {

		Bookmark bookmarkInstance = new Bookmark(userId, bookmark.getHref(),
				bookmark.getDescription(), bookmark.getLabel());

		return this.bookmarkRepository.save(bookmarkInstance);
	}

}
