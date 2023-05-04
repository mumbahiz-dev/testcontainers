package com.testcontainers;

public record ResponseDTO(
        Long id,
        String name,
        String nip,
        String gender,
        Integer age
) {
}
