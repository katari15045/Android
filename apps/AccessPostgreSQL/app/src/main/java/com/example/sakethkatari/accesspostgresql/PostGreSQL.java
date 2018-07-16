package com.example.sakethkatari.accesspostgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by Saketh Katari on 06-02-2018.
 */

public class PostGreSQL implements Runnable
{
    private String result = null;
    private String ip = null;
    private String port = null;
    private String dbName = null;
    private String username = null;
    private String password = null;
    private String command = null;
    private boolean isQuery;

    private Connection connection = null;
    private Statement statement = null;

    public PostGreSQL(String ip, String port, String dbName, String username, String password, String command, boolean isQuery)
    {
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.command = command;
        this.isQuery = isQuery;
    }

    public void run()
    {
        connect();

        if(isQuery)
        {
            executeQuery();
        }

        else
        {
            executeUpdate();
        }
    }

    public void connect()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + ip + ":"+ port + "/" + dbName, username, password);
            statement = connection.createStatement();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void executeUpdate()
    {
        try
        {
            statement.executeUpdate(command);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        result = "Updated!";
    }

    public void executeQuery()
    {
        ResultSet resultSet = null;

        try
        {
            resultSet = statement.executeQuery(command);
            result = printResultSet(resultSet);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String printResultSet(ResultSet resultSet) throws Exception
    {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols = metaData.getColumnCount();
        int curCol;
        StringBuilder stringBuilder = new StringBuilder();

        curCol = 1;

        while (curCol <= cols)
        {
            if( curCol == cols )
            {
                stringBuilder.append( metaData.getColumnName(curCol) );
            }

            else
            {
                stringBuilder.append( metaData.getColumnName(curCol) ).append(" || ");
            }

            curCol = curCol + 1;
        }

        stringBuilder.append("\n\n");

        while( resultSet.next() )
        {
            curCol = 1;

            while( curCol <= cols )
            {
                if( curCol == 1 )
                {
                    stringBuilder.append("=> ");
                }

                if( curCol == cols )
                {
                    stringBuilder.append( resultSet.getString(curCol) );
                }

                else
                {
                    stringBuilder.append( resultSet.getString(curCol) ).append(" || ");
                }

                curCol = curCol + 1;
            }

            stringBuilder.append("\n");
        }

        return  stringBuilder.toString();
    }

    public String getResult()
    {
        return result;
    }
}













