package yin.style.swdemintools.java;

import java.io.File;


public class DimenGeneratorMain {

    /**
     * 设计稿尺寸(将自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 750;

//    /**
//     * 设计稿的高度  （将自己设计师的设计稿的高度填入）
//     */
//    private static final int DESIGN_HEIGHT = 667;

    public static void main(String[] args) {
//        int smallest = DESIGN_WIDTH>DESIGN_HEIGHT? DESIGN_HEIGHT:DESIGN_WIDTH;  //     求得最小宽度
        int smallest = DESIGN_WIDTH;
        DimenTypes[] values = DimenTypes.values();
        File file = new File(System.getProperty("user.dir"), "swdemin-tools/files");
        System.out.printf("file:" + file.getPath());

        for (DimenTypes value : values) {
            MakeUtils.makeAll(smallest, value, file.getAbsolutePath());
        }
        System.out.printf("file:ok");
    }

}
