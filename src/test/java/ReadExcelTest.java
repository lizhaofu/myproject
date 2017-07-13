/**
 * Created by lizhaofu on 2017/5/19.
 */
public class ReadExcelTest {
    public static void main(String[] args) {
        String filePath ="data/inputg/newsg.xlsx";
        String outputPath = "data/inputg";
        ReadExcel re = new ReadExcel();
        re.ReadData(filePath,outputPath);
    }
}
