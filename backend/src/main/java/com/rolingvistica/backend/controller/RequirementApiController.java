package com.rolingvistica.backend.controller;

import com.rolingvistica.backend.service.RequirementService;
import io.rolingvistica.api.RequirementApi;
import io.rolingvistica.dto.RequirementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RequirementApiController implements RequirementApi {
    private final RequirementService requirementService;

    /**
     * GET /requirement/{problemId} : fetch a list of requirements assigned to a problem
     *
     * @param problemId The Id of the problem for which to return the list of requirements (required)
     * @return list of requirements (status code 200)
     */
    @Override
    public ResponseEntity<List<RequirementDTO>> getRequirementsByProblemId(Long problemId) {
        return new ResponseEntity<>(requirementService.getRequirementsByProblemId(problemId), HttpStatus.OK);
    }
}
