package storage;

import java.io.IOException;
import java.io.Serializable;

public interface Storage {
    /**
     * Overrides object within the target file (if it exists)
     * 
     * @param obj object to be stored
     * @throws IOException if failed to open/write to file
     */
    public void setData(Serializable obj) throws IOException;

    /**
     * Reads object within target file
     * 
     * @return deserialised data from file
     * @throws IOException            if failed to open/read file
     * @throws ClassNotFoundException only potentially happens with files from a
     *                                previous incompatible version
     */
    public Object getData() throws IOException, ClassNotFoundException;
}
