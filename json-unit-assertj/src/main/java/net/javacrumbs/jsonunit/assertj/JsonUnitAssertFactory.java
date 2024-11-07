package net.javacrumbs.jsonunit.assertj;

import org.assertj.core.api.InstanceOfAssertFactory;

public class JsonUnitAssertFactory extends InstanceOfAssertFactory<Object, JsonAssert> {

    public static final JsonUnitAssertFactory JSON = new JsonUnitAssertFactory();

    private JsonUnitAssertFactory() {
        super(Object.class, JsonAssertions::assertThatJson);
    }

}

