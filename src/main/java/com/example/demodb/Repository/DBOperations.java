package com.example.demodb.Repository;

import com.example.demodb.Model.Book;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DBOperations {
    private Connection connection;
    private String tableName;

    public void getConnection() throws SQLException {
        if(connection == null){
            // protocol:database-type://location-of-db/db-name
            // we can fetch the password and user in the environment variable and fetch it from
            // the file.
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Books",
                    "", "");
        }
    }

    public void closeConnection() {
        // responsibility of Garbage Collector to remove the connection
        if(connection != null)
            connection = null;
    }

    public String createTable(String tableName) throws SQLException {
        getConnection();
        this.tableName = tableName;
        // Create Statement is static
        Statement statement = connection.createStatement();
        boolean isExecuted = statement.execute("CREATE TABLE " + tableName + "(id  SERIAL PRIMARY KEY, " +
                "name VARCHAR(30), author_name VARCHAR(30), cost int)");
        closeConnection();
        if(isExecuted)
            return "Table " + tableName + " is created successfully";
        else
            return "Table " + tableName + " is not created successfully";
    }

    public String insertBook(Book book) throws SQLException {
        getConnection();
        // Prepare Statement is dynamic
        PreparedStatement  prepareStatement = connection.prepareStatement("INSERT INTO " +
                this.tableName + "(name, author_name, cost)" + " VALUES (?, ?, ?)");
        prepareStatement.setString(1, book.getName());
        prepareStatement.setString(2, book.getAuthorName());
        prepareStatement.setInt(3, book.getCost());
        int rows = prepareStatement.executeUpdate();
        closeConnection();
        if(rows == 1)
            return "Book is inserted successfully";
        else
            return "Book is not inserted successfully";
    }

    public List<Book> getAllBooks() throws SQLException {
        getConnection();
        List<Book> books = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + this.tableName);
        closeConnection();
        while (resultSet.next()){
            String name = resultSet.getString(2);
            String author_name = resultSet.getString(3);
            int cost = resultSet.getInt(4);
            Book book = new Book(name, author_name, cost);
            books.add(book);
        }
        return books;
    }

    public Book getBookById(int id) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + this.tableName +
                " WHERE id = " + id);
        closeConnection();
        if (resultSet.next()){
            String name = resultSet.getString(2);
            String author_name = resultSet.getString(3);
            int cost = resultSet.getInt(4);
            return new Book(name, author_name, cost);
        }
        System.out.println("No book with the given id is present inside the DB");
        return null;
    }
}
