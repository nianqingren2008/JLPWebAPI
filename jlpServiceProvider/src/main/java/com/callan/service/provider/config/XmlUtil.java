package com.callan.service.provider.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XmlUtil {
	public void readStringXml(String xml) {
        Document doc = null;
        try {

            // 读取并解析XML文档
            // SAXReader就是一个管道，用一个流的方式，把xml文件读出来
            // 
            // SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
            // Document document = reader.read(new File("User.hbm.xml"));
            // 下面的是通过解析xml字符串的
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

            Iterator iter = rootElt.elementIterator("head"); // 获取根节点下的子节点head

            // 遍历head节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                String title = recordEle.elementTextTrim("title"); // 拿到head节点下的子节点title值
                System.out.println("title:" + title);

                Iterator iters = recordEle.elementIterator("script"); // 获取子节点head下的子节点script

                // 遍历Header节点下的Response节点
                while (iters.hasNext()) {

                    Element itemEle = (Element) iters.next();

                    String username = itemEle.elementTextTrim("username"); // 拿到head下的子节点script下的字节点username的值
                    String password = itemEle.elementTextTrim("password");

                    System.out.println("username:" + username);
                    System.out.println("password:" + password);
                }
            }
            Iterator iterss = rootElt.elementIterator("body"); ///获取根节点下的子节点body
            // 遍历body节点
            while (iterss.hasNext()) {

                Element recordEless = (Element) iterss.next();
                String result = recordEless.elementTextTrim("result"); // 拿到body节点下的子节点result值
                System.out.println("result:" + result);

                Iterator itersElIterator = recordEless.elementIterator("form"); // 获取子节点body下的子节点form
                // 遍历Header节点下的Response节点
                while (itersElIterator.hasNext()) {

                    Element itemEle = (Element) itersElIterator.next();

                    String banlce = itemEle.elementTextTrim("banlce"); // 拿到body下的子节点form下的字节点banlce的值
                    String subID = itemEle.elementTextTrim("subID");

                    System.out.println("banlce:" + banlce);
                    System.out.println("subID:" + subID);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static List<Map<String,String>> coventXml(String xml) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Document doc = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml); 
            // 获取根节点
            Element rootElt = doc.getRootElement(); 
            // 拿到根节点的名称
//            System.out.println("根节点：" + rootElt.getName()); 

            //获取根节点下的子节点body
            Iterator iterss = rootElt.elementIterator("body"); 
            // 遍历body节点
            while (iterss.hasNext()) {
                Element recordEless = (Element) iterss.next();
                // 拿到body节点下的子节点result值
                // 获取子节点body下的子节点form
                Iterator itersElIterator = recordEless.elementIterator("div"); 
                // 遍历Header节点下的Response节点
                while (itersElIterator.hasNext()) {
                	Map<String,String> map = new HashMap<String, String>();
                    Element itemEle = (Element) itersElIterator.next();
                    // 拿到div下的子节点form下的字节点name的值
                    String name = itemEle.attributeValue("name");
                    if("插入文本块".equals(name)) {
                    	String title = itemEle.attributeValue("title");
                        String text = itemEle.elementTextTrim("text");
                        if(text != null) {
                        	text = text.trim();
                        }
                    	map.put(title, text);
                    }
                    if(map.isEmpty()) {
                    	String title = recordEless.attributeValue("title");
                        String text = recordEless.elementTextTrim("text");
                        if(text != null) {
                        	text = text.trim();
                        }
                        if(StringUtils.isNotBlank(title)) {
                        	map.put(title, text);
                        }
                    }
                    if(!map.isEmpty()) {
                    	list.add(map);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}

