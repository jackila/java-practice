package com.jackila.dbdemo.doc4j;

import com.google.common.collect.Lists;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by jackila ON 02/01/2018
 */
public class CreateWordprocessingMLDocument {


    public final static String[] DOC_OPERATE_FLAG = {"companyList","startCompanyList","endCompanyList"};
    public static void main(String[] args) throws Exception {

        Object b = null;
        String a = (String) b;

        boolean save = true;
        WordprocessingMLPackage wordMLPackage =
                WordprocessingMLPackage.load(new java.io.File("/Users/jackila/Desktop/template.docx"));

        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//
        DocxUtil.data = getStaticData();
//
//
//        Body body = documentPart.getContents().getBody();
//        Map ret = Maps.newHashMap();
//        findRichTextNode(getStaticData(),ret,body,null);
//
        //documentPart.variableReplace(staticMap);

        // Pretty print the main document part
//        System.out.println(
//                XmlUtils.marshaltoString(mdp.getJaxbElement(), true, true) );

        List list = Lists.newArrayList();
        list.add(getStaticData());
        list.add(getStaticData());
        Map repl1 = new HashMap<String, String>();
        repl1.put("companyName", "公司名1");
        repl1.put("companyRegistTime", "desc1");
        repl1.put("companySiteList", list);

        Map repl2 = new HashMap<String, String>();
        repl2.put("companyName", "公司名2");
        repl2.put("companyRegistTime", "desc2");
        repl2.put("companySiteList", list);

        Map repl3 = new HashMap<String, String>();
        repl3.put("companyName", "公司名3");
        repl3.put("companyRegistTime", "desc3");
        repl3.put("companySiteList", list);

        List<Map> maps = Arrays.asList(repl1, repl2, repl3);
        CopyParagraph.appendParamgraphAfterElement(
                new String[]{"siteType","siteURL"},
                maps,
                wordMLPackage,
                new String[]{"companyList"},
                "companySiteList");

        ClearDocUtil.clearWordFlag(DOC_OPERATE_FLAG,wordMLPackage);
        // Optionally save it
        if (save) {
            String filename ="/Users/jackila/Desktop/OUT.docx";
            wordMLPackage.save(new java.io.File(filename) );
            System.out.println("Saved " + filename);
        }

    }


    public static HashMap<String,String> getStaticData() {
        HashMap<String,String> staticData = new HashMap();
        staticData.put("siteType","hello world");
        staticData.put("siteURL","2017-10-09");
        staticData.put("title","mingzi");

        return staticData;
    }


}
