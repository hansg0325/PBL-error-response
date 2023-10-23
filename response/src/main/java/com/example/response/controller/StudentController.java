package com.example.response.controller;

import com.example.response.entity.ApiResponse;
import com.example.response.entity.ErrorCode;
import com.example.response.exception.CustomException;
import com.example.response.exception.InputRestriction;
import com.example.response.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/student")
    public ApiResponse add(
            @RequestParam String name,
            @RequestParam int grade
    ) {
        // 6이상으로 요청하면 예외 던지기
        if (grade >= 6) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "grade 는 6 이상을 입력할 수 없습니다.", new InputRestriction(6));
        }
        return makeResponse(studentService.addStudent(name, grade));
    }

    // 전체 성적 조회
    @GetMapping("/students")
    public ApiResponse getAll() {
        return makeResponse(studentService.getAll());
    }

    // 특정 성적을 입력 받아, 해당 성적의 학생들을 조회
    @GetMapping("/students/{grade}")
    public ApiResponse getGradeStudent(
            @PathVariable("grade") int grade
    ) {
        return makeResponse(studentService.getGradeStudent(grade));
    }

    public <T> ApiResponse<T> makeResponse(List<T> result) {
        return new ApiResponse<>(result);
    }

    public <T> ApiResponse<T> makeResponse(T result) {
        return makeResponse(Collections.singletonList(result));
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse customExceptionHandler(HttpServletResponse response, CustomException customException) {
//        response.setStatus(customException.getErrorCode().getHttpStatus());
        return new ApiResponse(customException.getErrorCode().getCode(),
                customException.getErrorCode().getMessage(),
                customException.getData());


    }
}
