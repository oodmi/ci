package oodmi.command;

import lombok.SneakyThrows;
import oodmi.shell.ShellHelper;
import org.oodmi.client.BranchClient;
import org.oodmi.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.Collection;
import java.util.LinkedHashMap;

@ShellComponent
public class BranchCommand {

    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private BranchClient branchClient;

    @SneakyThrows
    @ShellMethod(value = "Get branch list", key = "branch list")
    public void getBranches() {
        Collection<Branch> branches = branchClient.getBranches().get();

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("name", "name");
        headers.put("sha", "commit");
        TableModel model = new BeanListTableModel<>(branches, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_light);
        shellHelper.print(tableBuilder.build().render(80));
    }
}