package oodmi.command;

import lombok.SneakyThrows;
import oodmi.command.provider.StateValueProvider;
import oodmi.shell.ShellHelper;
import org.oodmi.client.PullRequestClient;
import org.oodmi.enums.State;
import org.oodmi.model.commit.Commit;
import org.oodmi.model.pull.PullRequest;
import org.oodmi.model.request.OpenPullRequest;
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
                         @ShellOption(defaultValue = NULL) String branch,
                         @ShellOption(defaultValue = NULL, valueProvider = StateValueProvider.class) State state) {
        Collection<PullRequest> pullRequests = pullRequestClient.getPulls(page, state, author, branch).get();

        printPullRequestTable(pullRequests);
    }

    @SneakyThrows
    @ShellMethod(value = "Open new pull", key = "pull open")
    public void openPull(@ShellOption String from,
                         @ShellOption String into,
                         @ShellOption String title,
                         @ShellOption(defaultValue = NULL) String body) {
        OpenPullRequest request = new OpenPullRequest()
                .setBase(into)
                .setHead(from)
                .setTitle(title)
                .setBody(body);

        PullRequest pullRequest = pullRequestClient.openPull(request).get();

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Merge pull", key = "pull merge")
    public void mergePull(Integer number) {
        pullRequestClient.mergePull(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully merged!");
    }

    @SneakyThrows
    @ShellMethod(value = "Close pull", key = "pull close")
    public void closePull(Integer number) {
        PullRequest pullRequest = pullRequestClient.closePull(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully closed!");

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Reopen pull", key = "pull reopen")
    public void reopenPull(Integer number) {
        PullRequest pullRequest = pullRequestClient.reopenPull(number).get();

        shellHelper.printSuccess("Pull request with number " + number + " successfully reopened!");

        printPullRequestTable(List.of(pullRequest));
    }

    @SneakyThrows
    @ShellMethod(value = "Reopen pull", key = "pull commits")
    public void getPullCommits(Integer number) {
        Collection<Commit> commits = pullRequestClient.getPullCommits(number).get();

        printCommitTable(commits);
    }

    private void printCommitTable(Collection<Commit> commits) {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("sha", "sha");
        headers.put("commit.message", "message");
        headers.put("commit.author.date", "date");
        headers.put("commit.author.name", "author");
        headers.put("commit.author.email", "email");
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
        headers.put("user.login", "login");
        headers.put("user.name", "name");
        headers.put("user.email", "email");
        headers.put("base.ref", "into");
        headers.put("head.ref", "from");
        headers.put("title", "title");
        headers.put("body", "summary");
        TableModel model = new BeanListTableModel<>(pullRequests, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(80));
    }
}