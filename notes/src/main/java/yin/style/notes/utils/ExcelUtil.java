package yin.style.notes.utils;

import android.content.Context;
import android.util.SparseArray;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import yin.style.notes.MyApp;
import yin.style.notes.model.DetailsBean;
import yin.style.notes.model.ProjectBean;

/**
 * Created by Chne on 2017/8/17.
 */

public class ExcelUtil {
    // excle表格的后缀
    public static final String SUFFIX = ".xls";
    private static int rowNum = 0;//列数
    private static int cellNum = 0;//行数

    public static void writeExecleToFile(Context context, ProjectBean bean, List<DetailsBean> list) {
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        SparseArray<CellStyle> cellStyles = creatCellStyles(workbook);
        //创建execl中的一个表
        Sheet sheet = workbook.createSheet();
        setSheet(sheet);
        rowNum = 0;

        //创建一行
        Row rowLine = sheet.createRow(rowNum++);
        // 设置第一行：48pt的字体的内容
        rowLine.setHeightInPoints(40);
        //创建第一行中第一单元格
        Cell cell = rowLine.createCell(0);
        cell.setCellValue(bean.getProjects());

        cell.setCellStyle(cellStyles.get(0));
        mergingCells(sheet, workbook, new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 7));


        //创建第二行
        cellNum = 0;
        Row secondRow = sheet.createRow(rowNum++);
        secondRow.setHeightInPoints(25);
        Cell cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("甲方：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(bean.getFirstParty());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("预算：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(bean.getBudget());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("收支：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(bean.getInCome() + "/" + bean.getExpend());
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("当前状态：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(ProjectBean.getFlagText(bean.getFlag()));
        cell2.setCellStyle(cellStyles.get(3));

        //下一行
        cellNum = 0;
        secondRow = sheet.createRow(rowNum++);
        secondRow.setHeightInPoints(25);

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("合同：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(DateUtil.date2Str(bean.getCreateTime(), "yyyy-MM-dd"));
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("开工：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(DateUtil.date2Str(bean.getStartTime(), "yyyy-MM-dd"));
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("交工：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(DateUtil.date2Str(bean.getEndTime(), "yyyy-MM-dd"));
        cell2.setCellStyle(cellStyles.get(3));

        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("打印时间：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(DateUtil.date2Str(new Date(), "yyyy-MM-dd\nHH:mm:ss"));
        cell2.setCellStyle(cellStyles.get(3));

        //下一行
        cellNum = 0;
        secondRow = sheet.createRow(rowNum++);
        secondRow.setHeightInPoints(25);
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue("备注：");
        cell2.setCellStyle(cellStyles.get(2));
        cell2 = secondRow.createCell(cellNum++);
        cell2.setCellValue(bean.getRemarks());
        cell2.setCellStyle(cellStyles.get(3));
        mergingCells(sheet, workbook, new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 7));

        //创建第三行
        sheet.createRow(rowNum++);//空一行
        Row threedRow = sheet.createRow(rowNum++);
        String values[] = {"", "明细内容", "金额", "类别", "工人", "日期", "备注"};
        threedRow.setHeightInPoints(25);
        for (int i = 0; i < values.length; ++i) {
            Cell cell1 = threedRow.createCell(i);
            cell1.setCellValue(values[i]);
            cell1.setCellStyle(cellStyles.get(2));
            if (i == values.length - 1)
                mergingCells(sheet, workbook, new CellRangeAddress(rowNum - 1, rowNum - 1, i, i + 1));
        }

        //创建第四行
//        fourRow.setHeightInPoints(150);
        for (int i = 0; i < list.size(); ++i) {
            Row fourRow = sheet.createRow(rowNum++);
            fourRow.setHeightInPoints(25);
            getValues(values, 1 + i, list.get(i));
            for (int j = 0; j < values.length; ++j) {
                Cell cell1 = fourRow.createCell(j);
                cell1.setCellValue(values[j]);
                cell1.setCellStyle(cellStyles.get(3));
                if (j == values.length - 1)
                    mergingCells(sheet, workbook, new CellRangeAddress(rowNum - 1, rowNum - 1, j, j + 1));
            }
        }

        setBorder(sheet);
        writeFile(workbook, getFile(context, bean));
    }

    private static void getValues(String[] values, int i, DetailsBean detailsBean) {
        values[0] = i + "";
        values[1] = detailsBean.getContent();
        values[2] = detailsBean.getMoney() + "";
        values[3] = DetailsBean.getFlagText(detailsBean.getFlag());
        values[4] = detailsBean.getWorker();
        values[5] = DateUtil.date2Str(detailsBean.getTime(), "yyyy-MM-dd");
        values[6] = detailsBean.getRemarks();
    }


    /**
     * 获取指定文件
     *
     * @return
     */
    public static String getFile(Context context, ProjectBean bean) {
        String fileName = DateUtil.date2Str(bean.getCreateTime(), "yyyy-MM-dd") +
                "_" + bean.getProjects() + "_" +
                DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
        return MyApp.getExcelPath(context) + File.separator + fileName + SUFFIX;
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
    private static void mergingCells(Sheet sheet, Workbook workbook, CellRangeAddress cellRangeAddress) {
        sheet.addMergedRegion(cellRangeAddress);

        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, workbook); // 下边框
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, workbook); // 左边框
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, workbook); // 有边框
        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, workbook); // 上边框</strong></span>
    }

    /**
     * 设置Sheet表
     *
     * @param sheet
     */
    private static void setSheet(Sheet sheet) {
        // turn off gridlines（关闭网络线）
//        sheet.setDisplayGridlines(true);
//        sheet.setPrintGridlines(true);
//        sheet.setFitToPage(true);
//        sheet.setHorizontallyCenter(true);
//
//        PrintSetup printSetup = sheet.getPrintSetup();
//        printSetup.setLandscape(true);
//        //只对HSSF需要用到的
//        sheet.setAutobreaks(true);
//        printSetup.setFitHeight((short) 1);
//        printSetup.setFitWidth((short) 1);
        sheet.setColumnWidth(0, 3 * 512);
        sheet.setColumnWidth(1, 7 * 512);
        sheet.setColumnWidth(2, 4 * 512);
        sheet.setColumnWidth(3, 7 * 512);
        sheet.setColumnWidth(4, 4 * 512);
        sheet.setColumnWidth(5, 8 * 512);
        sheet.setColumnWidth(6, 5 * 512);
        sheet.setColumnWidth(7, 7 * 512);

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
        CellStyle cellStyle3 = createBorderedStyle(workbook);
//        CellStyle cellStyle3 = workbook.createCellStyle();
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
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //重新设置单元格的四边颜色
        short thin = CellStyle.BORDER_THIN;
        short blackColor_Index = IndexedColors.BLACK.getIndex();
        cellStyle.setBottomBorderColor(blackColor_Index);
        cellStyle.setBorderBottom(thin);
        cellStyle.setTopBorderColor(blackColor_Index);
        cellStyle.setBorderTop(thin);
        cellStyle.setRightBorderColor(blackColor_Index);
        cellStyle.setBorderRight(thin);
        cellStyle.setLeftBorderColor(blackColor_Index);
        cellStyle.setBorderLeft(thin);
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

    /**
     * 设置边框
     *
     * @param sheet
     * @param workbook
     */
    public static void setBorder(Sheet sheet) {
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                CellStyle cellStyle = cell.getCellStyle();
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                cell.setCellStyle(cellStyle);
            }
        }


    }
}
