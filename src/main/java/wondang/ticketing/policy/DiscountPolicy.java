package wondang.ticketing.policy;

import wondang.ticketing.domain.Member;

public interface DiscountPolicy {

    int discount(Member member);
}