package com.application.backend;

import com.application.backend.repository.category.CategoryRepository;
import com.application.backend.repository.category.MySqlCategoryRepository;
import com.application.backend.repository.comment.CommentRepository;
import com.application.backend.repository.comment.MySqlCommentRepository;
import com.application.backend.repository.news.MySqlNewsRepository;
import com.application.backend.repository.news.NewsRepository;
import com.application.backend.repository.tag.MySqlTagRepository;
import com.application.backend.repository.tag.TagRepository;
import com.application.backend.repository.user.MySqlUserRepository;
import com.application.backend.repository.user.UserRepository;
import com.application.backend.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(NewsService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.application.backend");
    }
}
