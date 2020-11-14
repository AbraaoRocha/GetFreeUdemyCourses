package Frame;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.PageFactory;

import Page.PagePrincipal;
import core.DriverUtils;

@SuppressWarnings("all")
public class FramePrincipal {

	private static PagePrincipal page = new PagePrincipal();
	
	private static void init() {
		
		page = PageFactory.initElements(DriverUtils.getDriver(), PagePrincipal.class);
	
	}
	
	public static List<String> getListaLinks(){
		List<String> lista = new ArrayList();
		
		init();
		
		for(int i =0; i< page.listLink.size()-1; i++) {
			
			DriverUtils.delay(250);
			
			DriverUtils.scrollToElemento(page.listLink.get(i));
			
			lista.add(page.listLink.get(i).getText());
		
		}
		
		return lista;
	}
	
	
	
}
