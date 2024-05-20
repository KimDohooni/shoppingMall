package com.tjoeun.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tjoeun.shop.dto.PostDto;
import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.entity.Post;
import com.tjoeun.shop.repository.MemberRepository;
import com.tjoeun.shop.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	
	public PostDto createPost(PostDto postDto) {

		
				//Member reciever = memberRepository.findByEmail(postDto.getRecieverEmail());
				Member sender = memberRepository.findByEmail(postDto.getSenderEmail());
				

				Post post = new Post();
				post.setSender(sender);
				post.setReciever(postDto.getRecieverEmail());
				post.setPostSubject(postDto.getPostSubject());
				post.setPostContent(postDto.getPostContent());
				postRepository.save(post);

				return PostDto.toDto(post);
			}
	
	public List<PostDto> getPostList(String email){
		List<Post> postList = postRepository.findAllByReciever(email);
		List<PostDto> postDtoList = new ArrayList<>();
		
		for(Post post : postList) {
			postDtoList.add(PostDto.toDto(post));
		}
		
		return postDtoList;
	}
	
	/*private void existReciever(Post post) {
		Member findMember = memberRepository.findAllById(post.getReciever());
		if(findMember != null) {
			postRepository.save(post);
		}
	}*/
}
