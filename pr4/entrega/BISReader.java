import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BISReader extends InputStreamReader {

    public BISReader(InputStream is) {
        super(is);
    }

    @Override
    public int read(char[] cbuf, int offset, int length) throws IOException {
        int c = read();
        if (c == -1) {
            return -1; // EOF
        }
        else {
            cbuf[offset] = (char) c;
            return 1; // Read one character
        }
    }
}