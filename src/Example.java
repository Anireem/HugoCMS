import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Example {
    public static void main(String[] args) throws IOException {
        String test = "Hi \n My name is Richard \n I'm a photographer";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(test.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        while (bufferedInputStream.available() > 0)
            outputStream.write(bufferedInputStream.read());

        String result = new String(outputStream.toByteArray());
    }
}
