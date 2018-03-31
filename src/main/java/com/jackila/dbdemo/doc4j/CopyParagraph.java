package com.jackila.dbdemo.doc4j;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.jackila.dbdemo.doc4j.AppendTabel.getAllElementFromObject;
import static com.jackila.dbdemo.doc4j.AppendTabel.getTemplateTable;

/**
 * create by jackila ON 04/01/2018
 */
public class CopyParagraph {


    public static void appendParamgramAfterElementContainTable(String[] placeholders,
                                                               String[] tableHolders,
                                                               List<Map<String, Object>> textToAdd,
                                                               String tableName,
                                                               WordprocessingMLPackage template,
                                                               String[] afterPlaceHolder
    ) throws JAXBException, Docx4JException {
        List<Tbl> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
        Tbl tempTable = getTemplateTable(tables, placeholders[0]);


    }


    public static void appendParamgraphAfterElement(String[] tableHolders,
                                                    List<Map> textToAdd,
                                                    WordprocessingMLPackage template,
                                                    String[] afterPlaceHolder,
                                                    String tableName) throws Docx4JException, JAXBException {

        List<Tbl> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
        Tbl tempTable = (Tbl)tables.get(1);

        Tbl n = XmlUtils.deepCopy(tempTable);
        template.getMainDocumentPart().getContent().remove(tables.get(1));
        List<Tr> rows = getAllElementFromObject(tempTable, Tr.class);

        if (rows.size() == 2) {
            tempTable.getContent().removeAll(rows);
        }
        n.setParent(null);
        List<P> paragram = getAllElementFromObject(template.getMainDocumentPart(), P.class);
        List  tempP = getRangeTemplateParameter(paragram, new String[]{"startCompanyList","endCompanyList"},template.getMainDocumentPart().getContent());
        P headP =  getTemplateParameter(paragram, afterPlaceHolder).get(0);
        int index = template.getMainDocumentPart().getContent().indexOf(headP);

        //2
        for (Map<String, Object> replacements : textToAdd) {
            List newParameter = createNewParameter(tempP, replacements);
            Tbl newTempTable= AppendTabel.replaceTable(tableHolders,(List)replacements.get(tableName),template,n);
            newParameter.add(newTempTable);
            for(Object p:newParameter){
                index++;
                template.getMainDocumentPart().getContent().add(index,p);
            }

        }

        //3 移除模版的字段
        List<Tbl> allElementFromObject = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);


        template.getMainDocumentPart().getContent().removeAll(tempP);


    }

    public static List<P>  getTemplateParameter(List parameter, String[] templateKey) throws Docx4JException, JAXBException {

        List<P> ret = Lists.newArrayList();
        for (Iterator<Object> iterator = parameter.iterator(); iterator.hasNext();) {
            Object param = iterator.next();
            List<?> textElements = getAllElementFromObject(param, Text.class);
            for (Object text : textElements) {
                Text textElement = (Text) text;
                if (textElement.getValue() != null && ArrayUtils.contains(templateKey,textElement.getValue())){
                    ret.add((P)param);
                }

            }
        }
        return ret;
    }

    private static List<P>  getRangeTemplateParameter(List parameter, String[] templateKey, List<Object> content) throws Docx4JException, JAXBException {

        List<P> ret = Lists.newArrayList();
        boolean start = false;
        int startIndex = 0;
        Object startP = null;
        for (Iterator<Object> iterator = parameter.iterator(); iterator.hasNext();) {
            Object param = iterator.next();
            List<?> textElements = getAllElementFromObject(param, Text.class);

            for (Object text : textElements) {
                Text textElement = (Text) text;
                if (textElement.getValue() != null && ArrayUtils.contains(templateKey,textElement.getValue())){
                    if(startP == null){
                        startP = param;
                        start = true;
                    }else{
                        start = false;
                    }

                }

            }
            if(start){
                ret.add((P)param);
            }
        }
        ret.remove(startP);
        return ret;
    }

    private static List<P> createNewParameter(List<P> parameter, Map<String, Object> replacements) {

        List<P> nlist = Lists.newArrayList();
        for(P p:parameter){
            P p1 = XmlUtils.deepCopy(p);
            List<?> textElements = getAllElementFromObject(p1, Text.class);
            for (Object object : textElements) {
                Text text = (Text) object;
                String replacementValue = (String) replacements.get(StringUtils.trim(text.getValue()));
                if (replacementValue != null) {
                    text.setValue(replacementValue);
                }
            }
            nlist.add(p1);
        }

        return nlist;

    }



}
