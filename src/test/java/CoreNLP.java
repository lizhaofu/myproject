/**
 * Created by lizhaofu on 2017/4/26.
 */

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;

// import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
//Xudandan

public class CoreNLP {
    public static void main(String[] args) {
        /**
         * 创建一个StanfordCoreNLP object
         * tokenize(分词)、ssplit(断句)、 pos(词性标注)、lemma(词形还原)、
         * ner(命名实体识别)、parse(语法解析)、指代消解？同义词分辨？
         */

        Properties props = new Properties();
        //props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-caseless-left3words-distsim.tagger");
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");    // 七种Annotators
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // 依次处理

        String text = "This is a test. An dog, an cat.";               // 输入文本
//        MaxentTagger tagger = new MaxentTagger(
//                "taggers/english-left3words-distsim.tagger");

        Annotation document = new Annotation(text);    // 利用text创建一个空的Annotation
        pipeline.annotate(document);                   // 对text执行所有的Annotators（七种）

        // 下面的sentences 中包含了所有分析结果，遍历即可获知结果。
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        System.out.println("word\tpos\tlemma\tner");

        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

                String word = token.get(TextAnnotation.class);            // 获取分词
                String pos = token.get(PartOfSpeechAnnotation.class);     // 获取词性标注
                String ne = token.get(NamedEntityTagAnnotation.class);    // 获取命名实体识别结果
                String lemma = token.get(LemmaAnnotation.class);          // 获取词形还原结果

                System.out.println(word+"\t"+pos+"\t"+lemma+"\t"+ne);
            }

            // 获取parse tree
            Tree tree = sentence.get(TreeAnnotation.class);
            System.out.println(tree.toString());

            // 获取dependency graph
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(dependencies);
        }
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
    }
}
