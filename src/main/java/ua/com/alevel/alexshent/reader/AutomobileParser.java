package ua.com.alevel.alexshent.reader;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.model.Engine;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomobileParser {
    private EngineRegularExpressions engineRegularExpressions;
    private AutomobileRegularExpressions automobileRegularExpressions;
    private AutomobilesListRegularExpressions automobilesListRegularExpressions;

    public void setEngineRegularExpressions(EngineRegularExpressions engineRegularExpressions) {
        if (engineRegularExpressions == null) {
            throw new IllegalArgumentException("engineRegularExpressions is null");
        }
        this.engineRegularExpressions = engineRegularExpressions;
    }

    public void setAutomobileRegularExpressions(AutomobileRegularExpressions automobileRegularExpressions) {
        if (automobileRegularExpressions == null) {
            throw new IllegalArgumentException("automobileRegularExpressions is null");
        }
        this.automobileRegularExpressions = automobileRegularExpressions;
    }

    public void setAutomobilesListRegularExpressions(AutomobilesListRegularExpressions automobilesListRegularExpressions) {
        if (automobilesListRegularExpressions == null) {
            throw new IllegalArgumentException("automobilesListRegularExpressions is null");
        }
        this.automobilesListRegularExpressions = automobilesListRegularExpressions;
    }

    private String parseAutomobilesData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        String result = "";
        final Pattern patternAutomobiles = Pattern.compile(automobilesListRegularExpressions.automobilesList());
        Matcher matcherAutomobiles = patternAutomobiles.matcher(data);
        if (matcherAutomobiles.find()) {
            result = matcherAutomobiles.group(1);
        }
        return result;
    }

    private Engine parseEngine(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        final Pattern patternVolume = Pattern.compile(engineRegularExpressions.volume());
        final Pattern patternBrand = Pattern.compile(engineRegularExpressions.brand());
        Matcher matcher;

        // volume
        String volume = "";
        matcher = patternVolume.matcher(data);
        if (matcher.find()) {
            volume = matcher.group(1);
        }

        // brand
        String brand = "";
        matcher = patternBrand.matcher(data);
        if (matcher.find()) {
            brand = matcher.group(1);
        }

        return new Engine(Integer.parseInt(volume), brand);
    }

    private Automobile parseAutomobile(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        final Pattern patternModel = Pattern.compile(automobileRegularExpressions.model());
        final Pattern patternPrice = Pattern.compile(automobileRegularExpressions.price());
        final Pattern patternManufacturer = Pattern.compile(automobileRegularExpressions.manufacturer());
        final Pattern patternBodyType = Pattern.compile(automobileRegularExpressions.bodyType());
        final Pattern patternCreatedAt = Pattern.compile(automobileRegularExpressions.createdAt());
        final Pattern patternTripCounter = Pattern.compile(automobileRegularExpressions.tripCounter());
        final Pattern patternEngine = Pattern.compile(engineRegularExpressions.data());
        Matcher matcher;

        // model
        String model = "";
        matcher = patternModel.matcher(data);
        if (matcher.find()) {
            model = matcher.group(1);
        }

        //price
        String price = "";
        matcher = patternPrice.matcher(data);
        if (matcher.find()) {
            price = matcher.group(1);
        }

        // manufacturer
        String manufacturer = "";
        matcher = patternManufacturer.matcher(data);
        if (matcher.find()) {
            manufacturer = matcher.group(1);
        }

        // body type
        String bodyType = "";
        matcher = patternBodyType.matcher(data);
        if (matcher.find()) {
            bodyType = matcher.group(1);
        }

        // created at
        String createdAt = "";
        matcher = patternCreatedAt.matcher(data);
        if (matcher.find()) {
            createdAt = matcher.group(1);
        }

        // trip counter
        String tripCounter = "";
        matcher = patternTripCounter.matcher(data);
        if (matcher.find()) {
            tripCounter = matcher.group(1);
        }

        // engine
        String engine = "";
        matcher = patternEngine.matcher(data);
        if (matcher.find()) {
            engine = matcher.group(1);
        }

        Automobile automobile = new Automobile(
                model,
                AutomobileManufacturers.valueOf(manufacturer),
                BigDecimal.valueOf(Double.parseDouble(price)),
                bodyType
        );
        automobile.setTripCounter(Long.parseLong(tripCounter));
        automobile.setCreatedAt(ZonedDateTime.parse(createdAt).toLocalDateTime());
        automobile.setEngine(parseEngine(engine));
        return automobile;
    }

    public List<Automobile> parseAutomobilesList(Path path) {
        List<Automobile> list = new ArrayList<>();
        try {
            String data = new String(Files.readAllBytes(path));
            data = data.replaceAll("\\s+", "");
            String automobilesList = parseAutomobilesData(data);
            final Pattern patternAutomobile = Pattern.compile(automobileRegularExpressions.data());
            Matcher matcherAutomobile = patternAutomobile.matcher(automobilesList);
            while (matcherAutomobile.find()) {
                String automobileData = matcherAutomobile.group();
                Automobile automobile = parseAutomobile(automobileData);
                list.add(automobile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
