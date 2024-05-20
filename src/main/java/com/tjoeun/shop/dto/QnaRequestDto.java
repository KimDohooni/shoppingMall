package com.tjoeun.shop.dto;

import com.tjoeun.shop.entity.Qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaRequestDto {
	
	private Long id;
	
	private String title;
	
	private String content;
	
	private String registerId;
	
	public Qna toEntity() {
		return Qna.builder()
							.title(title)
							.content(content)
							.registerId(registerId)
							.build();
	}

}
