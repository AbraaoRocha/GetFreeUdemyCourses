package core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverUtils {

    private static WebDriver driver;

    private static long TEMPO = 20;

    public static WebDriver getDriver() {

        if (driver == null) {

            driver = DriverFactory.getDriver();

        }

        return driver;

    }

    public static void fecharDriver() {

        if (driver != null) {
        	DriverFactory.killDriver();
                driver = null;
            
        }

    }

    public static void mover(String x, String y) {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("document.window.moveTo(".concat(x).concat(",").concat(y).concat(")"));
    }

    public static void escrever(WebElement elemento, String texto) {

        buscarElementoPresente(elemento);
        elemento.sendKeys(texto);
    }

    public static void escrever(WebElement elemento, Keys key) {
        elemento.sendKeys(key);
    }

    public static String clicarMenu(WebElement menu1, String menu2) {
        if (menu2 != null) {
            WebElement eMenu2 = null;
            try {
                eMenu2 = driver.findElement(By.linkText(menu2));
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (menu2 != null) {
                if (eMenu2 == null || !eMenu2.isDisplayed()) {
                    clicar(menu1);
                    eMenu2 = driver.findElement(By.linkText(menu2));
                }

                return clicarMenu(eMenu2, null);

            }
        }
        return null;

    }

    public static void clicar(WebElement elemento) {

        try {

            scrollToElemento(elemento);

            elemento.click();

        } catch (ElementClickInterceptedException e) {

            scrollToElemento(elemento);

            elemento.click();

        }

    }

    public static void scrollToElemento(WebElement elemento) {

        JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();

        jse.executeScript("arguments[0].scrollIntoView()", elemento);

    }

    public static void scroll(String posicao) {
        JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        String scr = "scroll(0,###);";
        jse.executeScript(scr.replace("###", posicao));
    }

    public static void zerarScroll() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverUtils.getDriver();
        jse.executeScript("window.scrollTo(0,0)");

    }

    public static void limparCampo(WebElement elemento) {

        buscarElementoPresente(elemento);
        elemento.clear();
        delay(200);

    }

    public static void duploClique(WebElement elemento) {
        buscarElementoClicavel(elemento);

        elemento.click();

        elemento.click();
    }

    public static void duploCliqueComAjax(WebElement elemento) {

        buscarElementoClicavel(elemento);

        elemento.click();

        esperarPorAjax();

        elemento.click();

    }

    public static void esperarPorAjax() {

        try {

            delay(800);

            WebDriverWait wait = new WebDriverWait(driver, 1000);

            wait.withMessage("Tempo para aguardar o ajax esgotado.");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderMessage")));

        } catch (StaleElementReferenceException e) {

            delay(1000);

            WebDriverWait wait = new WebDriverWait(driver, 1000);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderMessage")));

        }
    }

    public static void esperarPor(By by)

    {

        try {
            int qtdEspera = 0;

            do {
                Thread.sleep(1000);
                if (qtdEspera > TEMPO) {

                    throw new TimeoutException();

                }
                qtdEspera++;
            } while (getDriver().findElements(by).size() == 0);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public static boolean buscarElementoPresente(WebElement locator) {

        WebDriverWait wait = new WebDriverWait(driver, TEMPO);

        wait.withMessage("Elemento " + locator + " não encontrado");

        wait.until((ExpectedConditions.elementToBeClickable(locator)));

        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(locator)));

        return true;
    }

    public static void buscarElementoClicavel(WebElement locator) {

        WebDriverWait wait = new WebDriverWait(driver, TEMPO);

        wait.withMessage("Elemento " + locator + " não clicável");

        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));

    }

    public static void selecionarComboPeloValor(WebElement elemento, Integer valor) {
        buscarElementoPresente(elemento);

        buscarElementoClicavel(elemento);

        Select select = new Select(elemento);

        select.selectByValue(valor.toString());

        esperarPorAjax();

    }

    public static void selecionarComboPeloNome(WebElement elemento, String texto) {
        buscarElementoPresente(elemento);

        buscarElementoClicavel(elemento);

        Select select = new Select(elemento);

        select.selectByVisibleText(texto);

        esperarPorAjax();

    }

    public static void selecionarComboPeloIndice(WebElement elemento, int indice) {

        buscarElementoClicavel(elemento);

        Select select = new Select(elemento);
        select.selectByIndex(indice);

        esperarPorAjax();

    }

    public static String getValor(WebElement elemento) {

        Select select = new Select(elemento);

        return select.getFirstSelectedOption().getAttribute("value");
    }

    public static String getValorCombo(WebElement elemento) {

        Select select = new Select(elemento);

        return select.getFirstSelectedOption().getText();
    }

    public static boolean isComboVazia(WebElement elemento) {

        Select select = new Select(elemento);

        return (select.getOptions().size() > 1 ? false : true);

    }

    public static void delay(long time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    /*********************************
     * MÃ©todos especÃ­ficos para manipular frames e pages
     ************************/

    public static void iterarTelaPrincipal() {
        Set<String> windowId = driver.getWindowHandles();
        Iterator<String> iterator = windowId.iterator();

        String principal = iterator.next();

        driver.switchTo().window(principal);

        driver.switchTo().defaultContent();

    }

    public static void avancarFrame() {

        WebDriverWait wait = new WebDriverWait(driver, TEMPO);

        wait.until(ExpectedConditions.refreshed(ExpectedConditions.frameToBeAvailableAndSwitchToIt("_blank")));

    }

    public static void trocarFrame(String frame) {

        WebDriverWait wait = new WebDriverWait(driver, TEMPO);

        wait.until(ExpectedConditions.refreshed(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame)));

        try {

            getDriver().switchTo().frame(frame);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }

    private static int buscarIndiceColunaTabela(String nomeTabela, String coluna) {

        int col = 1;

        for (WebElement elemento : DriverUtils.getDriver().findElement(By.id(nomeTabela))
                .findElements(By.tagName("th"))) {
            if (elemento.getText().equals(coluna)) {
                break;
            }
            col++;
        }

        return col;
    }

    public static boolean verificaTextoPresente(String nomeTabela, int linha, String coluna, String valor) {
        boolean condicao = false;

        int indiceColuna = buscarIndiceColunaTabela(nomeTabela, coluna);

        nomeTabela = ".//*[@id='" + nomeTabela + "']/table[2]/tbody";

        List<WebElement> elementos = DriverUtils.getDriver()
                .findElements(By.xpath(nomeTabela + "/tr[" + (linha + 2) + "]/td[" + indiceColuna + "]"));

        for (WebElement elementoLinha : elementos) {
            if (elementoLinha.getText().equals(valor)) {

                condicao = true;
                break;
            }

        }

        return condicao;
    }

    public static boolean verificaBotaoPresente(String nomeTabela, int linha, String coluna) {
        boolean condicao = false;

        int indiceColuna = buscarIndiceColunaTabela(nomeTabela, coluna);

        nomeTabela = ".//*[@id='" + nomeTabela + "']/table[2]/tbody";

        List<WebElement> elementos = DriverUtils.getDriver()
                .findElements(By.xpath(nomeTabela + "/tr[" + (linha + 2) + "]/td[" + indiceColuna + "]"));

        for (WebElement elementoLinha : elementos) {
            if (elementoLinha.findElements(By.tagName("button")).size() > 0) {
                condicao = true;
                break;
            }

        }

        return condicao;

    }

    public static int buscarQuantidadeLinhasTabela(String nomeTabela) {

        return DriverUtils.getDriver()
                .findElements(By.xpath(".//*[@id='" + nomeTabela + "']/table[2]/tbody/tr[*]/td[1]")).size();
    }

    public static void setDriver(WebDriver wd) {
        driver = wd;

    }

    public static String PegarTexto(By by) {
        String texto = null;
        if (buscarElementoPresente(driver.findElement(by))) {
            texto = driver.findElement(by).getText();
        }
        return texto;
    }

    public static boolean elementoVisivel(By by) {
        return driver.findElement(by).isDisplayed();
    }

}

