
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DimenTool {

    private final static String rootPath = "C:\\Users\\Administrator\\Desktop\\layoutroot\\values-{0}x{1}\\";

    private final static float dw = 480f;
    private final static float dh = 800f;

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    private final static int[] otherPxX = new int[]{};
    private final static int[] otherPxY = new int[]{};

    public static void main(String[] args) {
//        makeString(320, 480);
        makeString(480, 800);
        makeString(480, 640);
//        makeString(480, 854);
//        makeString(540, 960);
//        makeString(600, 1024);
//        makeString(720, 1184);
//        makeString(720, 1196);
//        makeString(720, 1280);
//        makeString(768, 1024);
//        makeString(800, 1280);
//        makeString(1080, 1812);
        makeString(1080, 1920);
//        makeString(1440, 2560);
    }

    public static void makeString(int w, int h) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");
        float cellw = w / dw;
        int tempxCount = (int) (dw * 2 / 3);
        for (int i = 1; i < tempxCount + 1; i++) {
            sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }

        for (int i = 0; i < otherPxX.length; i++) {
            int temp = otherPxX[i];
            if (temp < dw && temp > tempxCount) {
                sb.append(WTemplate.replace("{0}", temp + "").replace("{1}",
                        change(cellw * temp) + ""));
            }
        }

//        sb.append(WTemplate.replace("{0}", "320").replace("{1}", w + ""));
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");
        float cellh = h / dh;
        int tempyCount = (int) (dh / 3);
        for (int i = 1; i < tempyCount + 1; i++) {
            sb2.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }

        for (int i = 0; i < otherPxY.length; i++) {
            int temp = otherPxY[i];
            if (temp <= dh && temp > tempyCount) {
                sb.append(WTemplate.replace("{0}", temp + "").replace("{1}",
                        change(cellw * temp) + ""));
            }
        }

//        sb2.append(HTemplate.replace("{0}", "480").replace("{1}", h + ""));
        sb2.append("</resources>");

        String path = rootPath.replace("{0}", w + "").replace("{1}", h + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File layxFile = new File(path + "lay_x.xml");
        File layyFile = new File(path + "lay_y.xml");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sb2.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (pw != null)
                pw.close();
        }

    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }
}
