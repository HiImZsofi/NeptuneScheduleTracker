package portfolio.sajat.nst.neptunescheduletracker.dev.tools.database;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

/*
Executable bean that automatically creates the empty database tables
 */
@Slf4j
@Component
public class TableCreation {

    private final String startPath;

    //Gets the path value from application.properties
    @Value("${TABLE_CREATE_SCRIPT_PATH}")
    private String tableCreateScriptPath;

    public TableCreation(@Value("${TABLE_CREATE_SCRIPT_PATH}") String tableCreateScriptPath) {
        this.startPath = tableCreateScriptPath;
    }

    @SneakyThrows
    public void runTableCreateScript(DBConnector dbConnector) {
        try (Connection connection = dbConnector.createConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setSendFullScript(false);
            scriptRunner.setStopOnError(true);
            scriptRunner.runScript(new FileReader(startPath));
        } catch (FileNotFoundException e) {
            log.error("Table create path could not be found");
        }
    }
}