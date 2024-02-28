package Basics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.JSONArray;
import org.json.JSONObject;



public class SDET 
{
	
		public static void main(String [] args) throws InterruptedException {
			
			try {
	            // Read JSON file
	            String jsonContent = new String(Files.readAllBytes(Paths.get("C:\\automation\\Rajat\\src\\Basics\\testData.json")));
	            //Declared Chrome driver and initiated Chrome browser
	            System.setProperty("webdriver.chromedriver", "C:\\automation\\Rajat\\sarver\\chromedriver.exe");
				WebDriver driver =new ChromeDriver();
				//Declared Web Elements
				WebElement element,mytable;
				//Opened browser getting open given web URL
				driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");      
		        element = driver.findElement(By.xpath("//summary[text()='Table Data']"));
		        element.click();
		        element = driver.findElement(By.xpath("//*[@id='jsondata']"));
		        element.clear();
		        element.sendKeys(jsonContent);
		        element = driver.findElement(By.xpath("//*[@id='refreshtable']"));
		        element.click();
		        JSONArray jsonArray = new JSONArray(jsonContent);
		        //To locate table.
		    	 mytable = driver.findElement(By.xpath("//*[@id=\"dynamictable\"]"));
		    	//To locate rows of table. 
		    	List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
		    	//To calculate no of rows In table.
		    	int rows_count = rows_table.size();
		    	//Loop will execute till the last row of table.		    
	            // Get the JSON object at the current index
         		for (int row = 1; row < rows_count; row++) {
	    	    //To locate columns(cells) of that specific row.
         			List < WebElement > Columns_row = rows_table.get(row).findElements(By.tagName("td"));
         			//To calculate no of columns (cells). In that specific row.
         			int columns_count = Columns_row.size();
		    	    //Loop will execute till the last cell of that specific row.
		    	    for (int column = 0; column < columns_count; column++) {
		    	        // To retrieve text from that specific cell.
		    	        String celtext = Columns_row.get(column).getText();
		    	        JSONObject jsonObject = jsonArray.getJSONObject(row-1);
		    	        if(column == 0) {
		    	        	Assert.assertEquals(jsonObject.getString("name"),celtext);
		    	        }else if (column == 1) {
		    	        	Assert.assertEquals(Integer.toString(jsonObject.getInt("age")),celtext);
		    	        }else if(column == 2) {
		    	        	Assert.assertEquals(jsonObject.getString("gender"),celtext);
		    	        }else {
		    	        	System.out.println(jsonObject.getString("name")+" Data Not Matched!");
		    	        }
		    	    }
	    	    }
		        driver.close();
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
	    }	
	}