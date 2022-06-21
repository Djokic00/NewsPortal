package com.application.backend.repository.news;

import com.application.backend.entities.*;
import com.application.backend.repository.MySqlAbstractRepository;
import com.application.backend.repository.comment.CommentRepository;
import com.application.backend.repository.tag.TagRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Inject
    private TagRepository tagRepository;
    @Inject
    private CommentRepository commentRepository;

    @Override
    public List<News> allNews() {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news ORDER BY creation_date DESC");
            while (i < 10 && resultSet.next()) {
                i++;
                News news = new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getDate("creation_date"));
                news.setVisits(resultSet.getInt("visits"));
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();
                while (resultSetUser.next()) {
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));
                    synchronized (this) {
                        news.setAuthor(user);
                    }
                }
                preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
                preparedStatement.setString(1, resultSet.getString("category"));
                resultSetCategory = preparedStatement.executeQuery();
                while (resultSetCategory.next()) {
                    Category category = new Category(resultSetCategory.getString("name"), resultSetCategory.getString("description"));
                    synchronized (this) {
                        news.setCategory(category);
                    }
                }
                newsList.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allNewsByVisits() {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        int i = 0;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM news WHERE creation_date BETWEEN NOW() - INTERVAL 30 DAY AND NOW() ORDER BY visits DESC");
            while (i < 10 && resultSet.next()) {
                i++;
                News news = new News(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"),
                        resultSet.getDate("creation_date"));
                news.setVisits(resultSet.getInt("visits"));

                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();

                while (resultSetUser.next()){
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));
                    synchronized (this) {
                        news.setAuthor(user);
                    }
                }
                preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
                preparedStatement.setString(1, resultSet.getString("category"));
                resultSetCategory = preparedStatement.executeQuery();
                while (resultSetCategory.next()) {
                    Category category = new Category(resultSetCategory.getString("name"), resultSetCategory.getString("description"));
                    synchronized (this) {
                        news.setCategory(category);
                    }
                }
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetTag = null;
        try {
            connection = this.newConnection();
//            preparedStatement = connection.prepareStatement("ALTER TABLE news AUTO_INCREMENT = 7;");
//            preparedStatement.executeUpdate();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO news (title, content, creation_date, visits, author, category) VALUES(?, ?,?, ?,?,?)", generatedColumns);
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, news.getAuthor().getEmail());
            preparedStatement.setString(6, news.getCategory().getName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }

            for (String s: news.getTag()) {
                preparedStatement = connection.prepareStatement("SELECT * FROM tag WHERE tag_name = ?");
                preparedStatement.setString(1, s);
                resultSetTag = preparedStatement.executeQuery();
                if (resultSetTag.next()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO tag_news (id_news, id_tag) VALUES (?, ?)");
                    preparedStatement.setInt(1, news.getId());
                    preparedStatement.setInt(2, resultSetTag.getInt("id"));
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News updateNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news AS n SET n.title = ?, n.content = ?, " +
                    "n.author = ?, n.category = ?  where n.id = ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setString(3, news.getAuthor().getEmail());
            preparedStatement.setString(4, news.getCategory().getName());
            preparedStatement.setInt(5, news.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News findNews(Integer id) {
        News news = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date creationDate = resultSet.getDate("creation_date");
                Integer visits = resultSet.getInt("visits");
                preparedStatement = connection.prepareStatement("UPDATE news SET news.visits = news.visits + 1 WHERE news.id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                news = new News(id, title, content, creationDate, visits);
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();
                while (resultSetUser.next()) {
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));
                    synchronized (this) {
                        news.setAuthor(user);
                    }
                }
                preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
                preparedStatement.setString(1, resultSet.getString("category"));
                resultSetCategory = preparedStatement.executeQuery();
                while (resultSetCategory.next()){
                    Category category = new Category(resultSetCategory.getString("name"), resultSetCategory.getString("description"));
                    synchronized (this) {
                        news.setCategory(category);
                    }
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allByCategory(String name) {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE category LIKE ? ORDER BY creation_date DESC");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("content"), resultSet.getDate("creation_date"));
                news.setVisits(resultSet.getInt("visits"));
                newsList.add(news);
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
        return newsList;
    }

    @Override
    public List<News> allByTag(Integer id) {
        List<News> newsList = new ArrayList<>();
        News news = null;
        Connection connection = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tag_news WHERE id_tag = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer idNews = resultSet.getInt("id_news");
                preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE id = ? ");
                preparedStatement.setInt(1, idNews);
                resultSet1 = preparedStatement.executeQuery();
                if (resultSet1.next()) {
                    String title = resultSet1.getString("title");
                    String content = resultSet1.getString("content");
                    Date createdAt = resultSet1.getDate("creation_date");
                    Integer visits = resultSet1.getInt("visits");
                    news = new News(idNews, title, content, createdAt, visits);
                    preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
                    preparedStatement.setString(1, resultSet1.getString("author"));
                    resultSetUser = preparedStatement.executeQuery();
                    while (resultSetUser.next()) {
                        User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                        user.setStatus(resultSetUser.getInt("status"));
                        user.setType(resultSetUser.getInt("type"));

                        synchronized (this) {
                            news.setAuthor(user);
                        }
                    }
                    preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE name = ?");
                    preparedStatement.setString(1, resultSet1.getString("category"));
                    resultSetCategory = preparedStatement.executeQuery();
                    while (resultSetCategory.next()){
                        Category category = new Category(resultSetCategory.getString("name"), resultSetCategory.getString("description"));
                        synchronized (this) {
                            news.setCategory(category);
                        }
                    }
                }
                newsList.add(news);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<Tag> allTagByNews(Integer id) {
        List<Tag> tagList = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tag_news WHERE id_news = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag tag = tagRepository.findTagById(resultSet.getInt("id_tag"));
                tagList.add(tag);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tagList;
    }

    @Override
    public List<Comment> allCommentsByNews(Integer id) {
        List<Comment> tagList = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM comment WHERE news = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = commentRepository.findComment(resultSet.getInt("id"));
                tagList.add(comment);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tagList;
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM tag_news WHERE id_news = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
