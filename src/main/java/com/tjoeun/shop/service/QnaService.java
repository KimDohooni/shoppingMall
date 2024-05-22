package com.tjoeun.shop.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.shop.dto.QnaRequestDto;
import com.tjoeun.shop.dto.QnaResponseDto;
import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.entity.Qna;
import com.tjoeun.shop.repository.MemberRepository;
import com.tjoeun.shop.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QnaService {

	private final QnaRepository qnaRepository;
	
	@Transactional
	public Long save(QnaRequestDto qnaSaveDto) {
		return qnaRepository.save(qnaSaveDto.toEntity()).getId();
	}
	
	@Transactional(readOnly=true)
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<Qna> list = qnaRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regTime")));
		
		resultMap.put("list", list.stream().map(QnaResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	public QnaResponseDto findById(Long id) {
		return new QnaResponseDto(qnaRepository.findById(id).get());
	}
	
	public int updateQnaReadCntInc(Long id) {
		return qnaRepository.updateQnaReadCntInc(id);
	}
	
	public void deleteById(Long id) {
		qnaRepository.deleteById(id);
	}
	
	public void updateQnaBoard(@Param("qnaRequestDto") QnaRequestDto qnaRequestDto, @Param("id") Long id) {
		qnaRepository.updateQnaBoard(qnaRequestDto, id);
	}
	
}
