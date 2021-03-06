//package com.huake.lzf.test;
//
///**
// * Created by lizhaofu on 2017/4/26.
// */
//
//        import edu.stanford.nlp.dcoref.CorefChain;
//        import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
//        import edu.stanford.nlp.ie.util.RelationTriple;
//        import edu.stanford.nlp.ling.CoreAnnotations;
//        import edu.stanford.nlp.ling.CoreLabel;
//        import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
//        import edu.stanford.nlp.naturalli.OpenIE;
//        import edu.stanford.nlp.naturalli.SentenceFragment;
//        import edu.stanford.nlp.pipeline.Annotation;
//        import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//        import edu.stanford.nlp.semgraph.SemanticGraph;
//        import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
//        import edu.stanford.nlp.trees.Tree;
//        import edu.stanford.nlp.trees.TreeCoreAnnotations;
//        import edu.stanford.nlp.util.CoreMap;
//        import edu.stanford.nlp.util.PropertiesUtils;
//
//        import java.util.Collection;
//        import java.util.List;
//        import java.util.Map;
//        import java.util.Properties;
//
//        import static edu.stanford.nlp.ie.machinereading.structure.RelationMention.logger;
//
///**
// * Created by KrseLee on 2016/11/5.
// */
//public class CoreNlpTest {
//
//    public static void main(String[] args) throws Exception {
//
//        Properties props = new Properties();         //props是一个类似map的结构
//        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, natlog, openie");
//        /*   tokenize       Tokenizesthetextintoasequenceoftokens.分词，中文中，将句子分成一个个的词，英文中较简单？
//           * ssplit       Splits a sequence of tokens into sentences. 断句
//           * cleanxml     Removes most or all XML tags from the document
//           * truecase     Determinesthelikelytruecaseoftokens in text
//           * pos          part of speech,Labels tokens with their POS tag，词性标注CC,DT,JJR,TO,VB等等等等
//           * lemma        lemmatization，词元化，表示出词的原型，例如 sings--sing   your--you  is--be
//           * gender       Adds likely gender information to names
//           * ner          named entities recognizer  命名实体识别  识别出是ORGANIZATION组织，LOCATION地点 等等
//           *              Time, Location, Organization, Person, Money, Percent, Date   这7种
//           * parse        找出句子的语法结构，哪些词可以成组，哪些词是这个动词的主语或宾语
//           * depparse     Neural Network Dependency Parser  更厉害的parse？
//           * sentiment    Sentiment analysis with a compositional model over trees using deep learning
//           * natlog       Natural Logic   some cute rabbits are small -- some rabbits are small.
//           * dcoref       同义词分辨 Implements mention detection and both pronominal and nominal coreference resolution
//           * openie       open information extraction, 提取关系三元组
//           * */
//        /*
//         * 以下两种初始化管道的方式二选一
//         */
//      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);                  //用props初始化管道pipeline
////      StanfordCoreNLP pipeline = new StanfordCoreNLP(
////              PropertiesUtils.asProperties(
////                  "annotators", "tokenize,ssplit,pos,lemma,ner，depparse,natlog,openie",
////                  "ssplit.isOneSentence", "true",
////                  "parse.model", "edu/stanford/nlp/models/srparser/englishSR.ser.gz",
////                  "tokenize.language", "en"));
//
//        String text = "Stanford University is located in Stanford which is one of best good universities in 2015.\n";
//        Annotation doc = new Annotation(text);                                 // 用字符串初始化一个annotation类型
//        pipeline.annotate(doc);
//        /*  将前面的一系列操作处理字符串  StanfordCoreNLP.annotate(Annotation)
//         *  得到的doc为处理后的doc
//         */
//        int sentNo = 0;
//        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//        // sentence 是一个coreMap类型，使用类作为key，value可以为自定义类型
//        for(CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)){            //对 doc 的每一句话
//            System.out.println("Sentence #" + ++sentNo + ": " + sentence.get(CoreAnnotations.TextAnnotation.class)); //输出处理前的那句话
//            //如何得到分句后的结果呢？
//            System.out.println("word\tpos\tlema\tne");
//            // a CoreLabel is a CoreMap with additional token-specific methods
//            for(CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){                       //对每句话的每个单词(分词以后的)
//                String word = token.get(CoreAnnotations.TextAnnotation.class);                                 //获取分词
//                String lema = token.get(CoreAnnotations.LemmaAnnotation.class);                                //这个词的词元
//                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);                          //这个词的词性
//                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);                         //这个词属于哪种命名实体
//
//                System.out.println(word + '\t' + pos + '\t' + lema + '\t' + ne);
//            }
//
//            // this is the parse tree of the current sentence
//            // depparse 没有parse tree
////          System.out.println("--------tree--------");
////          Tree tree = sentence.get(TreeAnnotation.class);
////          System.out.println(tree.toString());
//
//
//            // SemanticGraph，BasicDependencies
//            System.out.println("--------SemanticGraph BasicDependencies--------");
//            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
//            System.out.println(dependencies.toString());
//
//            // SemanticGraph，EnhancedDependencies
//            // this is the Stanford dependency graph of the current sentence
//            // toString(SemanticGraph.OutputFormat.LIST)为输出格式
//            System.out.println("--------SemanticGraph EnhancedDependencies LIST-format--------");
//            System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
//            System.out.println("--------SemanticGraph EnhancedDependencies READABLE-format--------");
//            System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.READABLE));
//
//
//            System.out.println("--------RelationTriple--------");
//            // Get the OpenIE triples for the sentence
//            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
//            for(RelationTriple triple : triples) {
//                System.out.println(triple.confidence + '\t' + triple.subjectLemmaGloss() + '\t' + triple.relationLemmaGloss()
//                        + '\t' + triple.objectLemmaGloss());
//            }
//
//            // Alternately, to only run e.g., the clause splitter:
//            System.out.println("--------clauses--------");
//            List<SentenceFragment> clauses = new OpenIE(props).clausesInSentence(sentence);
//            for(SentenceFragment clause : clauses){
//                System.out.println("LIST");
//                System.out.println(clause.parseTree.toString(SemanticGraph.OutputFormat.LIST));
//                System.out.println("READABLE");
//                System.out.println(clause.parseTree.toString(SemanticGraph.OutputFormat.READABLE));
//
//            }
//
//            System.out.println();
//        }
//        System.out.println("end!");
//    }
//
////        Properties props = new Properties();
////        props.put("annotators", "tokenize,ssplit,pos, lemma");
////        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
////        String txtWord = "this is a simple text";
////        Annotation document = new Annotation(txtWord);
////        pipeline.annotate(document);
////        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
////        for(CoreMap sentence: sentences) {
////            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
////                String word = token.get(CoreAnnotations.TextAnnotation.class);
////                String lema = token.get(CoreAnnotations.LemmaAnnotation.class);
////                logger.info(word+","+lema);
//////                String originWord = lema;
//////                originFlag = true;
////            }
////        }
//
//
//
//    public void runAllAnnotators() throws Exception{
//        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
//        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//        StanfordCoreNLP pipeline;
//        pipeline = new StanfordCoreNLP(props);
//
//        // read some text in the text variable
//        String text = "this is a simple text"; // Add your text here!
//
//        // create an empty Annotation just with the given text
//        Annotation document = new Annotation(text);
//
//        // run all Annotators on this text
//        pipeline.annotate(document);
//
//        parserOutput(document);
//    }
//
//    public void parserOutput(Annotation document){
//        // these are all the sentences in this document
//        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//
//        for(CoreMap sentence: sentences) {
//            // traversing the words in the current sentence
//            // a CoreLabel is a CoreMap with additional token-specific methods
//            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//                // this is the text of the token
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                // this is the POS tag of the token
//                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                // this is the NER label of the token
//                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//            }
//
//            // this is the parse tree of the current sentence
//            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//            System.out.println("语法树：");
//            System.out.println(tree.toString());
//
//            // this is the Stanford dependency graph of the current sentence
//            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
//            System.out.println("依存句法：");
//            System.out.println(dependencies.toString());
//        }
//
//        // This is the coreference link graph
//        // Each chain stores a set of mentions that link to each other,
//        // along with a method for getting the most representative mention
//        // Both sentence and token offsets start at 1!
//        Map<Integer, CorefChain> graph =
//                document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
//    }
//}
