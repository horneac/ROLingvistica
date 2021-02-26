package com.rolingvistica.backend.controller;

import com.rolingvistica.backend.service.PartialScoreElementService;
import io.rolingvistica.api.PartialScoreElementsApi;
import io.rolingvistica.dto.PartialScoreElementDTO;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartialScoreElementApiController implements PartialScoreElementsApi {
    private final PartialScoreElementService partialScoreElementService;

    /**
     * GET /grading/elements/{requirementId} : Fetch the list of elements used to grade the given requirement
     *
     * @param requirementId The ID of the requirement to which the answers were given (required)
     * @return List of partial scores per element (status code 200)
     */
    @Override
    public ResponseEntity<List<PartialScoreElementDTO>> getPartialScoreElementsByRequirementId(Long requirementId) {
        return new ResponseEntity<>(partialScoreElementService.getPartialScoreElementsByRequirementId(requirementId), HttpStatus.OK);
    }
}
