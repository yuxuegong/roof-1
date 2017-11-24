package test.com.roof.demo.activity.service.impl;

import com.roof.demo.activity.entity.Activity;
import com.roof.demo.activity.entity.ActivityVo;
import com.roof.demo.activity.service.api.IActivityService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

/**
 * ActivityService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 20, 2017</pre>
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ActivityServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IActivityService activityService;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: save(Activity activity)
     */
    @Test
    public void testSave() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: delete(Activity activity)
     */
    @Test
    public void testDelete() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteByExample(Activity activity)
     */
    @Test
    public void testDeleteByExample() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: update(Activity activity)
     */
    @Test
    public void testUpdate() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateIgnoreNull(Activity activity)
     */
    @Test
    public void testUpdateIgnoreNull() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateByExample(Activity activity)
     */
    @Test
    public void testUpdateByExample() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: load(Activity activity)
     */
    @Test
    public void testLoad() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: selectForObject(Activity activity)
     */
    @Test
    public void testSelectForObject() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: selectForList(Activity activity)
     */
    @Test
    public void testSelectForList() throws Exception {
        List<ActivityVo> vos = activityService.selectForList(new Activity());
        System.out.println(vos);
    }

    /**
     * Method: page(Page page, Activity activity)
     */
    @Test
    public void testPage() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setIActivityDao(@Qualifier("activityDao") IActivityDao  activityDao)
     */
    @Test
    public void testSetIActivityDao() throws Exception {
//TODO: Test goes here... 
    }


} 
