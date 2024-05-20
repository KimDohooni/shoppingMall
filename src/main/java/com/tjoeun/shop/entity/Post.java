package com.tjoeun.shop.entity;

import com.tjoeun.shop.constant.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Post extends BaseEntity {
	/*public static MemberRepository memberRepository;*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	private String postSubject;

	private String postContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Member sender;
	
	private String reciever;	
	//private LocalDateTime regTime;

	@Enumerated(EnumType.STRING)
	private PostStatus postStatus;
	
	

}
