package superperk.pipboy.testcontainers.v3.containers;

public interface Container {
    void setVersion(String version);

    void setReuse(boolean reuse);
}