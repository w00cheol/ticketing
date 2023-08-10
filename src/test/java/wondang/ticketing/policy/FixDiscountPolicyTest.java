package wondang.ticketing.policy;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wondang.ticketing.domain.Member;
import wondang.ticketing.domain.MemberTest;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class FixDiscountPolicyTest {

    @Autowired private DiscountPolicy discountPolicy;

    @Test
    public void discount() {
        Member memberStaff = MemberTest.newMember("staff.woo", "test.nickname");
        Member memberNonStaff = MemberTest.newMember("kwon.woo", "test.nickname");

        int staffDiscountPrice = discountPolicy.discount(memberStaff);
        int nonStaffDiscountPrice = discountPolicy.discount(memberNonStaff);

        Assertions.assertThat(staffDiscountPrice).isEqualTo(1000);
        Assertions.assertThat(nonStaffDiscountPrice).isEqualTo(0);
    }
}