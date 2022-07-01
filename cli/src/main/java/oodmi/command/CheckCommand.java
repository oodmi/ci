package oodmi.command;

import lombok.SneakyThrows;
import oodmi.shell.ShellHelper;
import org.oodmi.client.TokenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CheckCommand {

    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private TokenClient tokenClient;

    @SneakyThrows
    @ShellMethod(value = "Check token", key = "token check")
    public void checkToken() {
        Boolean valid = tokenClient.check().get();
        if (valid) {
            shellHelper.printSuccess("Token is valid");
        } else {
            shellHelper.printError("Token is invalid");
        }
    }
}