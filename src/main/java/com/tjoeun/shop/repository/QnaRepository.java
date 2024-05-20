package com.tjoeun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.shop.dto.QnaRequestDto;
import com.tjoeun.shop.entity.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long>{

	static final String UPDATE_QNA_READ_CNT_INC = "UPDATE Qna q "
      + "SET q.readCnt = q.readCnt + 1 "
      + "WHERE q.id = :id";

	@Transactional
	@Modifying
	@Query(value = UPDATE_QNA_READ_CNT_INC)
	public int updateQnaReadCntInc(@Param("id") Long id);
	
	QnaRequestDto findAllById(Long id);
	
}
