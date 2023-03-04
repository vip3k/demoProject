package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

        public Properties getProperties () {
            Properties prop = new Properties();

            try {
                prop.load(Files.newInputStream(Paths.get("src/main/resources/user.properties")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return prop;
        }
    }
