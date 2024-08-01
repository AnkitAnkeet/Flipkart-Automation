package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {

   
    //method that can search anything in searchBox
    public static void searchProduct(ChromeDriver driver, String searchItem) throws InterruptedException{
        try{
            WebElement searchTextBox = driver.findElement(By.xpath("//input[@class='Pke_EE' or @class='zDPmFV']"));
            try{
            //select the content of textbox and delete whatever inside
            searchTextBox.sendKeys(Keys.CONTROL + "a");
            searchTextBox.sendKeys(Keys.DELETE);
            }catch(Exception e){
            System.out.println("Error clearing the search text box: " + e.getMessage());
            }
            searchTextBox.sendKeys(searchItem);
            try{
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            }catch(Exception e){
            System.out.println("Error clicking the submit button: " + e.getMessage());
            }
        }catch(Exception e){
            System.out.println("Error locating the search text box: " + e.getMessage());
        }
        Thread.sleep(4000);
    }

  
    //method to select any option that comes under "sort by" catagory
    public static void sortBy(ChromeDriver driver, String optionText) throws InterruptedException{
          try{
            List<WebElement> allOptions = driver.findElements(By.xpath("//div[contains(@class,'zg-M3Z')]"));
            boolean optionFound = false;
            for(WebElement option : allOptions){
                if(option.getText().trim().equals(optionText)){
                    try{
                    option.click();
                    optionFound = true;
                    Thread.sleep(3000);
                    break;
                    }catch(Exception e){
                    System.out.println("Error clicking the option: " + e.getMessage());
                    }
                 }
             
             }
             if(!optionFound){
                System.out.println("Option text not found: " + optionText);
            }
          }catch(Exception e){
            System.out.println("Error locating options: " + e.getMessage());
        }
    
    }

   //method to get the count based on rating
    public static int countByRating(ChromeDriver driver, double rating){
        int count = 0;
        try{
            List<WebElement> products = allDisplayedProducts(driver, By.xpath("//div[@class='yKfJKb row']"));
            for(WebElement product : products){
                try{
                    String ratingInString = product.findElement(By.xpath("./div/div/following-sibling::div/span/div")).getText().trim();
                    double ratingIndouble = extractValue(ratingInString);
                    if(ratingIndouble <= rating){
                        count++;
                    }
                }catch(Exception e){
                System.out.println("Error extracting or parsing rating: " + e.getMessage());
                }
            }
        }catch(Exception e){
        System.out.println("Error locating product elements: " + e.getMessage());
        }
        return count;
    }
    
    //method to store Webelements in a list
    public static List<WebElement> allDisplayedProducts(ChromeDriver driver, By locator){
        return driver.findElements(locator);
    }
    //method to filter list of webelement by discount percentage
    public static ArrayList<WebElement> filterByDiscount(ChromeDriver driver, double percentage){
        ArrayList<WebElement> shortedList = new ArrayList<>();
        try{
            List<WebElement> products = allDisplayedProducts(driver, By.xpath("//div[@class='yKfJKb row']"));
            for(WebElement product : products){
                try{
                String percentageString = product.findElement(By.xpath("./div/following-sibling::div/div/div/div[3]/span")).getText().trim();
                double extractedPercentage = extractValue(percentageString);
                  if(extractedPercentage == percentage){
                    shortedList.add(product);
                    }
                }catch(Exception e){
                System.out.println("Error extracting or parsing discount percentage: " + e.getMessage());
                }
            }
        }catch(Exception e){
        System.out.println("Error locating product elements: " + e.getMessage());
        }
        return shortedList;
    }

  //method to extract the integers from a string as double
    public static double extractValue(String str){
        StringBuilder numericPart = new StringBuilder();
        for(char ch: str.toCharArray()){
            if(Character.isDigit(ch) || ch == '.'){
                numericPart.append(ch);
            }
        }
        return Double.parseDouble(numericPart.toString());
    }

   //method to get text of elements 
    public static void printOnly(ChromeDriver driver, By locator, ArrayList<WebElement> list){
        for(WebElement element: list){
            String s = element.findElement(locator).getText();
            System.out.println(s);
        }
    }
   //method to apply any filter
    public static void selectOption(ChromeDriver driver, String option1, String option2) throws InterruptedException{
        try{
            List<WebElement> allOptions = driver.findElements(By.xpath("//div[@class='ewzVkT _3DvUAf']"));
            boolean optionSelected = false;
            for(WebElement desiredOpt : allOptions){
                try{
                    String attributeValue = desiredOpt.getAttribute("title");
                    if(attributeValue != null && attributeValue.contains(option1) && attributeValue.contains(option2)){
                        desiredOpt.findElement(By.xpath("./div/label/div")).click();
                        optionSelected = true;
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Error interacting with option: " + e.getMessage());
                }
            }
            if(!optionSelected){
                System.out.println("No option matching the criteria was found.");
            }
        }catch(Exception e){
            System.out.println("Error locating options: " + e.getMessage());
        }
        Thread.sleep(3000);
    }

    //method to print title and img url 
    public static void numberOfReviewList(ChromeDriver driver, int numberOfProductsNeed) throws InterruptedException{
        ArrayList<Integer> reviewWebElements = new ArrayList<>();
        ArrayList<WebElement> imgWebElements = new ArrayList<>();
        ArrayList<WebElement> titleElements = new ArrayList<>();
        try{
            List<WebElement> listOfAll = allDisplayedProducts(driver, By.xpath("//div[@class='slAVV4']"));
            for(WebElement singleElement : listOfAll){
                try{
                    WebElement reviewWebElement = singleElement.findElement(By.xpath(".//span[@class='Wphh3N']"));
                    WebElement imgElement = singleElement.findElement(By.xpath(".//img[@class='DByuf4']"));
                    WebElement titleElement = singleElement.findElement(By.xpath(".//a[@class='wjcEIp']"));

                    titleElements.add(titleElement);
                    imgWebElements.add(imgElement);
                    reviewWebElements.add((int) extractValue(reviewWebElement.getText().trim()));
                }catch(Exception e){
                    System.out.println("Error extracting information from element: " + e.getMessage());
                }
            }
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < reviewWebElements.size(); i++){
                indexes.add(i);
            }
            indexes.sort(Comparator.comparingInt(reviewWebElements::get).reversed());
            for(int i = 0; i < numberOfProductsNeed && i < indexes.size(); i++){
                try{
                    System.out.println("Title: " + titleElements.get(indexes.get(i)).getText());
                    System.out.println("Img URL: " + imgWebElements.get(indexes.get(i)).getAttribute("src"));
                }catch(Exception e){
                    System.out.println("Error printing product information: " + e.getMessage());
                }
                Thread.sleep(2000);
            }
        }catch(Exception e){
        System.out.println("Error processing product list: " + e.getMessage());
        }
    }
}
