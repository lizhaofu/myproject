/**
 * Created by lizhaofu on 2017/4/25.
 */
public class Paper {
    private String abstracts;
    private String paperNum;
    private String paperAll;

    public String getPaperAll() {
        return paperAll;
    }

    public void setPaperAll(String paperAll) {
        this.paperAll = paperAll;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(String paperNum) {
        this.paperNum = paperNum;
    }

    public Paper(){
        abstracts = "";
        paperNum = "";
        paperAll = "";
    }
}
