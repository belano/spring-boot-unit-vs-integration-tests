package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    private final long id;
    private final String content;

    // @JsonCreator tells Jackson deserializer to use the designated constructor for deserialization
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Greeting(@JsonProperty("id") long id, @JsonProperty("content") String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
