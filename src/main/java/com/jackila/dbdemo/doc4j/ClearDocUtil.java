package com.jackila.dbdemo.doc4j;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;

import javax.xml.bind.JAXBException;
import java.util.List;

import static com.jackila.dbdemo.doc4j.AppendTabel.getAllElementFromObject;

/**
 * create by jackila ON 05/01/2018
 */
public class ClearDocUtil {

    public static void clearWordFlag(String[] docOperateFlag, WordprocessingMLPackage wordMLPackage) throws JAXBException, Docx4JException {

        //find all element
        List<P> allElementFromObject = getAllElementFromObject(wordMLPackage.getMainDocumentPart(), P.class);
        List<P> templateParameter = CopyParagraph.getTemplateParameter(allElementFromObject, docOperateFlag);

        //remove all element
        wordMLPackage.getMainDocumentPart().getContent().removeAll(templateParameter);
    }

}
