/**
 * Copyright 2009-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.jsonunit.core.internal;

import org.junit.Test;

import java.io.StringReader;
import java.util.Iterator;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonOrgNodeFactoryTest {

    private final JsonOrgNodeFactory factory = new JsonOrgNodeFactory();

    @Test
    public void shouldParseInt() {
        Node node = read("1");
        assertEquals(Node.NodeType.NUMBER, node.getNodeType());
        assertEquals(ONE, node.decimalValue());
    }

    @Test
    public void shouldParseDouble() {
        Node node = read("1.1");
        assertEquals(Node.NodeType.NUMBER, node.getNodeType());
        assertEquals(valueOf(1.1), node.decimalValue());
    }

    @Test
    public void shouldParseString() {
        Node node = read("\"Hi\"");
        assertEquals(Node.NodeType.STRING, node.getNodeType());
        assertEquals("Hi", node.asText());
    }

    @Test
    public void shouldParseBoolean() {
        Node node = read("true");
        assertEquals(Node.NodeType.BOOLEAN, node.getNodeType());
        assertEquals(true, node.asBoolean());
    }

    @Test
    public void shouldParseArray() {
        Node node = read("[1, \"two\"]]");
        assertEquals(Node.NodeType.ARRAY, node.getNodeType());

        assertEquals(ONE, node.element(0).decimalValue());
        assertEquals("two", node.element(1).asText());

        Iterator<Node> elements = node.arrayElements();
        assertTrue(elements.hasNext());
        assertEquals(ONE, elements.next().decimalValue());

        assertTrue(elements.hasNext());
        assertEquals("two", elements.next().asText());

        assertFalse(elements.hasNext());
    }

    @Test
    public void shouldParseObject() {
        Node node = read("{\"root\":{\"a\": 1, \"b\": null}}");
        assertEquals(Node.NodeType.OBJECT, node.getNodeType());

        Node root = node.get("root");
        assertEquals(Node.NodeType.OBJECT, root.getNodeType());

        Node a = root.get("a");
        assertEquals(Node.NodeType.NUMBER, a.getNodeType());
        assertEquals(ONE, a.decimalValue());

        Node b = root.get("b");
        assertEquals(Node.NodeType.NULL, b.getNodeType());
        assertEquals(true, b.isNull());
    }


    private Node read(String value) {
        return factory.readValue(new StringReader(value), "label", false);
    }

}