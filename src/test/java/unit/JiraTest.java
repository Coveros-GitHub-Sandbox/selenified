package unit;

import com.coveros.selenified.utilities.Jira;
import com.coveros.selenified.utilities.JiraAnnotation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class JiraTest {

    @Test
    public void verifyDefaultTest(Method method) {
        Assert.assertFalse(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertFalse(new JiraAnnotation(method).isProjectPresent());
        Assert.assertFalse(new JiraAnnotation(method).isIssuePresent());
    }

    @Test
    @Jira
    public void verifyProjectTest(Method method) {
        Assert.assertTrue(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertFalse(new JiraAnnotation(method).isProjectPresent());
        Assert.assertNull(new JiraAnnotation(method).getProject());
    }

    @Test
    @Jira(project = "HW")
    public void verifySetProjectTest(Method method) {
        Assert.assertTrue(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertTrue(new JiraAnnotation(method).isProjectPresent());
        Assert.assertEquals(new JiraAnnotation(method).getProject(), "HW");
    }

    @Test
    @Jira
    public void verifyIssueTest(Method method) {
        Assert.assertTrue(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertFalse(new JiraAnnotation(method).isIssuePresent());
        Assert.assertNull(new JiraAnnotation(method).getIssue());
    }

    @Test
    @Jira(issue = "HW-123456")
    public void verifySetIssueTest(Method method) {
        Assert.assertTrue(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertTrue(new JiraAnnotation(method).isIssuePresent());
        Assert.assertEquals(new JiraAnnotation(method).getIssue(), "HW-123456");
    }

    @Test
    @Jira(project = "HW", issue = "HW-123456")
    public void verifySetProjectAndIssueTest(Method method) {
        Assert.assertTrue(new JiraAnnotation(method).isAnnotationPresent());
        Assert.assertTrue(new JiraAnnotation(method).isProjectPresent());
        Assert.assertTrue(new JiraAnnotation(method).isIssuePresent());
        Assert.assertEquals(new JiraAnnotation(method).getProject(), "HW");
        Assert.assertEquals(new JiraAnnotation(method).getIssue(), "HW-123456");
    }
}