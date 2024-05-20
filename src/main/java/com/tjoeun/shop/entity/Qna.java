package com.tjoeun.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Qna extends BaseEntity{
	
	@Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="qna_idx")
  private Long id;
  
	private String title;
	
	private String registerId;
	
	private String content;
	
	private int readCnt;
	
	@Builder
	public Qna(Long id, String title, String content, int readCnt, String registerId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
}
