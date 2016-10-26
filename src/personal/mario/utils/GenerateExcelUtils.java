package personal.mario.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import personal.mario.bean.MusicComment;
import personal.mario.bean.MusicCommentMessage;

public class GenerateExcelUtils {
	private static Logger logger = Logger.getLogger(GenerateExcelUtils.class);
	
	public static HSSFSheet generateCommentMessageExcelInit(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet("歌曲信息");
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
	
	public static void generateCommentMessageExcelProcess(HSSFWorkbook workbook, HSSFSheet sheet, MusicCommentMessage musicCommentMessage, int rowNum) {
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
	}
	
	public static void generateCommentMessageExcelWrite(HSSFWorkbook workbook) throws IOException {
		FileOutputStream fos = new FileOutputStream(Constants.COMMENT_MESSAGE_PATH);
        workbook.write(fos);
        fos.close();
	}
	
	public static void generateCommentsExcel(MusicCommentMessage musicCommentMessage) throws IOException {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("歌曲评论");
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
        cellHead.setCellValue("歌名");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(1);
        cellHead.setCellValue("评论类型");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(2);
        cellHead.setCellValue("评论用户昵称");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(3);
        cellHead.setCellValue("评论时间");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(4);
        cellHead.setCellValue("评论内容");
        cellHead.setCellStyle(style);
        
        cellHead = rowHead.createCell(5);
        cellHead.setCellValue("获赞数");
        cellHead.setCellStyle(style);
        
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        List<MusicComment> comments = musicCommentMessage.getComments();
        
    	for (int i = 0; i < comments.size(); i++) {
    		MusicComment comment = comments.get(i);
    		HSSFRow row = sheet.createRow(i + 1);
    		    	
	        HSSFCell cell = row.createCell(0);
	        cell.setCellValue(musicCommentMessage.getSongTitle());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(1);
	        cell.setCellValue(comment.getType());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(comment.getNickname());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(comment.getCommentDate());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(comment.getContent());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(comment.getAppreciation());
	        cell.setCellStyle(cellStyle);
    	}
        
    	String path = Constants.COMMENTS_PATH + StringUtils.dealWithFilename(musicCommentMessage.getSongTitle()) + Constants.COMMENTS_SUFFIX;
    	logger.info(path);
        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
	}
	
	public static void generateTopMusicExcel(List<MusicCommentMessage> ms) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Top" + ms.size() + "歌曲");
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
        
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
    	for (int i = 0; i < ms.size(); i++) {
    		MusicCommentMessage mcm = ms.get(i);
    		HSSFRow row = sheet.createRow(i + 1);
    		    	
	        HSSFCell cell = row.createCell(0);
	        cell.setCellValue(mcm.getSongTitle());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(1);
	        cell.setCellValue(mcm.getSongUrl());
	        cell.setCellStyle(cellStyle);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(mcm.getCommentCount());
	        cell.setCellStyle(cellStyle);
    	}
        
        FileOutputStream fos = new FileOutputStream(Constants.TOP_MUSIC_PATH);
        workbook.write(fos);
        fos.close();
	}
}
