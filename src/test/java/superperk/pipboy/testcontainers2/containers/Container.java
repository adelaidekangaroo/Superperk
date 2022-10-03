package superperk.pipboy.testcontainers2.containers;

public interface Container {
    void setVersion(String version);

    void setReuse(boolean reuse);
}