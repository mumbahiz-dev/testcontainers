package com.testcontainers;

public record RequestDTO(
        String name,
        String nip,
        String gender,
        Integer age
) {
}
