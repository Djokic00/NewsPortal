package com.application.backend.repository.comment;

import com.application.backend.entities.Comment;
import com.application.backend.entities.News;
import com.application.backend.repository.MySqlAbstractRepository;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> allComments() {
        List<Comment> commentList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM comments ORDER BY creation_date DESC");
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt("id"), resultSet.getString("author"),
                        resultSet.getString("content"), resultSet.getDate("creation_date"));
                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
                preparedStatement.setInt(1, resultSet.getInt("news"));
                resultSetNews = preparedStatement.executeQuery();
                while (resultSetNews.next()) {
                    News news = new News(resultSetNews.getInt("id"), resultSetNews.getString("title"),
                            resultSetNews.getString("content"), resultSetNews.getDate("creation_date"));
                    news.setVisits(resultSetNews.getInt("visits"));
                    synchronized (this) {
                        comment.setNews(news);
                    }
                    commentList.add(comment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return commentList;
    }

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
//            preparedStatement = connection.prepareStatement("ALTER TABLE comment AUTO_INCREMENT = 6;");
//            preparedStatement.executeUpdate();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO comment (author, content, creation_date, news) values (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor());
            preparedStatement.setString(2, comment.getContent());
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(4, comment.getNews().getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public Comment findComment(Integer id) {
        Comment comment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetNews = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comment WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String author = resultSet.getString("author");
                String content = resultSet.getString("content");
                Date date = resultSet.getDate("creation_date");
                comment = new Comment(id, author, content, date);
//                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ?");
//                preparedStatement.setInt(1, resultSet.getInt("news"));
//                resultSetNews = preparedStatement.executeQuery();
//                while (resultSetNews.next()) {
//                    News news = new News(resultSetNews.getInt("id"), resultSetNews.getString("title"),
//                            resultSetNews.getString("content"), resultSetNews.getDate("creation_date"));
//                    news.setVisits(resultSetNews.getInt("visits"));
//
//                    synchronized (this) {
//                        comment.setNews(news);
//                    }
//                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
