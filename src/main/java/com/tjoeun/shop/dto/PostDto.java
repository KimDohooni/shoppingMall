package com.tjoeun.shop.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.validator.constraints.Length;

import com.tjoeun.shop.entity.Post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto{
	private Long postId;
	@NotBlank(message="제목을 입력하세요")
	@Length(max=15, message="쪽지 제목의 길이는 30글자를 초과할 수 없습니다")
	private String postSubject;
	
	@NotBlank(message="쪽지 내용을 적으세요")
	private String postContent;
	
	
	private String senderEmail;
	
	@NotBlank(message="받는 분을 입력하세요")
	private String recieverEmail;
		
	
	private LocalDateTime regTime;
	 
	    public String getRegTime() {
	        DateTimeFormatter regTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        return regTime.format(this.regTime);
	    }
	
	
	public static PostDto toDto(Post post) {
		
		return new PostDto(
				post.getId(),
				post.getPostSubject(),
				post.getPostContent(),
				post.getSender().getEmail(),
				post.getReciever(),
				post.getRegTime()
				);
	}	
}
