package gr.aueb.cf.schoolapp.dao.util;

import gr.aueb.cf.schoolapp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class DBHelper {
    private static final Set<String> allowed_tables = Set.of("teachers", "students", "cities");

    private DBHelper() {

    }

    public static void eraseTableData(String table) throws SQLException {
        if (allowed_tables.contains(table)) {
            String sqlFkOff = "SET @@foreign_key_checks = 0";
            String sqlFkOn = "SET @@foreign_key_checks = 1";
            String sqlDelete = "DELETE FROM " + table;
            String sqlAutoInc = "ALTER TABLE " + table + " AUTO_INCREMENT = 1";


            try (Connection connection = DBUtil.getConnection()) {
                connection.setAutoCommit(false);
                try (PreparedStatement ps1 = connection.prepareStatement(sqlFkOff);
                     PreparedStatement ps2 = connection.prepareStatement(sqlDelete);
                     PreparedStatement ps3 = connection.prepareStatement(sqlAutoInc);
                     PreparedStatement ps4 = connection.prepareStatement(sqlFkOn)) {
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                    ps4.executeUpdate();
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            }
        }
    }
}
