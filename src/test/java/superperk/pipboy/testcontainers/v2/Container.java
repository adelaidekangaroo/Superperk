package superperk.pipboy.testcontainers.v2;

public interface Container {
    void setVersion(String version);

    void setProfiles(String[] profiles);
}