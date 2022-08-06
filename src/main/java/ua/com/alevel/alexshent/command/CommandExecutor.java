package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.Service;

import java.util.*;

public class CommandExecutor {
    private final Map<Integer, Command> options = new HashMap<>();

    public CommandExecutor(Service<Automobile> service) {
        options.put(1, new CreateAutomobileCommand(service));
        options.put(2, new UpdateAutomobileCommand(service));
        options.put(3, new DeleteAutomobileCommand(service));
        options.put(4, new DisplayAutomobileCommand(service));
        options.put(5, new DisplayAllAutomobilesCommand(service));
    }

    public void executeCommand(int option) {
        if (option < 0) {
            throw new IllegalArgumentException("invalid option");
        }
        Command command = options.get(option);
        if (command != null) {
            command.execute();
        }
    }

    public String getOptionsMenu() {
        final String menuHeader = "----------------------";
        final String menuFooter = "----------------------";
        final String separator = " - ";
        List<Integer> keys = new ArrayList<>(options.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(menuHeader);
        stringBuilder.append("\n");
        for (Integer key : keys) {
            stringBuilder.append(key);
            stringBuilder.append(separator);
            stringBuilder.append(options.get(key).toString());
            stringBuilder.append("\n");
        }
        stringBuilder.append(menuFooter);
        return stringBuilder.toString();
    }
}
