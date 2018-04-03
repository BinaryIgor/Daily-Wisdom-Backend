package control.self.igor.dailywisdom.service.abstraction;

public interface CrudServiceTest {

    public void properCreateTest();

    public void improperCreateTest();

    public void getListTest();

    public void properUpdateTest();

    public void improperUpdateTest();

    public void nonExistingGetTest();

    public void existingGetTest();

    public void duplicatedCreateTest();

    public void duplicatedUpdateTest();

    public void properDeleteTest();

    public void improperDeleteTest();

}
