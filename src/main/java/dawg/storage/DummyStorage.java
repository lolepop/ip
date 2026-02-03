package dawg.storage;

import java.io.Serializable;

/**
 * No operation storage backend, used for testing or when we don't want the
 * object to be placed anywhere
 */
public class DummyStorage implements Storage {
    private Object storedData;

    @Override
    public void setData(Serializable obj) {
        this.storedData = obj;
    }

    @Override
    public Object getData() {
        return this.storedData;
    }

    public Object getRaw() {
        return this.storedData;
    }
}
