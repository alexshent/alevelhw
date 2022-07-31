package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.Service;

public class DisplayAllAutomobilesCommand implements Command {
    private final Service<Automobile> service;

    public DisplayAllAutomobilesCommand(Service<Automobile> service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.printAll();
    }
}
