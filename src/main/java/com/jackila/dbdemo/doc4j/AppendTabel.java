package com.jackila.dbdemo.doc4j;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by jackila ON 03/01/2018
 */
public class AppendTabel {


    public static Tbl replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
                              WordprocessingMLPackage template,Tbl tempTable) throws Docx4JException, JAXBException {
        if(tempTable == null){
            List<Tbl> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

            // 1. find the table
            tempTable = getTemplateTable(tables, placeholders[0]);

        }else{
            tempTable = XmlUtils.deepCopy(tempTable);
        }

        List<Tr> rows = getAllElementFromObject(tempTable, Tr.class);

        // first row is header, second row is content
        if (rows.size() == 2) {
            // this is our template row
            Tr templateRow = (Tr) rows.get(1);

            for (Map<String, String> replacements : textToAdd) {
                // 2 and 3 are done in this method
                addRowToTable(tempTable, templateRow, replacements);
            }
            // 4. remove the template row
            tempTable.getContent().remove(templateRow);
        }
        return tempTable;
    }

    public  static Tbl getTemplateTable(List tables, String templateKey) throws Docx4JException, JAXBException {
        for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
            Object tbl = iterator.next();
            List<?> textElements = getAllElementFromObject(tbl, Text.class);
            for (Object text : textElements) {
                Text textElement = (Text) text;
                if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
                    return (Tbl) tbl;
            }
        }
        return null;
    }

    private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
        Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
        List<?> textElements = getAllElementFromObject(workingRow, Text.class);
        for (Object object : textElements) {
            Text text = (Text) object;
            String replacementValue = (String) replacements.get(text.getValue());
            if (replacementValue != null)
                text.setValue(replacementValue);
        }

        reviewtable.getContent().add(workingRow);
    }

    public static <T> List<T> getAllElementFromObject(Object obj, Class<T> toSearch) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

        if(obj == null)return result;

        if (obj.getClass().equals(toSearch))
            result.add((T)obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

}
