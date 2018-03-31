package com.jackila.dbdemo.doc4j;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * create by jackila ON 03/01/2018
 */
public class DocxUtil {

    public static void main(String[] args) throws Exception {
        WordprocessingMLPackage mlp = WordprocessingMLPackage.load(new File("/Users/jackila/Desktop/template.docx"));
        replaceText(mlp.getMainDocumentPart());

    }

    static Map<String,String> data ;

            static void replaceText(ContentAccessor c)
    throws Exception
        {
            if(data == null){
                return ;
            }
            for (Object p: c.getContent())
            {
                if (p instanceof ContentAccessor) {
                    replaceText((ContentAccessor) p);
                }

                else if (p instanceof JAXBElement)
                {
                    Object v = ((JAXBElement) p).getValue();

                    if (v instanceof ContentAccessor){
                        replaceText((ContentAccessor) v);
                    }

                    else if (v instanceof org.docx4j.wml.Text)
                    {
                        org.docx4j.wml.Text t = (org.docx4j.wml.Text) v;
                        String text = t.getValue();
                        if (text != null) {
                            //t.setSpace("preserve"); // needed?
                            t.setValue(replaceParams(text));
                        }
                    }
                }
            }
        }

        static String replaceParams(String text)
        {
            if(!data.containsKey(text)){
                return text;
            }

            return data.get(text);
        }

    public static void findRichTextNode(Map<String, String> dataMap, Map<String, List<Object>> resultData,
                                         Object object, ContentAccessor accessor) {
        Object textObj = XmlUtils.unwrap(object);
        if (textObj instanceof Text) {
            String text = ((Text) textObj).getValue();
            if (StringUtils.isNotEmpty(text)) {
                text = text.trim();
                if (text.startsWith("RH") && text.endsWith("END")) {
                    String textTag = text.substring("RH".length(), text.length() - 3);
                    if (StringUtils.isNotEmpty(textTag) && (accessor != null)) {
                        if (resultData.containsKey(textTag)) {
                            resultData.get(textTag).add(accessor);
                        } else {
                            List<Object> objList = Lists.newArrayList();
                            objList.add(accessor);
                            resultData.put(textTag, objList);
                        }
                    }
                } else if (dataMap.containsKey(text)) {
                    ((Text) textObj).setValue(dataMap.get(text));
                }
            }
        } else if (object instanceof ContentAccessor) {
            List<Object> objList = ((ContentAccessor) object).getContent();
            for (int i = 0, iSize = objList.size(); i < iSize; i++) {
                findRichTextNode(dataMap, resultData, objList.get(i), (ContentAccessor) object);
            }
        }
    }


}
