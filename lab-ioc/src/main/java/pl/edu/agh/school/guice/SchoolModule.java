package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import pl.edu.agh.logger.ConsoleMessageSerializer;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IPersistenceManager.class).to(SerializablePersistenceManager.class);
        bind(String.class).annotatedWith(Names.named("teachersStorageFileName")).toInstance("guice-teachers.dat");
        bind(String.class).annotatedWith(Names.named("classStorageFileName")).toInstance("guice-classes.dat");
    }

    @Provides
    @Singleton
    Logger provideLogger() {
        Logger logger = new Logger();
        logger.registerSerializer(new ConsoleMessageSerializer());
        logger.registerSerializer(new FileMessageSerializer("logfile.log"));
        return logger;
    }
}
