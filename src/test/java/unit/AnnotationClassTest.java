/*
 * Copyright 2018 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package unit;

import com.coveros.selenified.utilities.Jira;
import com.coveros.selenified.utilities.jira.Annotation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class AnnotationTest {

    @Test
    public void verifyDefaultTest(Method method) {
        Assert.assertFalse(new Annotation(method).isAnnotationPresent());
        Assert.assertFalse(new Annotation(method).isProjectPresent());
        Assert.assertFalse(new Annotation(method).isIssuePresent());
    }

    @Test
    @Jira
    public void verifyProjectTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertFalse(new Annotation(method).isProjectPresent());
        Assert.assertNull(new Annotation(method).getProject());
    }

    @Test
    @Jira(project = "HW")
    public void verifySetProjectTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertEquals(new Annotation(method).getProject(), "HW");
    }

    @Test
    @Jira
    public void verifyIssueTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertFalse(new Annotation(method).isIssuePresent());
        Assert.assertNull(new Annotation(method).getIssue());
    }

    @Test
    @Jira(issue = "HW-123456")
    public void verifySetIssueTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getIssue(), "HW-123456");
    }

    @Test
    @Jira(project = "HW", issue = "HW-123456")
    public void verifySetProjectAndIssueTest(Method method) {
        Assert.assertTrue(new Annotation(method).isAnnotationPresent());
        Assert.assertTrue(new Annotation(method).isProjectPresent());
        Assert.assertTrue(new Annotation(method).isIssuePresent());
        Assert.assertEquals(new Annotation(method).getProject(), "HW");
        Assert.assertEquals(new Annotation(method).getIssue(), "HW-123456");
    }
}