package com.example.demodb.Service;

import com.example.demodb.Model.Book;
import com.example.demodb.Repository.DBOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    DBOperations dbOperations;
    public String createTable(String tableName) throws SQLException {
        return dbOperations.createTable(tableName);
    }

    public String insertBook(Book book) throws SQLException {
        return dbOperations.insertBook(book);
    }

    public List<Book> getAllBooks() throws SQLException {
        return dbOperations.getAllBooks();
    }

    public Book getBookById(int id) throws SQLException {
        return dbOperations.getBookById(id);
    }
}
