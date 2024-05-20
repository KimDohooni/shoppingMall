package com.tjoeun.shop.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.tjoeun.shop.entity.Qna;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QnaResponseDto {

	private Long id;
	
	private  String title;
	
	private String content;
	
	private int readCnt;
	
	private String registerId;
	
	private LocalDateTime regTime;
	
	public QnaResponseDto(Qna entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		this.regTime = entity.getRegTime();
	}

	public String getRegTime() {
		return toStringDateTime(this.regTime);
	}
	
	public static String toStringDateTime(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return Optional.ofNullable(localDateTime)
            .map(formatter::format)
            .orElse("");
}
	
	
}


