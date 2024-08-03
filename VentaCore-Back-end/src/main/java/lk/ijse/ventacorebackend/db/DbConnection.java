package lk.ijse.ventacorebackend.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;
    static Logger logger = LoggerFactory.getLogger(DbConnection.class);

    private DbConnection() throws SQLException {
        logger.info("Initializing DB connection");
        try {
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/ventaCore");
            this.connection = pool.getConnection();
            logger.debug("Connection Initialized: {}", this.connection);
        } catch (SQLException | NamingException e) {
            logger.error("DB connection initialization failed", e);
            e.printStackTrace();
        }
    }

    public static DbConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            synchronized (DbConnection.class) {
                if (dbConnection == null) {
                    dbConnection = new DbConnection();
                }
            }
        }
        return dbConnection;
    }
}
