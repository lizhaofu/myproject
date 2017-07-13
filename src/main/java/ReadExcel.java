import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaofu on 2017/5/19.
 */
public class ReadExcel {

    //读取各省份的数据
    public void ReadData(String fileName, String outputPath) {

        try {
            InputStream is = new FileInputStream(fileName);//连续三行为读取Excel文件的初始化工作
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int nrows = xssfSheet.getLastRowNum();//
            List<String> excelList = new ArrayList<String>();
            File files = new File(outputPath);

            for (int i = 1; i < nrows; i++) {

                XSSFRow xssfRow = xssfSheet.getRow(i);//选中Excel表中的某一行
                XSSFCell cellProvince = xssfRow.getCell(8);//读取第一列的省份信息一下五行为从excel表中读取各列的数据
                if (cellProvince != null) {
                    excelList.add(cellProvince.toString().trim());

                }
            }
            if (!files.exists())
                files.mkdirs();
            BufferedWriter bw = null;

            try {

                File file = new File(outputPath + "/" + "newsg.txt");
                FileWriter fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
                for (int i = 0; i < excelList.size(); i++) {
                    bw.write(excelList.get(i) );
                    bw.newLine();
                }
                bw.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
