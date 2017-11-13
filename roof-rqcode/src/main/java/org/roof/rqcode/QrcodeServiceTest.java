package org.roof.rqcode;

public class QrcodeServiceTest {

//	@After
	public void tearDown() throws Exception {
	}

//	@Test
	public void testCreate() {
		QrcodeService
				.create("http://www.chinapet.com/source/plugin/leepet_thread/template/seyouhui/m/index.htm",
						"E:/excel/");
	}

}
