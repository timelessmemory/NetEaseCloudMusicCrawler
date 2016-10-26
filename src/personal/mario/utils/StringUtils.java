package personal.mario.utils;

public class StringUtils {
	public static String removeWs(String string) {
		return string.replaceAll("\\s*", "");
	}
	
	//1. 文件名不能包含/等特殊字符
	//2. 文件名长度有限制
	//3. 去空格
	public static String dealWithFilename(String filename) {
		return removeWs(filename).replace("/", "-");
	}
}
