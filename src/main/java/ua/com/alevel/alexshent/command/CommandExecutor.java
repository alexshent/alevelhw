package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.Service;

public class CommandExecutor {
    public static final int EXIT_COMMAND = 0;
    public static final int CREATE_AUTOMOBILE_COMMAND = 1;
    public static final int UPDATE_AUTOMOBILE_COMMAND = 2;
    public static final int DELETE_AUTOMOBILE_COMMAND = 3;
    public static final int DISPLAY_AUTOMOBILE_COMMAND = 4;
    public static final int DISPLAY_ALL_AUTOMOBILES_COMMAND = 5;
    private static final int COMMANDS_ARRAY_SIZE = 6;
    private final Command[] commands = new Command[COMMANDS_ARRAY_SIZE];

    public CommandExecutor(Service<Automobile> service) {
        commands[CREATE_AUTOMOBILE_COMMAND] = new CreateAutomobileCommand(service);
        commands[UPDATE_AUTOMOBILE_COMMAND] = new UpdateAutomobileCommand(service);
        commands[DELETE_AUTOMOBILE_COMMAND] = new DeleteAutomobileCommand(service);
        commands[DISPLAY_AUTOMOBILE_COMMAND] = new DisplayAutomobileCommand(service);
        commands[DISPLAY_ALL_AUTOMOBILES_COMMAND] = new DisplayAllAutomobilesCommand(service);
    }

    public void executeCommand(int option) {
        if (option < 0 || option >= commands.length) {
            throw new IllegalArgumentException("invalid option");
        }
        if (commands[option] != null) {
            commands[option].execute();
        }
    }

    public String getOptions() {
        return """
                ----------------------
                0 - exit
                1 - create automobile
                2 - update automobile
                3 - delete automobile
                4 - display automobile
                5 - display all automobiles
                ----------------------
                """;
    }
}
