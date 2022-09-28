package superperk.pipboy.testcontainers.v2;

sealed interface ContainerParameter permits ContainerImageParameter, ContainerReuseParameter {
}

record ContainerImageParameter(String image) implements ContainerParameter {
}

record ContainerReuseParameter(boolean reuse) implements ContainerParameter {
}