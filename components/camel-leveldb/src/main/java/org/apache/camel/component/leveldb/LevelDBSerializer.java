package org.apache.camel.component.leveldb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface LevelDBSerializer {

    default byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
             out.writeObject(object);
        }

        return baos.toByteArray();
    }

    default Object deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

        try (ObjectInputStream in = new ObjectInputStream(bais)) {
            return in.readObject();
        }
    }
}
