package com.tangaoyu.gen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGenerationApplicationTests {

	@Test
	public void contextLoads() {
		Observable.timer(5, TimeUnit.SECONDS) .doOnNext(integer -> System.out.println(integer)).subscribe(o ->
				System.out.println(o)
		);
	}



}
