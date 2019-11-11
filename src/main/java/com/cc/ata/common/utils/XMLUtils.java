package com.cc.ata.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Map;

/**
 * 操作XML文件的工具类
 *
 * @author glw
 */
public class XMLUtils {
    /**
     * 得到XML文档
     *
     * @param xmlFile
     *            文件名（路径）
     * @return XML文档对象
     * @throws DocumentException
     */
    public static Document getDocument(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->指定文件【" + xmlFile + "】读取错误");
        }
    }

    /**
     * 得到XML文档(编码格式-gb2312)
     *
     * @param xmlFile
     *            文件名（路径）
     * @return XML文档对象
     * @throws DocumentException
     */
    public static Document getDocument_gb2312(String xmlFile) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("gb2312");
        File file = new File(xmlFile);
        try {
            if (!file.exists()) {
                return null;
            } else {
                return reader.read(file);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e + "->指定文件【" + xmlFile + "】读取错误");
        }
    }

    public static String getText(Element element) {
        try {
            return element.getTextTrim();
        } catch (Exception e) {
            throw new RuntimeException(e + "->指定【" + element.getName() + "】节点读取错误");
        }

    }

    /**
     * 增加xml文件节点
     *
     * @param document
     *            xml文档
     * @param elementName
     *            要增加的元素名称
     * @param attributeNames
     *            要增加的元素属性
     * @param attributeValues
     *            要增加的元素属性值
     */
    public static Document addElementByName(Document document, String elementName, Map<String, String> attrs, String cdata) {
        try {
            Element root = document.getRootElement();
            Element subElement = root.addElement(elementName);
            for (Map.Entry<String, String> attr : attrs.entrySet()) {
                subElement.addAttribute(attr.getKey(), attr.getValue());
            }
            subElement.addCDATA(cdata);
        } catch (Exception e) {
            throw new RuntimeException(e + "->指定的【" + elementName + "】节点增加出现错误");
        }
        return document;
    }


    /**
     * 输出xml文件
     *
     * @param document
     * @param filePath
     * @throws IOException
     */
    public static void writeXml(Document document, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * 创建Document及根节点
     *
     * @param rootName
     * @param attributeName
     * @param attributeVaule
     * @return
     */
    public static Document createDocument(String rootName, String attributeName, String attributeVaule) {
        Document document = null;
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);
            root.addAttribute(attributeName, attributeVaule);
        } catch (Exception e) {
            throw new RuntimeException(e + "->创建的【" + rootName + "】根节点出现错误");
        }
        return document;
    }




}