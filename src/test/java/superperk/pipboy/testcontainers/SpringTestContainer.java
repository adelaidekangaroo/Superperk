package superperk.pipboy.testcontainers;

public interface SpringTestContainer {
    void start();

    void setVersion(String version);

    void setProfiles(String[] profiles);
}