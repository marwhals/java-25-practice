package practice.database.service.db;

import practice.database.internal.util.ConnectionUtils;
import practice.logger_module.Logger;

public class DatabaseServices {
    public static void connect() {
        ConnectionUtils.establishDbConnection();
        Logger.log("Database connected successfully!");
    }
}