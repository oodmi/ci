package org.oodmi.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.oodmi.model.branch.Branch;
import org.oodmi.service.BranchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Data
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public Collection<Branch> getBranches(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth) {
        return branchService.getBranches(auth);
    }
}
