package com.project.yeojeong.controller;

import com.project.yeojeong.dto.HeartFormDto;
import com.project.yeojeong.repository.HeartRepository;
import com.project.yeojeong.repository.MemberRepository;
import com.project.yeojeong.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;

    @PostMapping(value = "/new")
    public @ResponseBody ResponseEntity heartNew(@Valid @RequestBody HeartFormDto heartFormDto, Principal principal){
        String result = heartService.heartNew(heartFormDto, principal);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity heartDelete(@Valid @RequestBody HeartFormDto heartFormDto, Principal principal){
        heartService.heartDelete(heartFormDto, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    public @ResponseBody ResponseEntity heartGet(@Valid @RequestParam int postNo, Principal principal){
        System.out.println(principal.getName()+"KKKKK");
        Map<String, Boolean> body = new HashMap<>();

        if (heartRepository.findPostNoByMemberNotest(memberRepository.getByMemberId(principal.getName()).getMemberNo(), postNo) != null) {
            body.put("result", true);
        }else{
            body.put("result", false);
        }
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
