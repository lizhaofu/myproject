import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.*;

/**
 * Created by lizhaofu on 2017/4/20.
 */
public class AbstractTxtTransform {

    public enum TxtTypeEnum {
        Paper, Paptent;
    }

    public  List readPaperTxt(String path_src, TxtTypeEnum t) {
        Map<String, String> topicMap = new HashMap<String, String>();
        List<Paper> paperList = new ArrayList<Paper>();
        String lineCurrent = null;
        File file = new File(path_src);
        File[] tempList = file.listFiles();
        //file.listFiles();
        for (int j = 0; j < tempList.length; j++) {
            if (!tempList[j].exists()) {
                System.out.println("file " + file + " is not existed, exit");
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempList[j]), "UTF-8"));
                lineCurrent = br.readLine();
                Paper paper = new Paper();
                String str = null;
                if (t == TxtTypeEnum.Paper) {
                    Boolean flag = false;
                    Boolean flagAll = false;

                    while (lineCurrent != null) {
                        String[] wordFlag = lineCurrent.split(" ");
                        if (wordFlag.length == 0) {
                            lineCurrent = br.readLine();
                            continue;
                        }
                        if (wordFlag[0].equals("PT")) {
                            paper = new Paper();
                            str = null;
                            flagAll = true;
                        }
                        if (flagAll) {
                            paper.setPaperAll(paper.getPaperAll() + lineCurrent + "\n");
                        }

                        if (wordFlag[0].equals("AB")) {//read paper file
                            flag = true;
                            paper.setAbstracts(lineCurrent.substring(3).trim());
                        } else if (flag && wordFlag[0].equals("")) {
                            paper.setAbstracts(paper.getAbstracts() + lineCurrent.substring(3).trim());
                        } else if (flag && !wordFlag[0].equals("")) {
                            flag = false;
                        }
                        if (wordFlag[0].equals("UT")) {
                            paper.setPaperNum(lineCurrent.substring(7).trim());
                        }
                        if (wordFlag[0].equals("ER")) {
                            paperList.add(paper);
//                            flagAll = false;
//                            CoreNLP(paper, absDirs+"/abstract");
//                            topicMap = readLabel(labelPath);
//                            for (Map.Entry<String, String> entry : topicMap.entrySet()) {
//                                if (entry.getKey().equals(paper.getPaperNum())) {
//                                    writeAbstractAllTxt(paper, absDirs + "/paper/" + entry.getValue());
//                                    writeAbstractTxt(paper, absDirs + "/paperabstract/" + entry.getValue());
//                                }
//
//                            }


                        }
                        lineCurrent = br.readLine();
                    }
                }
                if (t == TxtTypeEnum.Paptent) {
                    Boolean flag = false;
                    Boolean flagAll = false;
                    while (lineCurrent != null) {
                        String[] wordFlag = lineCurrent.split("  ");
                        if (wordFlag.length == 0) {
                            lineCurrent = br.readLine();
                            continue;
                        }
                        if (wordFlag[0].equals("pn")) {
                            paper = new Paper();
                            flagAll = true;
                            paper.setPaperNum(lineCurrent.substring(5).trim());
                        }
                        if (flagAll) {
                            paper.setPaperAll(paper.getPaperAll() + lineCurrent + "\n");
                        }
                        if (wordFlag[0].equals("abd")) {//read paper file
                            flag = true;
                            paper.setAbstracts(lineCurrent.substring(6).trim());
                        } else if (flag && wordFlag[0].equals("")) {
                            paper.setAbstracts(paper.getAbstracts() + lineCurrent.substring(6).trim());
                        } else if (flag && !wordFlag[0].equals("")) {
                            flag = false;
                        }
                        if (wordFlag[0].equals("re")){
                            paperList.add(paper);
//                            flagAll = false;
//                            CoreNLP(paper, absDirs+"/abstract");
//                            topicMap = readLabel(labelPath);
//                            for (Map.Entry<String, String> entry : topicMap.entrySet()) {
//                                if (entry.getKey().equals(paper.getPaperNum())) {
//                                    writeAbstractAllTxt(paper, absDirs + "/patent/" + entry.getValue());
//                                    writeAbstractTxt(paper, absDirs + "/patentabstract/" + entry.getValue());
//                                }
//
//                            }
                        }

                        lineCurrent = br.readLine();
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paperList;
    }

    public  Map readLabel(String inputPath) {
        Map<String, String> topicMap = new HashMap<String, String>();
        String lineCurrent = null;
        File file = new File(inputPath);
        File[] tempList = file.listFiles();
        //file.listFiles();
        for (int j = 0; j < tempList.length; j++) {
            if (!tempList[j].exists()) {
                System.out.println("file " + file + " is not existed, exit");
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempList[j]), "UTF-8"));
                lineCurrent = br.readLine();
                while (lineCurrent != null){
                    String[] str = lineCurrent.split(" ");
                    topicMap.put(str[0], str[1]);
                    lineCurrent = br.readLine();

                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return topicMap;
    }



    public  void abstractWriter(String[] abst, String absDirs) {
        Paper paper = new Paper();
        for (int i = 0; i < abst.length; i++) {
            paper = new Paper();
            paper.setAbstracts(abst[i]);
            paper.setPaperNum(Integer.toString(i));
            writeAbstractTxt(paper, absDirs);
        }
    }

    public  List<Paper> CoreNLP(List<Paper> paperList,String outputPath) {
        List<Paper> paperOutList = new ArrayList<Paper>();
        for (int i = 0; i < paperList.size(); i++) {
            String lemma = null;
            String lemmaString = "";
            String pos = null;
            Paper paper = new Paper();
            List<String> abstractList = new ArrayList<String>();
            String[] abstractInitial = paperList.get(i).getAbstracts()
                    .replaceAll("\\(i+\\)", "")
                    .replaceAll("[\\d+]", "")
                    .replaceAll("[\\p{Punct}]", " ")
                    .replaceAll("\\t", " ")
                    .split(" ");
            Boolean flag = false;
            for (int j = 0; j < abstractInitial.length; j++) {
                abstractList.add(abstractInitial[j].toLowerCase());
                Properties props = new Properties();
                props.put("annotators", "tokenize, ssplit, pos, lemma");    // 七种Annotators
                StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // 依次处理
                Annotation document = new Annotation(abstractInitial[j].toLowerCase());    // 利用text创建一个空的Annotation
                pipeline.annotate(document);
                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

                for (CoreMap sentence : sentences) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String word = token.get(CoreAnnotations.TextAnnotation.class);            // 获取分词
                        pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);     // 获取词性标注
                        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);    // 获取命名实体识别结果
                        lemma = token.get(CoreAnnotations.LemmaAnnotation.class);          // 获取词形还原结果
                        if (StopWords.stopwords.contains(lemma) || lemma.length()<3)
                            continue;
                        if (flag && pos.equals("NN")){
                            lemmaString += "$$"+lemma;
                            flag =false;
                        } else
                            lemmaString += " "+lemma;

                        if (flag)
                            flag=false;
                        if (pos.equals("JJ"))
                            flag = true;
                    }
                }
            }
            paper.setAbstracts(lemmaString.trim());
            paper.setPaperNum(paperList.get(i).getPaperNum());
            paperOutList.add(paper);
        }
//        for (Paper p :paperOutList){
//            writeAbstractTxt(p,outputPath+"/abstract");
//
//        }
        return paperOutList;

    }


    public  void writeAbstractAllTxt(Paper paper, String outputPath) {
        File files = new File(outputPath);
        if (!files.exists())
            files.mkdirs();
        try {
            BufferedWriter bw = null;

            File file = new File(outputPath + "/" + paper.getPaperNum() + ".txt");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(paper.getPaperAll());
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void writeAbstractTxt(Paper paper, String outputPath) {
        File files = new File(outputPath);
        if (!files.exists())
            files.mkdirs();
        try {
            BufferedWriter bw = null;

            File file = new File(outputPath + "/" + paper.getPaperNum() + ".txt");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(paper.getAbstracts()+"\n");
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void treeHyperParamWriter(String absDir) {
        String[] tree_hyperparams = {"DEFAULT_ 0.01\nNL_ 0.01\nML_ 100\nCL_ 0.00000000001"};
        File files = new File(absDir);
        if (!files.exists())
            files.mkdirs();
        for (File file : files.listFiles())
            file.delete();
        try {
            BufferedWriter bw = null;
            for (int i = 0; i < tree_hyperparams.length; i++) {
                bw = new BufferedWriter(new FileWriter(new File(absDir + "/" + "tree_hyperparams.txt")));
                bw.write(tree_hyperparams[i]);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  void backupWriter(List<Paper> paperList,String absDirs,String labelPath){
        Map<String, String> topicMap = readLabel(labelPath);
        for (Paper paper:paperList) {
            for (Map.Entry<String, String> entry : topicMap.entrySet()) {
                if (entry.getKey().equals(paper.getPaperNum())) {
                    writeAbstractAllTxt(paper, absDirs + "/complete/" + entry.getValue());
                    writeAbstractTxt(paper, absDirs + "/originalabstract/" + entry.getValue());
                }

            }

        }

    }

}
