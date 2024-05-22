package com.tjoeun.shop.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.shop.dto.QnaRequestDto;
import com.tjoeun.shop.dto.QnaResponseDto;
import com.tjoeun.shop.service.QnaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QnaController {

	private final QnaService qnaService;
	
	@GetMapping("/qna/list")
	public String getQnaListPage(@RequestParam(required = false, defaultValue = "0") Integer page,
															 @RequestParam(required = false, defaultValue = "5") Integer size,
															 Model model) throws Exception {

		try {
			model.addAttribute("resultMap", qnaService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		return "qna/list";
	}
	
	
	@GetMapping("/qna/write")
	public String getQnaWritePage(QnaRequestDto qnaRequestDto, Model model,
																Principal principal) {
		
    String registerId = principal.getName();
    model.addAttribute("registerId", registerId);
    
		return "qna/write";
	}
	
	
	@PostMapping("/qna/write/action")
	public String qnaWriteAction(@Valid QnaRequestDto qnaRequestDto, @RequestParam("registerId")String registerId,
																	BindingResult result, Model model) throws Exception{
  try {
  		
  		qnaRequestDto.setRegisterId(registerId);
      qnaService.save(qnaRequestDto);
  } catch (Exception e){
      model.addAttribute("errorMessage", "등록 중 오류가 발생함 !!!");
      return "qna/write";
  }  
		return "redirect:/qna/list";
	}
	
	
	@PostMapping("/qna/write/edit")
	public String qnaWriteEdit(@Valid QnaRequestDto qnaRequestDto, @RequestParam("registerId") String registerId,
																	BindingResult result, Model model) throws Exception{
  try {
      qnaService.updateQnaBoard(qnaRequestDto, qnaRequestDto.getId());
  } catch (Exception e){
      model.addAttribute("errorMessage", "등록 중 오류가 발생함 !!!");
      return "qna/write";
  }  
		return "redirect:/qna/list";
	}
	
	@GetMapping("/qna/view")
	public String getQnaViewPage(@RequestParam Long id,
															 Model model) throws Exception{
		
		try {
			if(id != null) {
				qnaService.updateQnaReadCntInc(id);
				model.addAttribute("qnaView", qnaService.findById(id));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "qna/view";
	}
	
	@PostMapping("/qna/view/delete")
	public String qnaViewDeleteAction(Principal principal, @RequestParam Long id, RedirectAttributes redirectAttributes) throws Exception {
	    String currentUserId = principal.getName(); 

	    QnaResponseDto qnaResponseDto = qnaService.findById(id);

	    if (qnaResponseDto.getRegisterId().equals(currentUserId)) {
	        qnaService.deleteById(id);
	        return "redirect:/qna/list";
	    } else {
	    	redirectAttributes.addFlashAttribute("error", "사용자의 게시물만 삭제할 수 있습니다");
	        return "redirect:/qna/list";
	    }
	}

	


	
	
	
	
	
}