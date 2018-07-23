
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DimenTool2 {

    private final static String WTemplate = "<dimen name=\"px{0}\">{1}px</dimen>\n";

    public static void main(String[] args) {
        makeString(0, 1350);
    }

    public static void makeString(int start, int end) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");
        for (int i = start; i < end; i++) {
            sb.append(WTemplate.replace("{0}", i + "").
                    replace("{1}", i + ""));
        }

        sb.append("</resources>");

        File rootFile = new File("");//设定为当前文件夹

        File layxFile = new File(rootFile.getParentFile(), "lay_px.xml");
        if (!layxFile.exists())
            layxFile.mkdirs();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null)
                pw.close();
        }

    }
}
