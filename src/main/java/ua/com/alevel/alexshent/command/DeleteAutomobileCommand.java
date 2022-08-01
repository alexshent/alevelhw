package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteAutomobileCommand implements Command {
    private final Service<Automobile> service;

    public DeleteAutomobileCommand(Service<Automobile> service) {
        this.service = service;
    }

    @Override
    public void execute() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("automobile id: ");
            String id = reader.readLine();

            if (id.length() > 0) {
                service.deleteProduct(id);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "delete automobile";
    }
}
