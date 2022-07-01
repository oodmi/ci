package org.oodmi.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Data;
import org.oodmi.model.commit.CommitInfo;
import org.oodmi.model.pull.Pull;
import org.oodmi.model.request.MergeRequest;
import org.oodmi.model.request.MergeResponse;
import org.oodmi.service.PullService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Data
@RequestMapping("/pull")
public class PullController {

    private final PullService pullService;

    @GetMapping
    public Collection<Pull> getPulls(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth) {
        return pullService.getPulls(auth);
    }

    @GetMapping("/{id}/commits")
    public Collection<CommitInfo> getPullCommits(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth,
            @PathVariable("id") Integer id) {
        return pullService.getPullCommits(auth, id);
    }

    @PutMapping("/{id}/merge")
    public MergeResponse mergePull(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth,
            @PathVariable("id") Integer id,
            @RequestBody MergeRequest request) {
        return pullService.mergePull(auth, id, request);
    }

    @PatchMapping("/{id}/close")
    public MergeResponse closePull(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth,
            @PathVariable("id") Integer id) {
        return pullService.closePull(auth, id);
    }

    @PatchMapping("/{id}/reopen")
    public MergeResponse reopenPull(
            @Parameter(description = "token",
                    example = "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",
                    required = true,
                    name = "token",
                    in = ParameterIn.HEADER)
            @RequestHeader("token") String auth,
            @PathVariable("id") Integer id) {
        return pullService.reopenPull(auth, id);
    }
}
