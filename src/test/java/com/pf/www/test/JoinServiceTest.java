package com.pf.www.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.portfolio.www.dto.MemberDto;
import com.portfolio.www.service.JoinService;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(SpringExtension.class)	// junit5,
@ContextConfiguration(locations = {"classpath:context-beans-test.xml", "classpath:**/pf-servlet.xml"})
@Slf4j
class JoinServiceTest {

	/*
	 * @Autowired private JoinService joinService;
	 */
	@Resource(name = "joinService")
	private JoinService joinService;
	
	@Test
	void testJoinMember() {
		System.out.println("joinService========잘찍히니?=======================>"+joinService); 
		//fail("Not yet implemented");
	}
	
    @Test
    @Order(1)
    void testJoin() {
        HashMap<String, String> params = new HashMap<>();
        params.put("memberID", "test006");
        params.put("passwd", "test006");
        params.put("memberNM", "테스트계정");
        params.put("email", "kyd546@naver.com");

        int cnt = joinService.joinMember(params);
        // Assume joinMember returns memberSeq; adapt as necessary
        int memberSeq = joinService.getMemberSeq(params.get("memberID"));
        // int memberSeq = memberSeqStr != null ? Integer.parseInt(memberSeqStr) : 0;
        
        System.out.println("cnt확인==========================>"+cnt);
        System.out.println("memberSeq확인==========================>"+memberSeq);
        
        assertEquals(1, cnt);
        assertNotEquals(0, memberSeq);
        assertEquals(1, joinService.deleteMember(memberSeq));
    }

}
