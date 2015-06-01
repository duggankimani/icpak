package com.icpak.rest.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import org.apache.log4j.Logger;

public class DocumentHTMLMapper {

	private static Logger log = Logger.getLogger(DocumentHTMLMapper.class);
	
	public String map(Doc doc, String html){
		
		html = parseReplaceGridMatches(doc,html);
		html = parseAndReplaceQR(doc, html);
		html = parseAndReplace(doc.getValues(), html);
		
		return html;
	}

	private String parseReplaceGridMatches(Doc doc, String html) {
		Pattern pattern = Pattern.compile("<!--\\s*?@[<>]\\w+?\\b\\s*?-->");
		Matcher matcher = pattern.matcher(html);
		
		//Expected <!-- @>GridName --> Content <!-- @<GridName -->
		int c=0;
		int start=0;
		while(matcher.find()){
			++c;
//			System.err.format("I found the text" +
//                    " \"%s\" starting at " +
//                    "index %d and ending at index %d.%n",
//                    matcher.group(),
//                    matcher.start(),
//                    matcher.end());
			
			if(c%2==0){
				String gridRows = html.substring(start,matcher.start());
				String gridName = getGridName(matcher.group());
				log.debug("GridName = "+gridName);
				List<DocumentLine> gridVals=doc.getDetails().get(gridName);
				
				StringBuffer buff = new StringBuffer();
				if(gridVals!=null){
					for(DocumentLine line: gridVals){
						buff.append(parseAndReplaceGridRow(line, new String(gridRows)));
					}
				}
				
				html = html.replace(gridRows, buff.toString());
				html = html.replaceAll("<!--\\s*?@[<>]"+gridName+"\\b\\s*?-->", "");
				
				//System.err.println("########## Done "+gridName+"; Next Loop!!!! ");
				return parseReplaceGridMatches(doc, html);
			}else{
				start=matcher.end();
			}
			
        }
        
		return html;
	}

	private String parseAndReplaceGridRow(DocumentLine line, String html) {			
		return parseAndReplace(line.getValues(), html);
	}

	/**
	 * Example QR Representation
	 * <p>
	 * <pre>
	 * {@code
	 * <img width="100" height="100"
		src="QRCode
		DocNo @@lpoNumber\n
		CaseNo @@subject\n
		CaseId @#documentId\n
		Created @#GenDate\n
		CreatedBy @@createdBy\n 
		ValidationUrl: @@url 
		QRCode"></img>
	 }
	 * </pre>
	 * @param values
	 * @param html
	 * @return
	 */
	private String parseAndReplaceQR(Doc doc, String html) {
		Map<String, Object> values = doc.getValues();
		
		Pattern pattern = Pattern.compile("QRCode\\b(.*?)\\bQRCode",Pattern.DOTALL);//"QRCode\\b(.+?)\\bQRCode");
		String rtn = new String(html);
		Matcher matcher = pattern.matcher(rtn);
		
		while(matcher.find()){
			String group = matcher.group();
			String replacement = parseAndReplace(values, group.substring(7, group.length()-7));
			String pngUrl = generateQRCode(doc.getCaseNo(),replacement);
			rtn = rtn.replace(group, pngUrl);
        }
		
		return rtn;
	}

	private String generateQRCode(String subject, String strToEncode) {

		log.debug(subject+"- Encode to QR{ "+strToEncode+" }");
		
		String uri=null;
		
		try {
			
			File file = File.createTempFile(subject+" ", System.currentTimeMillis()+".png");
			
			QRCode.from(strToEncode).to(ImageType.PNG).writeTo(new FileOutputStream(file));
			
			uri = file.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("image url = "+uri);
		
		return uri;
	}

	private String parseAndReplace(Map<String, Object> values, String html) {
		Pattern pattern = Pattern.compile("@[@#]\\w+?\\b");
		String rtn = new String(html);
		Matcher matcher = pattern.matcher(rtn);
		
		while(matcher.find()){
			String group = matcher.group();
//			int start = matcher.start();
//			int end=matcher.end();
//			System.err.format("I found the text" +
//                    " \"%s\" starting at " +
//                    "index %d and ending at index %d.%n",
//                    group,
//                    start,
//                    end);
			boolean isNumber = group.startsWith("@#");
			String key = group.substring(2, group.length());
			Object val = get(values, key);
			String value = val==null ? "": val.toString();
			rtn= rtn.replace(matcher.group(), value);
        }
        	
        return rtn;
	}
	
	public String map(Map<String, Object> values,String html) {
		Pattern pattern = Pattern.compile("@[@#]\\w+?\\b");
		String rtn = new String(html);
		Matcher matcher = pattern.matcher(rtn);
		
		while(matcher.find()){
			String group = matcher.group();
//			int start = matcher.start();
//			int end=matcher.end();
//			System.err.format("I found the text" +
//                    " \"%s\" starting at " +
//                    "index %d and ending at index %d.%n",
//                    group,
//                    start,
//                    end);
			String key = group.substring(2, group.length());
			Object val = get(values, key);
			String value = val==null? "": getValue(val);
			rtn= rtn.replace(matcher.group(), value);
        }
        	
        return rtn;
	}

	private Object get(Map<String, ?> values, String key) {
		Object value  = values.get(key);
		if(value==null){
			key = key.substring(0, 1).toUpperCase()+key.substring(1, key.length());
			value = values.get(key);
		}
		
		return value;
	}

	private String getValue(Object value) {
		return getValue(value, false);
	}
	
	private String getValue(Object value, boolean isNumber) {
		if(value!=null){
			if(value instanceof Date){
				return new SimpleDateFormat("dd/MM/yyyy").format((Date)value);
			}
			
			if(isNumber && (value instanceof Number)){
				NumberFormat format = NumberFormat.getNumberInstance();
				String out = format.format(value);
				return out;
			}
			
			return value.toString();
		}
		
		return "";
	}

	public String getGridName(String group) {
		Pattern pattern = Pattern.compile("@[<>]\\w+?\\b");
		Matcher matcher = pattern.matcher(group);
		String gridName=null;
		if(matcher.find()){
			log.debug("match found >> "+matcher.group());
			gridName = group.substring(matcher.start(), matcher.end());
			return gridName.substring(2,gridName.length());
		}else{
			log.debug("No match found for "+group);
			return "";
		}
		
	}
	
}
