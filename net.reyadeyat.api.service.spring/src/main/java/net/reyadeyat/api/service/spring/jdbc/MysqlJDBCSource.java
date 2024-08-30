/*
 * Copyright (C) 2023 Reyadeyat
 *
 * Reyadeyat/RELATIONAL.API is licensed under the
 * BSD 3-Clause "New" or "Revised" License
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://reyadeyat.net/LICENSE/RELATIONAL.API.LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.reyadeyat.api.service.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import net.reyadeyat.api.library.jdbc.JDBCSource;

/**
 *
 * Description
 *
 *
 * @author Mohammad Nabil Mostafa
 * <a href="mailto:code@reyadeyat.net">code@reyadeyat.net</a>
 *
 * @since 2023.01.01
 */
public class MysqlJDBCSource implements JDBCSource {

    final private String data_database_server;
    final private String data_database_user_name;
    final private String data_database_password;
    final private String data_database_schema;
    final public static String database_schema = "";
    final public static String mysql_database_field_open_quote = "`";
    final public static String mysql_database_field_close_quote = "`";
    
    public MysqlJDBCSource(String data_database_server,
        String data_database_user_name,
        String data_database_password,
        String data_database_schema) {
        this.data_database_server = data_database_server;
        this.data_database_user_name = data_database_user_name;
        this.data_database_password = data_database_password;
        this.data_database_schema = data_database_schema;
    }

    @Override
    public String getDataSourceName() throws Exception {
        return getDatabaseName();
    }

    @Override
    public Connection getConnection(Boolean auto_commit) throws Exception {
        //CREATE DATABASE `data` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
        Connection database_connection = DriverManager.getConnection("jdbc:mysql://" + data_database_server + "/" + data_database_schema, data_database_user_name, data_database_password);
        database_connection.setAutoCommit(auto_commit);
        return database_connection;
    }

    @Override
    public String getUserName() throws Exception {
        return data_database_user_name;
    }

    @Override
    public String getUserPassword() throws Exception {
        return data_database_password;
    }

    @Override
    public String getDatabaseEngine() throws Exception {
        return "mysql";
    }

    @Override
    public String getURL() throws Exception {
        return "jdbc:mysql://" + data_database_server + "/" + data_database_schema;
    }

    @Override
    public String getDatabaseName() throws Exception {
        return data_database_schema;
    }

    @Override
    public String getDatabaseServer() throws Exception {
        return data_database_server;
    }

    @Override
    public String getDatabaseSchema() throws Exception {
        return database_schema;
    }

    @Override
    public String getDatabaseOpenQuote() throws Exception {
        return mysql_database_field_open_quote;
    }

    @Override
    public String getDatabaseCloseQuote() throws Exception {
        return mysql_database_field_close_quote;
    }
}
