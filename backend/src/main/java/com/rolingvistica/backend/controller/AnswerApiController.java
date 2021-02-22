package com.rolingvistica.backend.controller;

import com.rolingvistica.backend.service.AnswerService;
import io.rolingvistica.api.AnswerApi;
import io.rolingvistica.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnswerApiController implements AnswerApi {
    private final AnswerService answerService;
    /**
     * GET /answer/{requirementId} : Fetch a list of answers of a requirement
     *
     * @param requirementId The ID of the requirement to which the answers were given (required)
     * @return List of answers (status code 200)
     */
    @Override
    public ResponseEntity<List<AnswerDTO>> getAnswersByRequirementId(Long requirementId) {
        return new ResponseEntity<>(answerService.getAnswers(requirementId), HttpStatus.OK);
    }
}
