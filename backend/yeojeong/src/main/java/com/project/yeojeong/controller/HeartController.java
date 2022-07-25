package com.project.yeojeong.controller;

import com.project.yeojeong.dto.HeartFormDto;
import com.project.yeojeong.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {
    private final HeartService heartService;

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
}
