package com.tjoeun.shop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjoeun.shop.constant.PostStatus;
import com.tjoeun.shop.dto.CartItemDto;
import com.tjoeun.shop.dto.PostDto;
import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.entity.Post;
import com.tjoeun.shop.repository.MemberRepository;
import com.tjoeun.shop.repository.PostRepository;
import com.tjoeun.shop.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
	private final PostService postService;
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;

	@GetMapping("/post")
	public String post(Principal principal, Model model, PostDto post) {
		/*
		 * //List<PostDto> PostList = postService. //PostDto postDto = new PostDto();
		 * String sender = principal.getName(); //post.setSenderEmail(sender); Member
		 * member = new Member(); member.setEmail(sender); List<PostDto> postList =
		 * postService.getPostList(member); model.addAttribute("postList", postList);
		 */
		String sender = principal.getName();
		post.setSenderEmail(sender);

		return "member/post";
	}

	@PostMapping("/post/new")
	public String newPost(@Valid PostDto postDto, BindingResult br, Principal principal, Model model) {
		if (br.hasErrors()) {
			return "member/post";
		}
		try {
			if (principal != null && memberRepository.findByEmail(postDto.getRecieverEmail()) != null) {
				postService.createPost(postDto);
				return "redirect:/postList";
			}
			return "member/sendError";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/post";
		}
	}

	@GetMapping("/postList")
	public String postList(Principal principal, Model model, PostDto post) {
		// Member member = new Member();
		String Email = principal.getName();
		Member member = memberRepository.findByEmail(principal.getName());
		//Long id = member.getId();
		List<PostDto> postList = postService.getPostList(Email);
		List<PostDto> postSendList = postService.getPostSendList(member);

		/* ArrayList indexList = null; */

		log.info(">>>>>>>>>>>>>>>> postList : " + postList);
		if (postList.isEmpty()) {
			postList = null;
		}
		/*
		 * for(int i = 0 ; i < postList.size(); i++) { indexList.add(i); }
		 */
		model.addAttribute("postList", postList);
		// model.addAttribute("indexList", indexList);

		if (postSendList.isEmpty()) {
			postSendList = null;
		}
		/*
		 * for(int i = 0 ; i < postList.size(); i++) { indexList.add(i); }
		 */
		model.addAttribute("postSendList", postSendList);

		return "/member/postList";
	}
	/*@PostMapping("/receivedPosts")
	@ResponseBody
	public ResponseEntity getReceivedPosts(Principal principal) {
	    String email = principal.getName();
	    List<PostDto> receivedPosts = postService.getPostList(email);
	    return new ResponseEntity<>(receivedPosts, HttpStatus.OK);
	}

	@PostMapping("/sentPosts")
	@ResponseBody
	public ResponseEntity getSentPosts(Principal principal) {
	    Member member = memberRepository.findByEmail(principal.getName());
	    List<PostDto> sentPosts = postService.getPostSendList(member);
	    return new ResponseEntity<>(sentPosts, HttpStatus.OK);
	}
	*/
	@GetMapping("/reply")
	public String reply(PostDto postDto, Principal principal, @RequestParam("sender") String sender) {
		postDto.setRecieverEmail(sender);
		postDto.setSenderEmail(principal.getName());
		// postDto.setRegTime(regTime);
		/*
		 * if(principal != null &&
		 * memberRepository.findByEmail(postDto.getRecieverEmail()) != null) {
		 * postService.createPost(postDto); return "/member/postList"; }
		 */
		return "member/reply";
	}

	@GetMapping("/clearPost")
	public String clearAll(Principal principal) {
		/*
		 * //로그인 사용자의 이메일 가져옴 String user = principal.getName(); //로그인 사용자의 이메일로 id를
		 * 가져와서 Long userId = memberRepository.findByEmail(user).getId(); //로그인 사용자의
		 * 쪽지함을 비움 postRepository.deleteById(userId);
		 */
		String user = principal.getName();
		List<Post> userPosts = postRepository.findByReciever(user);
		postRepository.deleteAll(userPosts);
		return "redirect:/postList";
	}

	@GetMapping("/detail")
	public String postDetail(@RequestParam("postId") Long postId, Model model) {
		Post post = postRepository.getById(postId);		
		log.info(">>>>>>>>>>>>>>>> postRepository.getById(postId) : " + post);
		post.setPostStatus(PostStatus.READ);
		postRepository.save(post);
		PostDto postDto = PostDto.toDto(post);
		
		model.addAttribute("post", postDto);
		return "member/postDetail";
	}

	@GetMapping("/deleteOne")
	public String DeleteOne(@RequestParam("postId") Long postId) {
		postRepository.deleteById(postId);
		return "redirect:/postList";
	}

}
