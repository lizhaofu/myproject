import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        String inputPath = "data/paper";
        String outputPath = "data/output4";
        String labelPath = "data/topic";
        AbstractTxtTransform att = new AbstractTxtTransform();
        AbstractTxtTransform.TxtTypeEnum tt = AbstractTxtTransform.TxtTypeEnum.Paper;
        List<Paper> at = att.readPaperTxt(inputPath, tt);
        List<Paper> list = att.CoreNLP(at,outputPath);
//        att.backupWriter(at,outputPath,labelPath);
        File files = new File(outputPath);
        if (!files.exists())
            files.mkdirs();


        try {
            BufferedWriter bw = null;
            File file = new File(outputPath + "/" + "logistic.txt");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (int i = 0; i < list.size(); i++) {

                bw.write(list.get(i).getAbstracts() + "\n");
            }
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
