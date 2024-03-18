package xmlParsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParse1 {
	public static boolean checkValidity(String date) {
		LocalDate toBeCheckedDate=LocalDate.parse(date,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		LocalDate referenceDate=LocalDate.now();
		boolean isValid=!toBeCheckedDate.isBefore(referenceDate);
		
		if(isValid) {
			return true;
		}
		else {
			return false;
		}
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document document1=builder.parse(".\\data\\file1.xml");
		Document document2=builder.parse(".\\data\\file2.xml");
		PrintWriter pw1=new PrintWriter(".\\data\\valid.txt");
		PrintWriter pw2=new PrintWriter(".\\data\\invalid.txt");
		NodeList csr1=document1.getElementsByTagName("CSR_Producer");
		for(int i=0;i<csr1.getLength();i++)
		{
			Element ele=(Element)csr1.item(i);
			NodeList licenses = ele.getElementsByTagName("License");
			for(int j=0;j<licenses.getLength();j++)
			{
				Element one=(Element)licenses.item(j);
				String nipr=ele.getAttribute("NIPR_Number");
				String expirationDate=one.getAttribute("License_Expiration_Date");
				String StateCode=one.getAttribute("State_Code");
				String licenseNumber=one.getAttribute("License_Number");
				String licenseEffectiveDate=one.getAttribute("Date_Status_Effective");
				if(checkValidity(expirationDate))
				{
					pw1.println(nipr+" "+StateCode+" "+licenseNumber+" "+licenseEffectiveDate);
					System.out.println("File written successfully");
				}
				else{
					pw2.println(nipr+" "+StateCode+" "+licenseNumber+" "+licenseEffectiveDate);
					System.out.println("File written successfully");
				}
			}
		}
		
		NodeList csr2=document2.getElementsByTagName("CSR_Producer");
		for(int i=0;i<csr2.getLength();i++)
		{
			Element ele=(Element)csr2.item(i);
			String nipr=ele.getAttribute("NIPR_Number");
			NodeList licenses = ele.getElementsByTagName("License");
			for(int j=0;j<licenses.getLength();j++)
			{
				Element one=(Element)licenses.item(j);
				String expirationDate=one.getAttribute("License_Expiration_Date");
				String StateCode=one.getAttribute("State_Code");
				String licenseNumber=one.getAttribute("License_Number");
				String licenseEffectiveDate=one.getAttribute("Date_Status_Effective");
				if(checkValidity(expirationDate))
				{
					pw1.println(nipr+" "+StateCode+" "+licenseNumber+" "+licenseEffectiveDate);
					System.out.println("File written successfully");
				}
				else{
					pw2.println(nipr+" "+StateCode+" "+licenseNumber+" "+licenseEffectiveDate);
					System.out.println("File written successfully");
				}
			}
		}
		pw1.close();
		pw2.close();
		
		PrintWriter pw = new PrintWriter(".\\data\\merged.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(".\\data\\valid.txt"));
		String line = br.readLine();
		while (line != null) 
        { 
            pw.println(line); 
            line = br.readLine(); 
        } 
		br = new BufferedReader(new FileReader(".\\data\\invalid.txt"));
		line = br.readLine(); 
		while(line != null) 
        { 
            pw.println(line); 
            line = br.readLine(); 
        } 
		pw.flush();
		br.close();
		pw.close(); 
		System.out.println("Merged both text files");
	}
}
