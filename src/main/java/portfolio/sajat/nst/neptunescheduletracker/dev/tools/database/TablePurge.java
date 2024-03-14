package portfolio.sajat.nst.neptunescheduletracker.dev.tools.database;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

@Slf4j
@Component
public class TablePurging {
    private final String startPath;

    public TablePurging(@Value("${TABLE_PURGE_SCRIPT_PATH}") String tablePurgeScriptPath) {
        this.startPath = tablePurgeScriptPath;
    }

    @SneakyThrows
    public void runTablePurgeScript(DBConnector dbConnector) {
        try (Connection connection = dbConnector.createConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setSendFullScript(false);
            scriptRunner.setStopOnError(true);
            scriptRunner.runScript(new FileReader(startPath));
        } catch (FileNotFoundException e) {
            log.error("Table purge path could not be found");
        }
    }
}
