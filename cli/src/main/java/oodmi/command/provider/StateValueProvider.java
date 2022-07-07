package oodmi.command.provider;

import org.oodmi.model.PullState;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.EnumValueProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateValueProvider extends EnumValueProvider {

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return super.supports(parameter, completionContext);
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        return Arrays.stream(PullState.values())
                .map(state -> new CompletionProposal(state.name()))
                .collect(Collectors.toList());

    }
}
