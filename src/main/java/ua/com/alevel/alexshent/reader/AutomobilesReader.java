package ua.com.alevel.alexshent.reader;

import ua.com.alevel.alexshent.model.Automobile;

import java.nio.file.Path;
import java.util.List;

public class AutomobilesReader {
    private final AutomobileParser automobileParser = new AutomobileParser();

    public List<Automobile> readXmlList(Path path) {
        AutomobilesListRegularExpressions automobilesListRegularExpressions =
                new AutomobilesListRegularExpressions(
                        "<automobiles>(.+)</automobiles>"
                );
        AutomobileRegularExpressions automobileRegularExpressions =
                new AutomobileRegularExpressions(
                        "<model>.+?</engine>",
                        "<model>(.+)</model>",
                        "<price.*>(.+)</price>",
                        "<manufacturer>(.+)</manufacturer>",
                        "<bodyType>(.+)</bodyType>",
                        "<createdAt>(.+)</createdAt>",
                        "<tripCounter>(.+)</tripCounter>"
                );
        EngineRegularExpressions engineRegularExpressions =
                new EngineRegularExpressions(
                        "<engine>(.+)</engine>",
                        "<volume>(.+)</volume>",
                        "<brand>(.+)</brand>"
                );
        automobileParser.setAutomobilesListRegularExpressions(automobilesListRegularExpressions);
        automobileParser.setAutomobileRegularExpressions(automobileRegularExpressions);
        automobileParser.setEngineRegularExpressions(engineRegularExpressions);
        return automobileParser.parseAutomobilesList(path);
    }

    public List<Automobile> readJsonList(Path path) {
        AutomobilesListRegularExpressions automobilesListRegularExpressions =
                new AutomobilesListRegularExpressions(
                        "\\[(.+)\\]"
                );
        AutomobileRegularExpressions automobileRegularExpressions =
                new AutomobileRegularExpressions(
                        "\\{\"model\".+?\\}\\},?",
                        "\"model\":\"(.+?)\"",
                        "\"price\":\"(.+?)\"",
                        "\"manufacturer\":\"(.+?)\"",
                        "\"bodyType\":\"(.+?)\"",
                        "\"createdAt\":\"(.+?)\"",
                        "\"tripCounter\":\"(.+?)\""
                );
        EngineRegularExpressions engineRegularExpressions =
                new EngineRegularExpressions(
                        "\"engine\":\\{(.+?)\\}",
                        "\"volume\":\"(.+?)\"",
                        "\"brand\":\"(.+?)\""
                );
        automobileParser.setAutomobilesListRegularExpressions(automobilesListRegularExpressions);
        automobileParser.setAutomobileRegularExpressions(automobileRegularExpressions);
        automobileParser.setEngineRegularExpressions(engineRegularExpressions);
        return automobileParser.parseAutomobilesList(path);
    }
}
