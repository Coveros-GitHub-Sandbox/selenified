package unit;

import com.coveros.selenified.utilities.Jira;
import com.coveros.selenified.utilities.jira.Annotation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Jira(project = "HW", issue = "HW-123456")
public class AnnotationOverrideTest {

    @Test
    public void verifyDefaultTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertTrue(new Annotation(method).isIssuePresent());
    }

    @Test
    @Jira
    public void verifyProjectTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertEquals(new Annotation(method).getProject(), "HW");
    }

    @Test
    @Jira
    public void verifyIssueTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getIssue(), "HW-123456");
    }

    @Test
    @Jira(project = "FB")
    public void verifyOverrideProjectTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertEquals(new Annotation(method).getProject(), "FB");
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getIssue(), "HW-123456");
    }

    @Test
    @Jira(issue = "FB-987654")
    public void verifyOverrideIssueTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertEquals(new Annotation(method).getProject(), "HW");
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getIssue(), "FB-987654");
    }

    @Test
    @Jira(project = "FB", issue = "FB-987654")
    public void verifyOverrideBothTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertEquals(new Annotation(method).getProject(), "FB");
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getIssue(), "FB-987654");
    }
}