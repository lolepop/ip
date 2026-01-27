package storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileStorage implements Storage {
    // File file;
    private static final String FILE_PATH = "./tasks.txt";
    private String filePath; // so that this may be extended to use a config system if necessary

    public FileStorage() {
        this.filePath = FILE_PATH;
    }

    public void setData(Serializable obj) throws IOException {
        var outputStream = new FileOutputStream(this.filePath);
        var objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Object getData() throws IOException, ClassNotFoundException {
        var inputStream = new FileInputStream(this.filePath);
        var objectInputStream = new ObjectInputStream(inputStream);
        var obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }

}