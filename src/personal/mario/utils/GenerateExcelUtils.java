package personal.mario.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import personal.mario.bean.MusicCommentMessage;

public class GenerateExcelUtils {
	public static HSSFSheet generateCommentMessageInit(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet("歌曲评论信息");
        sheet.setDefaultColumnWidth(15);
        
        HSSFRow rowHead = sheet.createRow(0);
        
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.LIGHT_BLUE.index);
        font.setFontHeightInPoints((short) 8);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        HSSFCell cellHead = rowHead.createCell(0);
        cellHead.setCellValue("歌曲信息");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(1);
        cellHead.setCellValue("歌曲链接");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(2);
        cellHead.setCellValue("评论数");
        cellHead.setCellStyle(style);
        
        return sheet;
	}
	
	public static void generateCommentMessageProcess(HSSFWorkbook workbook, HSSFSheet sheet, MusicCommentMessage musicCommentMessage, int rowNum) throws IOException {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
    	HSSFRow row = sheet.createRow(rowNum + 1);
    	
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(musicCommentMessage.getSongTitle());
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell(1);
        cell.setCellValue(musicCommentMessage.getSongUrl());
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell(2);
        cell.setCellValue(musicCommentMessage.getCommentCount());
        cell.setCellStyle(cellStyle);
        
        FileOutputStream fos = new FileOutputStream(Constants.COMMENT_MESSAGE_PATH);
        workbook.write(fos);
        fos.close();
	}
	
	private static void generateComments() {
	}
	
	private static void generateTopMusic() {
	}
}
