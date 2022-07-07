package oodmi.command;

import lombok.SneakyThrows;
import oodmi.command.provider.StateValueProvider;
import oodmi.shell.ShellHelper;
import org.oodmi.client.PullRequestClient;
import org.oodmi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.shell.standard.ShellOption.NULL;

@ShellComponent
public class PullCommand {

    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private PullRequestClient pullRequestClient;

    @SneakyThrows
    @ShellMethod(value = "Get pull list", key = "pull list")
    public void getPulls(@ShellOption(defaultValue = NULL) Integer page,
                         @ShellOption(defaultValue = NULL) String author,
                         @ShellOption(defaultValue = NULL) String into,
                         @ShellOption(defaultValue = NULL) String from,
                         @ShellOption(defaultValue = NULL, valueProvider = StateValueProvider.class) PullState state) {
        PullRequestFilter pullRequestFilter = new PullRequestFilter()
                .byState(state)
                .byIntoBranch(into)
                .byFromBranchAndAuthor(from, author);
        Collection<PullRequest> pullRequests = pullRequestClient.getPullRequests(page, pullRequestFilter).get();

        printPullRequestTable(pullRequests);
    }

    @SneakyThrows
    @ShellMethod(value = "Open new pull", key = "pull open")
    public void openPull(@ShellOption String from,
                         @ShellOption String into,
                         @ShellOption String title,
                         @ShellOption(defaultValue = NULL) String body) {
        OpenPullRequest request = new OpenPullRequest()
                .setInto(into)
                .setFrom(from)
                .setTitle(title)
                .setBody(body);

        PullRequest pullRequest = pullRequestClient.openPullRequest(request).get();

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Merge pull", key = "pull merge")
    public void mergePull(Integer number) {
        pullRequestClient.mergePullRequest(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully merged!");
    }

    @SneakyThrows
    @ShellMethod(value = "Close pull", key = "pull close")
    public void closePull(Integer number) {
        PullRequest pullRequest = pullRequestClient.closePullRequest(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully closed!");

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Reopen pull", key = "pull reopen")
    public void reopenPull(Integer number) {
        PullRequest pullRequest = pullRequestClient.reopenPullRequest(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully reopened!");

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Reopen pull", key = "pull commits")
    public void getPullCommits(Integer number) {
        try{
            Collection<Commit> commits = pullRequestClient.getPullRequestCommits(number).get();
            printCommitTable(commits);
        }catch (IllegalArgumentException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    private void printCommitTable(Collection<Commit> commits) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("sha", "sha");
        headers.put("message", "message");
        headers.put("date", "date");
        headers.put("author", "author");
        headers.put("email", "email");
        TableModel model = new BeanListTableModel<>(commits, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(120));
    }

    private void printPullRequestTable(Collection<PullRequest> pullRequests) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("number", "number");
        headers.put("state", "state");
        headers.put("login", "login");
        headers.put("name", "name");
        headers.put("email", "email");
        headers.put("into", "into");
        headers.put("from", "from");
        headers.put("title", "title");
        headers.put("summary", "summary");
        TableModel model = new BeanListTableModel<>(pullRequests, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(80));
    }
}