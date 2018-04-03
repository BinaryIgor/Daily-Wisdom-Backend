package control.self.igor.dailywisdom.service.abstraction;

public interface EntityQuoteServiceTest {

    public void existingEntityListTest();

    public void nonExistingEntityListTest();

    public void searchExistingEntityQuotesTest();

    public void searchNonExistingEntityQuotesTest();

    public void createExistingEntityProperQuoteTest();

    public void createExistingEntityImproperQuoteTest();

    public void createNonExistingEntityQuoteTest();

    public void updateExistingEntityProperQuoteTest();

    public void updateExistingEntityImproperQuoteTest();

    public void deleteExistingEntityQuoteTest();

    public void deleteNonExistingEntityQuoteTest();

    public void updateNonExistingEntityQuoteTest();
}
