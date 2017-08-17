package yin.style.notes.utils;

import android.content.Context;
import android.util.SparseArray;

import com.jskingen.baselib.utils.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import yin.style.notes.model.DetailsBean;
import yin.style.notes.model.ProjectBean;

/**
 * Created by Chne on 2017/8/17.
 */

public class ExcelUtil {
    // excle表格的后缀
    private static final String SUFFIX = ".xls";

    private static Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

    public static void writeExecleToFile(Context context, ProjectBean bean, List<DetailsBean> list) {
        //创建工作簿
        Workbook workbook = createWorkbook();
        SparseArray<CellStyle> cellStyles = creatCellStyles(workbook);
        //创建execl中的一个表
        Sheet sheet = workbook.createSheet();
        // setSheet(sheet);

        //创建第一行
        Row headerRow = sheet.createRow(0);
        // 设置第一行：48pt的字体的内容
        headerRow.setHeightInPoints(60);
        //创建第一行中第一单元格
        Cell cell = headerRow.createCell(0);
        cell.setCellValue(bean.getProjects());//***---***
        cell.setCellStyle(cellStyles.get(0));
        mergingCells(sheet, CellRangeAddress.valueOf("$A$1:$F$1"));


        //创建第二行
        Row secondRow = sheet.createRow(1);
        secondRow.setHeightInPoints(40);
//        for (int i = 0; i < 2; ++i) {
//            mergingCells(sheet, new CellRangeAddress(1, 1, i * 3, i * 3 + 2));
//            Cell cell1 = secondRow.createCell(i * 3);
//            cell1.setCellValue(SENCOND_VALUES[i]);
//            cell1.setCellStyle(cellStyles.get(1));
//        }
        Cell cell2 = secondRow.createCell(0);
        cell2.setCellValue("甲方：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(0);
        cell2.setCellValue(bean.getFirstParty());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(0);
        cell2.setCellValue("预算：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(0);
        cell2.setCellValue(bean.getBudget());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(0);
        cell2.setCellValue("收支：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(0);
        cell2.setCellValue(bean.getInCome() + "/" + bean.getExpend());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(0);
        cell2.setCellValue("当前状态：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(0);
        cell2.setCellValue(ProjectBean.getFlagText(bean.getFlag()));
        cell2.setCellStyle(cellStyles.get(3));


//        //创建第三行
//        Row threedRow = sheet.createRow(2);
//        threedRow.setHeightInPoints(40);
//        for (int i = 0; i < 2; ++i) {
//            for (int j = 0; j < 3; ++j) {
//                Cell cell1 = threedRow.createCell(i * 3 + j);
//                cell1.setCellValue(THREE_VALUES[j]);
//                cell1.setCellStyle(cellStyles.get(2));
//            }
//        }
//
//
//        //创建第四行
//        Row fourRow = sheet.createRow(3);
//        fourRow.setHeightInPoints(150);
//        for (int i = 0; i < FOUR_VALUES.length; ++i) {
//            for (int j = 0; j < FOUR_VALUES[i].length; ++j) {
//                Cell cell1 = fourRow.createCell(i * 3 + j);
//                cell1.setCellValue(FOUR_VALUES[i][j]);
//                cell1.setCellStyle(cellStyles.get(3));
//            }
//        }

        writeFile(workbook, getFile(context));
    }


    /**
     * 获取指定文件
     *
     * @return
     */
    public static String getFile(Context context) {
        File dirFile = FileUtils.getDownloadFile(context);
        if (dirFile != null && !dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile.getAbsolutePath() + File.separator + new Date().getTime() + SUFFIX;
    }

    /**
     * 合并cell单元格
     * <p>
     * CellRangeAddress构造器中参数：
     * 参数1：first row(0-based)
     * 参数2：last row(0-based)
     * 参数3：first column(0-based)
     * 参数4：last column(0-based)
     *
     * @param sheet
     * @param cellRangeAddress
     */
    private static void mergingCells(Sheet sheet, CellRangeAddress cellRangeAddress) {
        sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 设置Sheet表
     *
     * @param sheet
     */
    private static void setSheet(Sheet sheet) {
        // turn off gridlines（关闭网络线）
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        //只对HSSF需要用到的
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);
    }

    /**
     * 创建各种不同单元格特征,根据个人需求不同而定 。
     *
     * @return
     */
    private static SparseArray<CellStyle> creatCellStyles(Workbook workbook) {
        SparseArray<CellStyle> array = new SparseArray<>();
        //第一行的单元格特征
        CellStyle cellStyle0 = createBorderedStyle(workbook);
        Font font0 = creatFont(workbook);
        font0.setFontHeightInPoints((short) 14);
        font0.setBold(true);
        cellStyle0.setFont(font0);
        array.put(0, cellStyle0);

        //第二行的单元格特征
        CellStyle cellStyle1 = createBorderedStyle(workbook);
        Font font1 = creatFont(workbook);
        font1.setBold(true);
        cellStyle1.setFont(font1);
        array.put(1, cellStyle1);
        //第三行的单元格特征
        array.put(2, cellStyle1);
        //第四行的单元格特征

        CellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setAlignment(HorizontalAlignment.CENTER);
        cellStyle3.setVerticalAlignment(VerticalAlignment.TOP);
        //使用换行符，需要开启wrap. 换行的文本用“\n”连接
        cellStyle3.setWrapText(true);
        array.put(3, cellStyle3);
        return array;
    }

    /**
     * 设置表格的内容到四边的距离,表格四边的颜色
     * <p>
     * 对齐方式：
     * 水平： setAlignment();
     * 竖直：setVerticalAlignment()
     * <p>
     * 四边颜色：
     * 底边：  cellStyle.setBottomBorderColor()
     * <p>
     * 四边边距：
     * <p>
     * 填充：
     * <p>
     * 缩进一个字符：
     * setIndention()
     * <p>
     * 内容类型：
     * setDataFormat()
     *
     * @param workbook
     * @return
     */
    private static CellStyle createBorderedStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/*     //重新设置单元格的四边颜色
    BorderStyle thin=BorderStyle.THIN;
    short blackColor_Index=IndexedColors.BLACK.getIndex();
    cellStyle.setBottomBorderColor(blackColor_Index);
    cellStyle.setBorderBottom(thin);
    cellStyle.setTopBorderColor(blackColor_Index);
    cellStyle.setBorderTop(thin);
    cellStyle.setRightBorderColor(blackColor_Index);
    cellStyle.setBorderRight(thin);
    cellStyle.setLeftBorderColor(blackColor_Index);
    cellStyle.setBorderLeft(thin);*/
        return cellStyle;
    }

    /**
     * 创建Font
     * <p>
     * 注意点：excle工作簿中字体最大限制为32767，应该重用字体，而不是为每个单元格都创建字体。
     * <p>
     * 其API:
     * setBold():设置粗体
     * setFontHeightInPoints():设置字体的点数
     * setColor():设置字体颜色
     * setItalic():设置斜体
     *
     * @param workbook
     * @return
     */
    private static Font creatFont(Workbook workbook) {
        Font font = workbook.createFont();
        return font;
    }

    /**
     * 将Excle表格写入文件中
     *
     * @param workbook
     * @param fileName
     */
    private static void writeFile(Workbook workbook, String fileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
