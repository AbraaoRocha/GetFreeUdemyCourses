package Page;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PagePrincipal {

	public static final String LinkList = "/html/body/div[6]/div/div/div[1]/div/div[1]/div/article/div/div[1]/p/a";

	public static final String BtnAddToCart = "/html/body/div[1]/div[3]/div[1]/div[3]/div/div/div/div[1]/div/div[1]/div[2]/div/div[1]/div/div[4]/div/button";

	public static final String LblPrice = "/html/body/div[1]/div[3]/div[1]/div[3]/div/div/div/div[1]/div/div[1]/div[2]/div/div[1]/div/div[2]/div/div/div[1]/span[2]";

	public static final String SpanGoToCourse = "/html/body/div[1]/div[3]/div[1]/div[3]/div/div/div/div[1]/div/div[1]/div[2]/div/div[1]/div/div[2]/div/button/span";

	public static final String SpanFreeCart = "/html/body/div[1]/div[2]/div/div/div/div[2]/div[2]/div[1]/div/div[2]/span[2]";

	private static final String BtnClosePopUp = "/html/body/div[4]/div/div[2]/button";
	
	@FindBy(how = How.XPATH, using = LinkList)
	public static List<WebElement> listLink;

	@FindBy(how = How.XPATH, using = BtnAddToCart)
	public static WebElement btnAddToCart;

	@FindBy(how = How.XPATH, using = LblPrice)
	public static WebElement lblPrice;
	
	@FindBy(how = How.XPATH, using = SpanGoToCourse)
	public static WebElement spanGoToCourse;

	@FindBy(how = How.XPATH, using = SpanFreeCart)
	public static WebElement spanFreeCart;
	
	@FindBy(how = How.XPATH, using = BtnClosePopUp)
	public static WebElement btnClosePopUp;
	
}
