package net.javacrumbs.jsonunit.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JsonUnitFactoryTest {

    @Test
    void shouldNavigateFromAssertjAssertionToJsonUnit() {
        assertThat("{}")
            .asInstanceOf(JsonUnitAssertFactory.JSON)
            .isObject()
            .isEmpty();
    }

    @Test
    void shouldNavigateFromAssertjAssertionToJsonUnitInObject() {
        record DummyResponse(String trackingId, String json) {}
        DummyResponse resp = new DummyResponse("abcd-0001", "{ \"foo\": \"bar\" }");

        assertThat(resp)
            .hasFieldOrPropertyWithValue("trackingId", "abcd-0001")
            .extracting("json").asInstanceOf(JsonUnitAssertFactory.JSON)
            .isObject().containsEntry("foo", "bar");
    }

}
