import grails.plugins.selenium.SeleniumAware
@Mixin(SeleniumAware)
class HomepageTests extends GroovyTestCase {
    void testHomepageLoads() {
        selenium.open "/gongodb"
        assertTrue selenium.isTextPresent("Welcome to Grails")
    }
}
