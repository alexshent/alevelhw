package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateAutomobileCommand implements Command {
    private final Service<Automobile> service;

    public CreateAutomobileCommand(Service<Automobile> service) {
        this.service = service;
    }

    @Override
    public void execute() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("model: ");
            String model = reader.readLine();

            System.out.println("price: ");
            String price = reader.readLine();

            System.out.println("manufacturer: ");
            String manufacturer = reader.readLine();

            System.out.println("body type: ");
            String bodyType = reader.readLine();

            Automobile automobile = new Automobile(model, AutomobileManufacturers.valueOf(manufacturer), BigDecimal.valueOf(Long.parseLong(price)), bodyType);
            List<Automobile> list = new ArrayList<>();
            list.add(automobile);
            service.saveProducts(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "create automobile";
    }
}
