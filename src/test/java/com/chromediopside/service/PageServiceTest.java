package com.chromediopside.service;

import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Page;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PageServiceTest {

  @Autowired
  private PageService pageService;
  @Autowired
  private Page page;
  @Autowired
  private MockProfileBuilder mockProfileBuilder;

  @Test
  public void pageObjectHasAllFields() {
    List<GiTinderProfile> testList = new ArrayList<>();
    testList.add(mockProfileBuilder.getProfile());

    page.setProfiles(testList);
    page.setCount(1);
    page.setAll(1);
    assertEquals(page, pageService.setPage(1));
  }
}
