package superperk.pipboy.testcontainers.handlers.parameters;

sealed public interface ContainerSetting permits ContainerImageSetting, ContainerReuseSetting {
}