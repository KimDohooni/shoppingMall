package com.tjoeun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllByRecieverOrderByRegTimeDesc(String email);
	
	List<Post> findAllBySender(Member member);

	List<Post> findByReciever(String user);

}
