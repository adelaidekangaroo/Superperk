package superperk.pipboy.testcontainers.containers;

import org.testcontainers.containers.GenericContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class AbstractGenericContainer implements Container {
    protected String version = "postgres:latest";
    protected boolean reuse = true;

    protected abstract GenericContainer getSourceContainer();

    @PostConstruct
    public abstract void init();

    @PreDestroy
    public void preDestroy() {
        if (!reuse) getSourceContainer().stop();
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setReuse(boolean reuse) {
        this.reuse = reuse;
    }
}