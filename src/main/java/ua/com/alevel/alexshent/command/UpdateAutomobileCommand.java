package ua.com.alevel.alexshent.command;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class UpdateAutomobileCommand implements Command {
    private final Service<Automobile> service;

    public UpdateAutomobileCommand(Service<Automobile> service) {
        this.service = service;
    }

    @Override
    public void execute() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("automobile id: ");
            String id = reader.readLine();

            System.out.println("model: ");
            String model = reader.readLine();

            System.out.println("price: ");
            String price = reader.readLine();

            System.out.println("manufacturer: ");
            String manufacturer = reader.readLine();

            System.out.println("body type: ");
            String bodyType = reader.readLine();

            Automobile automobile = service.getProductById(id).get();
            if (model.length() > 0) {
                automobile.setModel(model);
            }
            if (price.length() > 0) {
                automobile.setPrice(BigDecimal.valueOf(Long.parseLong(price)));
            }
            if (manufacturer.length() > 0) {
                automobile.setManufacturer(AutomobileManufacturers.valueOf(manufacturer));
            }
            if (bodyType.length() > 0) {
                automobile.setBodyType(bodyType);
            }
            service.updateProduct(automobile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "update automobile";
    }
}
